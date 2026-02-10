package com.cts.ownerservice.repository;

import com.cts.ownerservice.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity,Long> {
    Optional<OwnerEntity> findByOwnerId(Long id);
    Optional<OwnerEntity> findByAuthId(Long id);
}
