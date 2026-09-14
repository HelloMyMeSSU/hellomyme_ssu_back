package com.example.hellomyme.domain.auth.controller;


import com.example.hellomyme.domain.auth.service.EmailAuthServiceImpl;
import com.example.hellomyme.domain.user.dto.req.UserReqDTO;
import com.example.hellomyme.global.apipayload.ApiResponse;
import com.example.hellomyme.global.apipayload.domain.AuthErrorStatus;
import com.example.hellomyme.global.apipayload.exception.GeneralException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final EmailAuthServiceImpl emailAuthServiceImpl;


    @PostMapping("/email/send")
    public ApiResponse<Void> sendCode(@RequestBody @Valid UserReqDTO.EmailRequest request) {
        emailAuthServiceImpl.sendCode(request.email());
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/email/verify")
    public ApiResponse<Void> verifyCode(@RequestBody @Valid UserReqDTO.EmailVerifyRequest request) {
        boolean isVerified = emailAuthServiceImpl.verifyCode(request.email(), request.code());

        if (!isVerified) {
            throw new GeneralException(AuthErrorStatus.INVALID_AUTH_CODE);
        }

        return ApiResponse.onSuccess(null);
    }
}