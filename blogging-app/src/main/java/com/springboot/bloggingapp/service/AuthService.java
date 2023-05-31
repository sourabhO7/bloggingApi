package com.springboot.bloggingapp.service;

import com.springboot.bloggingapp.payload.LoginDto;
import com.springboot.bloggingapp.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
