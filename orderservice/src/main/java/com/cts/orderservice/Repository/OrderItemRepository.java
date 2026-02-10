package com.cts.orderservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.orderservice.entity.OrderItems;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

}
