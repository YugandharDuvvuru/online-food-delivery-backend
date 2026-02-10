package com.cts.userservice.service;

import com.cts.userservice.client.AuthClient;
import com.cts.userservice.dto.MessageResponse;
import com.cts.userservice.dto.UserAddressResponseDto;
import com.cts.userservice.entity.UserAddress;
import com.cts.userservice.entity.UserEntity;
import com.cts.userservice.dto.UserAddressDto;
import com.cts.userservice.dto.UserDetailsDto;
import com.cts.userservice.exceptions.AddressNotFoundException;
import com.cts.userservice.exceptions.AuthenticationException;
import com.cts.userservice.exceptions.DuplicateEmailException;
import com.cts.userservice.repository.AddressRepository;
import com.cts.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService{
    @Autowired
    private AuthClient authClient;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AddressRepository addressRepo;
    @Override
    public ResponseEntity<MessageResponse> saveUserDetails(UserEntity userEntity) {
        UserEntity user=userRepo.save(userEntity);
         return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User saved successfully"));
    }


    @Override
    public ResponseEntity<UserDetailsDto> getUserDetailsById(Long id) {
        Optional<UserEntity> userDetails = userRepo.findByAuthId(id);


        if (userDetails.isEmpty()) {
            throw new AuthenticationException("User not found");
        }

        // Get entity
        UserEntity user = userDetails.get();

        // Manually map entity fields to DTO
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<MessageResponse> addUserAddress(Long userId, UserAddressDto userAddress) {
        Optional<UserEntity> userDetails = userRepo.findByUserId(userId);
        if (userDetails.isEmpty()) {
            throw new AuthenticationException("User not found");
        }
        UserEntity user = userDetails.get();
        UserAddress address=new UserAddress();
        address.setUser(user);
        address.setHouseNo(userAddress.getHouseNo());
        address.setStreetName(userAddress.getStreetName());
        address.setTown(userAddress.getTown());
        address.setDistrict(userAddress.getDistrict());
        address.setState(userAddress.getState());
        address.setPincode(userAddress.getPincode());
        addressRepo.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Address saved successfully"));
    }


    @Override
    public ResponseEntity<List<UserAddressResponseDto>> getUserAddress(Long userId) {
        // Fetch addresses for the user
        List<UserAddress> addresses = addressRepo.findByUser_UserId(userId);

        // If your repo never returns null, prefer checking for empty
        if (addresses == null || addresses.isEmpty()) {
            throw new AddressNotFoundException("No addresses found for userId: " + userId);
        }

        // Map entities to DTOs manually
        List<UserAddressResponseDto> userAddresses = new ArrayList<>(addresses.size());
        for (UserAddress addr : addresses) {
            UserAddressResponseDto dto = new UserAddressResponseDto();
            dto.setAddressId(addr.getAddressId());
            dto.setHouseNo(addr.getHouseNo());
            dto.setStreetName(addr.getStreetName());
            dto.setTown(addr.getTown());
            dto.setDistrict(addr.getDistrict());
            dto.setState(addr.getState());
            dto.setPincode(addr.getPincode());
            userAddresses.add(dto);
        }

        return ResponseEntity.ok(userAddresses);
    }

    @Override
    public ResponseEntity<UserDetailsDto> updateUserById(Long userId, UserDetailsDto userDetails,String token) {
        UserEntity user = userRepo.findByUserId(userId)
                .orElseThrow(() -> new AuthenticationException("User not found"));
        boolean emailChanged = userDetails.getEmail() != null
                && !userDetails.getEmail().equals(user.getEmail());
        if (emailChanged) {
            System.out.println("email changed");
            MessageResponse authResp = authClient.updateEmail(token,user.getAuthId(), userDetails.getEmail()).getBody();
            String message = (authResp != null ? authResp.getMessage() : null);
            if ("Email already registered".equals(message)) {
                throw new DuplicateEmailException("Email already registered try with another Mail.");
            }
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getFirstName() != null) {
            user.setFirstName(userDetails.getFirstName());
        }
        if (userDetails.getLastName() != null) {
            user.setLastName(userDetails.getLastName());
        }
        if (userDetails.getMobileNumber() != null) {
            user.setMobileNumber(userDetails.getMobileNumber());
        }
        UserEntity saved = userRepo.save(user);
        UserDetailsDto responseDto = new UserDetailsDto(saved);
        System.out.println("how are you");
        return ResponseEntity.ok(responseDto);
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> deleteUserById(Long userId,String token) {
        Optional<UserEntity> userEntity = userRepo.findByUserId(userId);
        if(userEntity.isEmpty()){
            throw new AuthenticationException("User not found");
        }
        MessageResponse response=authClient.deleteUserByAuthId(token,userEntity.get().getAuthId()).getBody();
        if(response.getMessage().equals("User Deleted Successfully.")){
            userRepo.deleteById(userId);
            return ResponseEntity.ok(new MessageResponse("User Deleted Successfully"));
        }
        else {
            return ResponseEntity.ok(new MessageResponse("Error occurs while deleting the user"));
        }
    }

}


