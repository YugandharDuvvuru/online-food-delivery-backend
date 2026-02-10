package com.cts.userservice.dto;

import com.cts.userservice.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsDto {
    private Long userId;
    private String email;
    private String mobileNumber;
    private String firstName;
    private String lastName;
    public UserDetailsDto(UserEntity entity){
        this.userId=entity.getUserId();
        this.email=entity.getEmail();
        this.firstName=entity.getFirstName();
        this.lastName=entity.getLastName();
        this.mobileNumber=entity.getMobileNumber();
    }
}
