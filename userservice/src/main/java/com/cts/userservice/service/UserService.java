package com.cts.userservice.service;

import com.cts.userservice.dto.MessageResponse;
import com.cts.userservice.dto.UserAddressResponseDto;
import com.cts.userservice.entity.UserEntity;
import com.cts.userservice.dto.UserAddressDto;
import com.cts.userservice.dto.UserDetailsDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public ResponseEntity<MessageResponse> saveUserDetails(UserEntity userEntity);
    public ResponseEntity<UserDetailsDto> getUserDetailsById(Long id);
    public ResponseEntity<MessageResponse> addUserAddress(Long userId, UserAddressDto userAddress);
    public ResponseEntity<List<UserAddressResponseDto>> getUserAddress(Long userId);
    public ResponseEntity<UserDetailsDto> updateUserById(Long userId,UserDetailsDto userDetails,String token);
    public ResponseEntity<MessageResponse> deleteUserById(Long userId,String token);
}
