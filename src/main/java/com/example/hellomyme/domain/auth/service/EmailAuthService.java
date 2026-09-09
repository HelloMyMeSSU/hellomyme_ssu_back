package com.example.hellomyme.domain.auth.service;

public interface EmailAuthService {
    public void sendCode(String email);
    public boolean verifyCode(String email, String inputCode);
}
