package com.example.hellomyme.domain.term.repository;

import com.example.hellomyme.domain.term.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
}
