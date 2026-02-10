package com.cts.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAddressResponseDto {
    private Long addressId;
    private String houseNo;
    private String streetName;
    private String town;
    private String district;
    private String state;
    private String pincode;
}
