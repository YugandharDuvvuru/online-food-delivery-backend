package com.cts.orderservice.controller;

import com.cts.orderservice.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cts.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
    @PreAuthorize("hasRole('USER')")
	@PostMapping("/place/order/{userId}")
	public ResponseEntity<OrderResponseDto> placeOrder(HttpServletRequest servletRequest, @PathVariable Long userId, @RequestBody OrderRequestDto request){
        String authHeader=servletRequest.getHeader("Authorization");
		return orderService.placeOrder(userId, request,authHeader);
	}
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("toggle/orderStatus/{status}/{orderId}")
    public ResponseEntity<MessageResponse> toogleOrderStatus(@PathVariable String status,@PathVariable Long orderId){
        return orderService.toggleOrderStatus(status,orderId);
    }
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    @GetMapping("/get-order/details/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderDetails(@PathVariable Long orderId){
        return orderService.getOrderDetails(orderId);
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/cancel/order/{orderId}")
    public ResponseEntity<MessageResponse> cancelOrder(@PathVariable Long orderId){
        return orderService.cancelOrder(orderId);
    }
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("show/orders/for-particular-restaurant/{restaurantId}")
    public ResponseEntity<List<RestaurantOrderSummaryDto>> getIncommingOrders(@PathVariable Long restaurantId){
        return orderService.showIncommingOrdersToRestaurant(restaurantId);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get/order/history/for-user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(@PathVariable Long userId){
        return orderService.getOrderHistory(userId);
    }

}
