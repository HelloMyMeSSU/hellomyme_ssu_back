package com.example.hellomyme.domain.user.entity.mapping;

import com.example.hellomyme.domain.term.entity.Term;
import com.example.hellomyme.domain.user.entity.User;
import com.example.hellomyme.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agree")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agree extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;                  // 멤버 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id")
    private Term term;                     // 약관 아이디

    @Builder
    private Agree(User user, Term term) {
        this.user = user;
        this.term = term;
    }
}