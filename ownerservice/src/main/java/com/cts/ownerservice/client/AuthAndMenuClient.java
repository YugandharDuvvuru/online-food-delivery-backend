package com.cts.ownerservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.cts.ownerservice.dto.MessageResponse;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name="APIGATEWAY")
public interface AuthAndMenuClient {
    @PutMapping("auth-api/auth/update/email/by-authId/{authId}/{email}")
    public ResponseEntity<MessageResponse> updateEmail(@RequestHeader("Authorization") String token, @PathVariable Long authId, @PathVariable String email);

    @DeleteMapping("auth-api/auth/delete/the/user/by/{authId}")
    public ResponseEntity<MessageResponse> deleteUserByAuthId(@RequestHeader("Authorization") String token,@PathVariable Long authId);
    @DeleteMapping("menu-api/menu/delete/items/by/{restaurantId}")
    public void deleteItemsOfRestaurant(@RequestHeader("Authorization") String token,@PathVariable Long restaurantId);
}
