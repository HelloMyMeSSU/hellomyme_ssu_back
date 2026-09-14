package com.example.hellomyme.domain.user.controller;


import com.example.hellomyme.domain.user.dto.req.UserReqDTO;
import com.example.hellomyme.domain.user.dto.res.UserResDTO;
import com.example.hellomyme.domain.user.service.UserService;
import com.example.hellomyme.global.apipayload.ApiResponse;
import com.example.hellomyme.global.security.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserResDTO.SignUpDTO> signUp(@RequestBody @Valid UserReqDTO.SignUpDTO dto) {
        UserResDTO.SignUpDTO response = userService.signUp(dto);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/login")
    public ApiResponse<UserResDTO.LoginDTO> login(@RequestBody @Valid UserReqDTO.LoginDTO dto) {
        UserResDTO.LoginDTO response = userService.login(dto);
        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/profile")
    public ApiResponse<UserResDTO.UpdateProfileDTO> updateProfile(
            @AuthenticationPrincipal PrincipalDetails userDetails,
            @RequestBody UserReqDTO.UpdateProfileDTO request){

        UserResDTO.UpdateProfileDTO response =
                userService.updateProfile(userDetails.getUserId(), request);

        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/delete")
    public ApiResponse<Void>deleteUser(
            @AuthenticationPrincipal PrincipalDetails userDetails
    ){
        userService.deleteUser(userDetails.getUserId());
        return ApiResponse.onSuccess(null);
    }

}
