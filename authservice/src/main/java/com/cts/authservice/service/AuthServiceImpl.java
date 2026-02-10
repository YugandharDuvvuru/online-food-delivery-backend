package com.cts.authservice.service;

import com.cts.authservice.client.UserAndOwnerClient;
import com.cts.authservice.dto.*;
import com.cts.authservice.entity.UserAuthDetails;
import com.cts.authservice.exceptions.AuthenticationException;
import com.cts.authservice.repository.AuthRepository;
import com.cts.authservice.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements  AuthService{
    @Autowired
    private UserAndOwnerClient client;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthRepository authRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Transactional
    @Override
    public ResponseEntity<MessageResponse> registerUser(String role, UserDetailsDto userDetailsDto) {
        UserAuthDetails existingUser = authRepo.findByUserEmail(userDetailsDto.getUserEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Email already registered"));
        }
        UserAuthDetails user = new UserAuthDetails();
        user.setUserEmail(userDetailsDto.getUserEmail());
        user.setUserPassword(passwordEncoder.encode(userDetailsDto.getUserPassword()));
        user.setRoles(role);
        authRepo.saveAndFlush(user); // ensures DB constraint violation occurs here if duplicate
        if(role.equals("USER")){
            UserRegisterResponseDto userData=new UserRegisterResponseDto();
            userData.setAuthId(user.getAuthId());
            userData.setEmail(userDetailsDto.getUserEmail());
            userData.setMobileNumber(userDetailsDto.getMobileNumber());
            userData.setFirstName(userDetailsDto.getFirstName());
            userData.setLastName(userDetailsDto.getLastName());
            client.saveUserDetails(userData);
        }
        else{
            UserRegisterResponseDto userData=new UserRegisterResponseDto();
            userData.setAuthId(user.getAuthId());
            userData.setEmail(userDetailsDto.getUserEmail());
            userData.setMobileNumber(userDetailsDto.getMobileNumber());
            userData.setFirstName(userDetailsDto.getFirstName());
            userData.setLastName(userDetailsDto.getLastName());
            client.saveOwnerDetails(userData);

        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Registration successful"));
        }

    @Override
    public ResponseEntity<JwtResponseDto> checkLoginDetails(UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getUserEmail(), userLoginDto.getUserPassword())
        );

        if (!authentication.isAuthenticated()) {
        	System.out.println("authentication failed");
            throw new AuthenticationException("Authentication failed");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtUtil.generateJwtToken(authentication);
        Long expiry=jwtUtil.getExpiry();
        JwtResponseDto jwtResponseDto=new JwtResponseDto();
        jwtResponseDto.setJwtToken(token);
        jwtResponseDto.setJwtTokenExpiration(String.valueOf(expiry));
        System.out.println("authentication passed");
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponseDto);
    }

    @Override
    public ResponseEntity<MessageResponse> updateEmail(Long authId,String email) {
        UserAuthDetails userDetails=authRepo.findByUserEmail(email);
        if(userDetails!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Email already registered"));
        }
        UserAuthDetails userNewDetails=authRepo.findByAuthId(authId);
        userNewDetails.setUserEmail(email);
        authRepo.save(userNewDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Email updated successfully"));

    }

    @Override
    public ResponseEntity<MessageResponse> deleteUserById(Long authId) {
        UserAuthDetails userDetails=authRepo.findByAuthId(authId);
        if(userDetails==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not found"));
        }
       authRepo.deleteById(authId);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User Deleted Successfully."));
    }
}
