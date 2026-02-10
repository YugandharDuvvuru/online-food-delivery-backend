package com.cts.cartservice.client;

import com.cts.cartservice.dto.MenuResponseDto;
import com.cts.cartservice.dto.RestaurantResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "APIGATEWAY")
public interface MenuAndRestaurantClient {
    @GetMapping("menu-api/menu/get-item/details/{itemId}")
    public ResponseEntity<MenuResponseDto> getParticularItemDetails(@RequestHeader("Authorization") String token, @PathVariable Long itemId);
    @GetMapping("owner-api/restaurant/get-restaurant/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@RequestHeader("Authorization") String token, @PathVariable Long restaurantId);
}
