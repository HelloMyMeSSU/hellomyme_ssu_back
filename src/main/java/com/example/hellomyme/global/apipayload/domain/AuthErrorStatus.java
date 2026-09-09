package com.example.hellomyme.global.apipayload.domain;


import com.example.hellomyme.global.apipayload.code.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorStatus implements BaseCode {

    TOKEN_MISSING(HttpStatus.BAD_REQUEST, "AUTH_400_07", "토큰 값이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_401_01", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_401_02", "만료된 토큰입니다."),
    ALREADY_EXIST_EMAIL(HttpStatus.CONFLICT, "AUTH_409_01", "이미 가입된 이메일입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "AUTH_404_01", "존재하지 않는 유저입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "AUTH_400_01", "비밀번호가 일치하지 않습니다."),
    VERIFICATION_CODE_REQUIRED(HttpStatus.BAD_REQUEST, "AUTH_400_07", "인증 코드가 필요합니다."),
    INVALID_AUTH_CODE(HttpStatus.BAD_REQUEST, "AUTH_400_02", "인증코드가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}

