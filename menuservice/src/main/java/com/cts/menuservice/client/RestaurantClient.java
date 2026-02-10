package com.cts.menuservice.client;

import com.cts.menuservice.dto.RestaurantResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="APIGATEWAY")
public interface RestaurantClient {
    @GetMapping("owner-api/restaurant/get-restaurant/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@RequestHeader("Authorization") String token, @PathVariable Long restaurantId);
}
