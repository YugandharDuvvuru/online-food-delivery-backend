package com.cts.authservice.repository;

import com.cts.authservice.entity.UserAuthDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<UserAuthDetails,Long> {
    UserAuthDetails findByAuthId(Long authId);
    UserAuthDetails findByUserEmail(String userEmail);
}
