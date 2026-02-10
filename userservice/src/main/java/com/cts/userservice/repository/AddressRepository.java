package com.cts.userservice.repository;

import com.cts.userservice.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<UserAddress,Long> {
    List<UserAddress> findByUser_UserId(Long userId);
}
