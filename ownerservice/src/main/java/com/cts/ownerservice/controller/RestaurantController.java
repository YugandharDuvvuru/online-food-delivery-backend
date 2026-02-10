package com.cts.ownerservice.controller;

import com.cts.ownerservice.dto.MessageResponse;
import com.cts.ownerservice.dto.RestaurantDetailsDto;
import com.cts.ownerservice.dto.RestaurantResponseDto;
import com.cts.ownerservice.service.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurant")
@CrossOrigin
public class RestaurantController {
    @Autowired
    private OwnerService ownerService;
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/add-restaurant-details/{ownerId}")
    public ResponseEntity<MessageResponse> addRestaurant(@PathVariable Long ownerId, @RequestBody RestaurantDetailsDto restaurantDetailsDto){
        return ownerService.addRestaurants(ownerId,restaurantDetailsDto);
    }
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/get-restaurant-details/{ownerId}")
    public ResponseEntity<List<RestaurantResponseDto>> getRestaurantsOfOwner(@PathVariable Long ownerId){
        return ownerService.getResataurantsOfOwner(ownerId);
    }
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    @GetMapping("/get-restaurant/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Long restaurantId){
        return ownerService.getRestaurantsById(restaurantId);
    }
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/toggle-open-status/{status}/{restaurantId}")
    public ResponseEntity<MessageResponse> toggleOpenStatus(@PathVariable boolean status,@PathVariable Long restaurantId){
        return ownerService.toggleOpenStatus(status,restaurantId);
    }
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    @GetMapping("/get-restaurant/by-name/{restaurantName}")
    public ResponseEntity<List<RestaurantResponseDto>> getRestaurantsByName(@PathVariable String restaurantName){
     return ownerService.searchRestaurantByName(restaurantName);
    }
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/update/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> updateRestaurantById(@PathVariable Long restaurantId,@RequestBody RestaurantDetailsDto restaurantDetails){
     return  ownerService.updateRestaurantById(restaurantId,restaurantDetails);
    }
    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/delete/restaurant/{restaurantId}")
    public ResponseEntity<MessageResponse> detelteRestaurantById(HttpServletRequest request, @PathVariable Long restaurantId){
        String header=request.getHeader("Authorization");
        return ownerService.deleteRestaurantById(restaurantId,header);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-all-available")
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurantByUser(){
    	return ownerService.getAllRestaurantsForUser();
    }
    
    
}
