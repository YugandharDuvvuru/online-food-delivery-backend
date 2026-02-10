package com.cts.ownerservice.service;

import com.cts.ownerservice.dto.MessageResponse;
import com.cts.ownerservice.dto.OwnerDetailsDto;
import com.cts.ownerservice.dto.RestaurantDetailsDto;
import com.cts.ownerservice.dto.RestaurantResponseDto;
import com.cts.ownerservice.entity.OwnerEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OwnerService {
    public ResponseEntity<MessageResponse> saveOwnerDetails(OwnerEntity ownerEntity);
    public ResponseEntity<OwnerDetailsDto>getOwnerDetailsById(Long id);
    public ResponseEntity<MessageResponse> addRestaurants(Long ownerId, RestaurantDetailsDto restaurantDetails);
    public ResponseEntity<List<RestaurantResponseDto>> getResataurantsOfOwner(Long ownerId);
    public ResponseEntity<RestaurantResponseDto> getRestaurantsById(Long restaurantId);
    public ResponseEntity<MessageResponse> toggleOpenStatus(boolean status,Long restaurantId);
    public ResponseEntity<List<RestaurantResponseDto>> searchRestaurantByName(String name);
    public ResponseEntity<RestaurantResponseDto> updateRestaurantById(Long restaurantId,RestaurantDetailsDto restaurantDetails);
    public ResponseEntity<MessageResponse> deleteRestaurantById(Long restaurantId,String token);
    public ResponseEntity<OwnerDetailsDto> updateOwnerById(Long ownerId,OwnerDetailsDto ownerDetails,String token);
    public ResponseEntity<MessageResponse> deleteOwnerById(Long ownerId,String token);
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurantsForUser();
}
