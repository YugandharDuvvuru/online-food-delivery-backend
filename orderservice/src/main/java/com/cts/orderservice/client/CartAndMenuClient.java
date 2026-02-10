package com.cts.orderservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.orderservice.dto.MenuResponseDto;

@FeignClient(name="APIGATEWAY")
public interface CartAndMenuClient {
	 @GetMapping("cart-api/cart/get/all-cart-items/for/user-with/{userId}")
	 public ResponseEntity<List<MenuResponseDto>> getAllCartItemsForUser(@RequestHeader("Authorization") String token, @PathVariable Long userId);
     @DeleteMapping("cart-api/cart/clear/cart/{userId}")
     public ResponseEntity<String> deleteAllCartItems(@RequestHeader("Authorization") String token,@PathVariable Long userId);
    @PutMapping("menu-api/menu/update/no-of-items-delivered/{itemId}/{estimatedItemsDelivered}")
    public ResponseEntity<String> updateEstimatedItemsDelivered(@RequestHeader("Authorization") String token, @PathVariable Long itemId, @PathVariable Integer estimatedItemsDelivered);
}
