package com.example.hellomyme.domain.user.dto.res;


import java.time.LocalDateTime;

public class UserResDTO {
    public record SignUpDTO(
            Long id,
            LocalDateTime createdAt
    ) {
    }
    public record LoginDTO(
            String accessToken
    ) {}

    public record UpdateProfileDTO(
            String nickname,
            String email
    ){}
}

