package com.example.hellomyme.domain.term.dto.res;

import com.example.hellomyme.domain.term.entity.Term;

public record TermResDTO(
        Long id,
        String title,
        String content,
        boolean isRequired
) {
    public static TermResDTO from(Term term) {
        return new TermResDTO(term.getId(), term.getTitle(), term.getContent(), term.isRequired());
    }
}
