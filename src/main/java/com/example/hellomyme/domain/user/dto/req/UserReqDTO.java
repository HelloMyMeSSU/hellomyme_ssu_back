package com.example.hellomyme.domain.user.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserReqDTO {
    public record SignUpDTO(
            @NotBlank
            @Email
            String email,

            @NotBlank
            String authCode,

            @NotBlank
            String nickname,

            @NotBlank
            String password,

            List<Long> agreedTermIds
    ){}

    public record LoginDTO(
            @NotBlank @Email String email,
            @NotBlank String password
    ) {}

    public record EmailRequest(@NotBlank @Email String email) {}

    public record EmailVerifyRequest(
            @Email @NotBlank String email,
            @NotBlank String code
    ) {}

    public record UpdateProfileDTO(
            String nickname,
            String email,
            String code
    ){}

}