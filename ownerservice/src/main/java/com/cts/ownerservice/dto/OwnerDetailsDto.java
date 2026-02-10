package com.cts.ownerservice.dto;

import com.cts.ownerservice.entity.OwnerEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OwnerDetailsDto {
    private Long ownerId;
    private String email;
    private String mobileNumber;
    private String firstName;
    private String lastName;
    public OwnerDetailsDto(OwnerEntity entity){
        this.ownerId=entity.getOwnerId();
        this.email=entity.getEmail();
        this.mobileNumber=entity.getMobileNumber();
        this.firstName=entity.getFirstName();
        this.lastName=entity.getLastName();
    }
}
