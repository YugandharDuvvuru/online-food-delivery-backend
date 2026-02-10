package com.cts.userservice.controller;

import com.cts.userservice.dto.MessageResponse;
import com.cts.userservice.dto.UserAddressResponseDto;
import com.cts.userservice.entity.UserEntity;
import com.cts.userservice.dto.UserAddressDto;
import com.cts.userservice.dto.UserDetailsDto;
import com.cts.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/save-details")
    public ResponseEntity<MessageResponse> saveUserDetails(@RequestBody UserEntity userEntity){
        return userService.saveUserDetails(userEntity);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-details-by-id/{id}")
    public ResponseEntity<UserDetailsDto> getUserDetails(@PathVariable Long id){
      return userService.getUserDetailsById(id);
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add-address/{userId}")
    public ResponseEntity<MessageResponse> addUserAddress(@PathVariable Long userId, @RequestBody UserAddressDto userAddress){
        return userService.addUserAddress(userId,userAddress);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-address/{userId}")
    public ResponseEntity<List<UserAddressResponseDto>> getUserAddresses(@PathVariable Long userId){
        return userService.getUserAddress(userId);
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/user-details/{userId}")
    public ResponseEntity<UserDetailsDto> updateUserDetails(HttpServletRequest request, @PathVariable Long userId, @RequestBody UserDetailsDto userDetails){
        String authHeader=request.getHeader("Authorization");
        return userService.updateUserById(userId,userDetails,authHeader);
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/user-by/{userId}")
    public ResponseEntity<MessageResponse> deleteUserById(HttpServletRequest request,@PathVariable Long userId){
        String authHeader=request.getHeader("Authorization");
        return userService.deleteUserById(userId,authHeader);
    }
}
