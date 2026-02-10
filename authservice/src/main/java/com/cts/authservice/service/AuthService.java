package com.cts.authservice.service;

import com.cts.authservice.dto.JwtResponseDto;
import com.cts.authservice.dto.MessageResponse;
import com.cts.authservice.dto.UserDetailsDto;
import com.cts.authservice.dto.UserLoginDto;
import org.springframework.http.ResponseEntity;

public interface AuthService{
    public ResponseEntity<MessageResponse> registerUser(String role, UserDetailsDto userDetailsDto);
    public ResponseEntity<JwtResponseDto> checkLoginDetails(UserLoginDto userLoginDto);
    public ResponseEntity<MessageResponse> updateEmail(Long authId,String email);
    public ResponseEntity<MessageResponse> deleteUserById(Long authId);
}
