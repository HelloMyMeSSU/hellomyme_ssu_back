package com.example.hellomyme.domain.user.converter;

import com.example.hellomyme.domain.user.dto.req.UserReqDTO;
import com.example.hellomyme.domain.user.dto.res.UserResDTO;
import com.example.hellomyme.domain.user.entity.User;
import com.example.hellomyme.global.common.RoleType;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {

    // Entity -> DTO
    public UserResDTO.SignUpDTO toSignUpDTO(User user) {
        return new UserResDTO.SignUpDTO(
                user.getId(),
                user.getCreatedAt()
        );
    }

    // DTO -> Entity
    public User toUser(UserReqDTO.SignUpDTO dto, String encodedPassword) {
        return User.builder()
                .email(dto.email())
                .password(encodedPassword)
                .nickname(dto.nickname() + System.currentTimeMillis() % 10000)
                .role(RoleType.ROLE_USER)
                .build();
    }

    public UserResDTO.UpdateProfileDTO toUpdateProfileDTO(User user) {
        return new UserResDTO.UpdateProfileDTO(user.getNickname(), user.getEmail());
    }
}
