package com.cts.authservice.controller;

import com.cts.authservice.dto.JwtResponseDto;
import com.cts.authservice.dto.MessageResponse;
import com.cts.authservice.dto.UserDetailsDto;
import com.cts.authservice.dto.UserLoginDto;
import com.cts.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/user-register/{role}")
    public ResponseEntity<MessageResponse> registerUser(@PathVariable String role, @RequestBody UserDetailsDto userDetailsDto){
        return authService.registerUser(role,userDetailsDto);
    }
    @PostMapping("/user-login")
    public ResponseEntity<JwtResponseDto> validateUserLogin(@RequestBody UserLoginDto userLoginDto){
    	System.out.println("helloworld");
        return authService.checkLoginDetails(userLoginDto);
    }
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    @PutMapping ("/update/email/by-authId/{authId}/{email}")
    public ResponseEntity<MessageResponse> updateEmail(@PathVariable Long authId,@PathVariable String email){
      return authService.updateEmail(authId,email);
    }
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    @DeleteMapping("delete/the/user/by/{authId}")
    public ResponseEntity<MessageResponse>  deleteUserByAuthId(@PathVariable Long authId){
        return authService.deleteUserById(authId);
    }


}
