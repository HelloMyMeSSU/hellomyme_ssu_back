package com.example.hellomyme.domain.term.controller;


import com.example.hellomyme.domain.term.dto.res.TermResDTO;
import com.example.hellomyme.domain.term.repository.TermRepository;
import com.example.hellomyme.domain.user.entity.mapping.Agree;
import com.example.hellomyme.domain.user.repository.AgreeRepository;
import com.example.hellomyme.global.apipayload.ApiResponse;
import com.example.hellomyme.global.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/terms")
@RequiredArgsConstructor
public class TermController {

    private final TermRepository termRepository;
    private final AgreeRepository agreeRepository;

    //모든 약관 조회(회원 가입 시)
    @GetMapping("")
    public ApiResponse<List<TermResDTO>> getTerms(){
        List<TermResDTO> terms = termRepository.findAll().stream()
                .map(TermResDTO::from)
                .toList();
        return ApiResponse.onSuccess(terms);
    }

    //내가 동의한 약관 목록 조회(로그인 후 조회)
    @GetMapping("/me")
    public ApiResponse<List<TermResDTO>> getMyAgreedTerms(
            @AuthenticationPrincipal PrincipalDetails userDetails) {

        List<Agree> agreements = agreeRepository.findByUser_Id(userDetails.getUserId());
        List<TermResDTO> terms = agreements.stream()
                .map(agree -> TermResDTO.from(agree.getTerm()))
                .toList();

        return ApiResponse.onSuccess(terms);
    }
}

