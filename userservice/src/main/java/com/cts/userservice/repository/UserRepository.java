package com.cts.userservice.repository;

import com.cts.userservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByAuthId(Long id);
    Optional<UserEntity> findByUserId(Long id);
}
