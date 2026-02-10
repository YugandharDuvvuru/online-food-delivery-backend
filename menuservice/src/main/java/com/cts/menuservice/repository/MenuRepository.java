package com.cts.menuservice.repository;

import com.cts.menuservice.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MenuRepository extends JpaRepository<MenuEntity,Long> {
    boolean existsByRestaurantIdAndItemNameKey(Long restaurantId, String itemNameKey);
    List<MenuEntity> findByRestaurantId(Long restaurantId);
    Optional<MenuEntity> findByItemId(Long itemId);

    @Query(
            value = """
            SELECT *
            FROM menu_items
            WHERE item_name_key LIKE CONCAT('%', :normalizedKey, '%')
            """,
            nativeQuery = true
    )
    List<MenuEntity> searchByItemNameKeyContainsNative(@Param("normalizedKey") String name);

    @Query(
            value = """
                SELECT *
                FROM menu_items
                WHERE category = :category
                  AND price <= :price
                """,
            nativeQuery = true
    )
    List<MenuEntity> findByCategoryAndPriceLessThanEqualNative(
            @Param("category") String category,
            @Param("price") Integer price
    );

    // Filter by category only
    @Query(
            value = """
                SELECT *
                FROM menu_items
                WHERE category = :category
                """,
            nativeQuery = true
    )
    List<MenuEntity> findByCategoryNative(@Param("category") String category);

    // Filter by price <= only
    @Query(
            value = """
                SELECT *
                FROM menu_items
                WHERE price <= :price
                """,
            nativeQuery = true
    )
    List<MenuEntity> findByPriceLessThanEqualNative(@Param("price") Integer price);
    @Modifying
    @Query(value = "DELETE FROM menu_items WHERE restaurant_id = :restaurantId", nativeQuery = true)
    void deleteByRestaurantId(@Param("restaurantId") Long restaurantId);

}



