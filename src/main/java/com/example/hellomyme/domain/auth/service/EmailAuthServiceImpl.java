package com.example.hellomyme.domain.auth.service;

import com.example.hellomyme.global.apipayload.domain.AuthErrorStatus;
import com.example.hellomyme.global.apipayload.exception.GeneralException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class EmailAuthServiceImpl implements EmailAuthService {

    private final JavaMailSender mailSender;
    private final Map<String, CodeEntry> codeStore = new ConcurrentHashMap<>();

    private static final long EXPIRE_MINUTES = 5;

    @Override
    public void sendCode(String email) {
        String code = generateCode();
        codeStore.put(email, new CodeEntry(code, LocalDateTime.now().plusMinutes(EXPIRE_MINUTES)));

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setFrom("hellomyme.ssu@gmail.com", "헬로마이미");
            helper.setSubject("[헬로마이미] 회원가입 인증코드 안내");

            String htmlContent = "<div style='margin:20px; padding:20px; border:1px solid #e2e2e2; border-radius:10px;'>"
                    + "<h2>헬로마이미 회원가입 인증번호</h2>"
                    + "<p>아래 6자리 인증번호를 입력창에 입력해주세요.</p>"
                    + "<div style='font-size:24px; font-weight:bold; color:#4A90E2; letter-spacing:4px; margin:20px 0;'>"
                    + code + "</div>"
                    + "<p style='color:#888; font-size:12px;'>본 인증코드는 5분간 유효합니다.</p>"
                    + "</div>";

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (Exception e) {
            throw new GeneralException(AuthErrorStatus.MAIL_SEND_ERROR); // 예외 처리
        }
    }

    @Override
    public boolean verifyCode(String email, String inputCode) {
        System.out.println("검증 요청 이메일: [" + email + "]");
        System.out.println("검증 요청 코드: [" + inputCode + "]");

        CodeEntry entry = codeStore.get(email);

        if (entry == null) {
            System.out.println("❌ 실패: 저장소에 해당 이메일로 발급된 코드가 없습니다.");
            return false;
        }

        if (entry.expireAt().isBefore(LocalDateTime.now())) {
            System.out.println("❌ 실패: 코드가 만료되었습니다.");
            return false;
        }

        System.out.println("저장되어 있던 코드: [" + entry.code() + "]");

        if (!entry.code().equals(inputCode)) {
            System.out.println("❌ 실패: 저장된 코드와 입력된 코드가 다릅니다.");
            return false;
        }

        System.out.println("✅ 성공: 인증코드 일치");
        codeStore.remove(email);
        return true;
    }

    private String generateCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    private record CodeEntry(String code, LocalDateTime expireAt) {}
}