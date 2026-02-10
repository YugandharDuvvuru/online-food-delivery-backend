package com.cts.orderservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.orderservice.entity.OrderEntity;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long>{
    OrderEntity findByOrderId(Long orderId);
    List<OrderEntity> findByUserId(Long userId);

    @Query("""
        SELECT DISTINCT o
        FROM OrderEntity o
        JOIN o.orderItems oi
        WHERE oi.restaurantId = :restaurantId
          AND o.orderStatus = 'CREATED'
        ORDER BY o.orderTime DESC
    """)
    List<OrderEntity> findCreatedOrdersForRestaurant(@Param("restaurantId") Long restaurantId);

}
