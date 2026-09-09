package com.example.hellomyme.domain.user.repository;

import com.example.hellomyme.domain.term.entity.Term;
import com.example.hellomyme.domain.user.entity.User;
import com.example.hellomyme.domain.user.entity.mapping.Agree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgreeRepository extends JpaRepository<Agree,Long> {
    List<Agree> findByUser(User user);
    boolean existsByUserAndTerm(User user, Term term);

    List<Agree> findByUser_Id(Long userId);
}
