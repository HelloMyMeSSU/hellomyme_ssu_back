package com.example.hellomyme.domain.term.entity;

import com.example.hellomyme.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "term")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Term extends BaseEntity {

    private String title;                 // 제목

    @Column(columnDefinition = "TEXT")
    private String content;               // 내용

    @Column(name = "is_required")
    private boolean isRequired;             // 동의 여부(필수 동의항목 동의 여부)

    @Builder
    private Term(String title, String content, boolean isRequired) {
        this.title = title;
        this.content = content;
        this.isRequired = isRequired;
    }
}

