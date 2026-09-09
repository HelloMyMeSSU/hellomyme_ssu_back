package com.example.hellomyme.domain.user.entity;



import com.example.hellomyme.global.common.RoleType;
import com.example.hellomyme.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Column(nullable = false)
    private String nickname;              // 닉네임

    @Column(nullable = false)
    private String password;              // 비밀번호

    @Enumerated(EnumType.STRING)
    private RoleType role;                // 권한

    @Column(nullable = false, unique = true)
    private String email;                 // 이메일 (검증 로직은 서비스단에서)

    private String profile;               // 프로필 이미지 url

    @Column
    private LocalDateTime deletedAt;      // 삭제 시간


    @Builder
    private User(String nickname, String email, String profile,RoleType role, String password) {
        this.nickname = nickname;
        this.email = email;
        this.profile = profile;
        this.role = role;
        this.password = password;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}

