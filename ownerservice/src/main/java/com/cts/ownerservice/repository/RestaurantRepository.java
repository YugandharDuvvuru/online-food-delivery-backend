package com.cts.ownerservice.repository;

import com.cts.ownerservice.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Long> {
    List<RestaurantEntity> findByOwner_OwnerId(Long ownerId);
    Optional<RestaurantEntity> findByRestaurantId(Long restaurantId);
    @Query(
            value = """
            SELECT *
            FROM restaurant_entity
            WHERE name_key LIKE CONCAT('%', :normalizedKey, '%')
            """,
            nativeQuery = true
    )
    List<RestaurantEntity> searchByNameKeyContainsNative(@Param("normalizedKey") String name);
}
