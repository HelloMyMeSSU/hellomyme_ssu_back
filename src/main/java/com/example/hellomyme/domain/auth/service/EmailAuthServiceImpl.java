package com.example.hellomyme.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("헬로마이미 회원가입 인증코드");
        message.setText("인증코드: " + code);

        mailSender.send(message);
    }

    @Override
    public boolean verifyCode(String email, String inputCode) {
        // 1. 넘어온 파라미터 확인 (공백 확인을 위해 대괄호 감쌈)
        System.out.println("검증 요청 이메일: [" + email + "]");
        System.out.println("검증 요청 코드: [" + inputCode + "]");

        CodeEntry entry = codeStore.get(email);

        // 2. Map에서 꺼낸 결과 확인
        if (entry == null) {
            System.out.println("❌ 실패: 저장소에 해당 이메일로 발급된 코드가 없습니다.");
            return false;
        }

        if (entry.expireAt().isBefore(LocalDateTime.now())) {
            System.out.println("❌ 실패: 코드가 만료되었습니다.");
            return false;
        }

        System.out.println("저장되어 있던 코드: [" + entry.code() + "]");

        // 3. 값 비교
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