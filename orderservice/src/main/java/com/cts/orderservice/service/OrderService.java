package com.cts.orderservice.service;

import com.cts.orderservice.dto.MessageResponse;
import com.cts.orderservice.dto.RestaurantOrderSummaryDto;
import org.springframework.http.ResponseEntity;

import com.cts.orderservice.dto.OrderRequestDto;
import com.cts.orderservice.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
	public ResponseEntity<OrderResponseDto> placeOrder(Long userId,OrderRequestDto request,String token);
    public ResponseEntity<MessageResponse> toggleOrderStatus(String status, Long orderId);
    public ResponseEntity<OrderResponseDto> getOrderDetails(Long orderId);
    public ResponseEntity<MessageResponse> cancelOrder(Long orderId);
    public ResponseEntity<List<RestaurantOrderSummaryDto>> showIncommingOrdersToRestaurant(Long restaurantId);
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(Long userId);

}
