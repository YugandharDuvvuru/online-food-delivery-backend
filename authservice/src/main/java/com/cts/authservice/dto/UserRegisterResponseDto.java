package com.cts.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterResponseDto {
    private Long authId;
    private String email;
    private String mobileNumber;
    private String firstName;
    private String lastName;
}
