package com.cts.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsDto {
    private String userEmail;
    private String userPassword;
    private String mobileNumber;
    private String firstName;
    private String lastName;
}
