package com.cts.authservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAuthDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long authId;
    private String userEmail;
    private String userPassword;
    private String roles;
}
