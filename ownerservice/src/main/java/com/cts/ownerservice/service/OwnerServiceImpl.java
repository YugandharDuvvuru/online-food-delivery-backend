package com.cts.ownerservice.service;

import com.cts.ownerservice.client.AuthAndMenuClient;
import com.cts.ownerservice.dto.MessageResponse;
import com.cts.ownerservice.dto.OwnerDetailsDto;
import com.cts.ownerservice.dto.RestaurantDetailsDto;
import com.cts.ownerservice.dto.RestaurantResponseDto;
import com.cts.ownerservice.entity.OwnerEntity;
import com.cts.ownerservice.entity.RestaurantEntity;
import com.cts.ownerservice.exceptions.AuthenticationException;
import com.cts.ownerservice.exceptions.DuplicateEmailException;
import com.cts.ownerservice.exceptions.NoRestaurantFoundException;
import com.cts.ownerservice.exceptions.RestaurantsNotFoundException;
import com.cts.ownerservice.repository.OwnerRepository;
import com.cts.ownerservice.repository.RestaurantRepository;
import com.cts.ownerservice.util.RestaurantNameNormalizer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService{
    @Autowired
    private AuthAndMenuClient client;
    @Autowired
    private RestaurantNameNormalizer nameNormalizer;
    @Autowired
    private RestaurantRepository restaurantRepo;
    @Autowired
    private OwnerRepository ownerRepo;
    @Override
    public ResponseEntity<MessageResponse> saveOwnerDetails(OwnerEntity ownerEntity) {
        OwnerEntity owner=ownerRepo.save(ownerEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Owner saved successfully"));
    }
    @Override
    public ResponseEntity<OwnerDetailsDto> getOwnerDetailsById(Long id) {
        Optional<OwnerEntity> ownerDetails = ownerRepo.findByAuthId(id);
        if (ownerDetails.isEmpty()) {
            throw new AuthenticationException("Owner not found");
        }
        // Get entity
        OwnerEntity owner = ownerDetails.get();
        // Manually map entity fields to DTO
        OwnerDetailsDto dto = new OwnerDetailsDto();
        dto.setOwnerId(owner.getOwnerId());
        dto.setEmail(owner.getEmail());
        dto.setMobileNumber(owner.getMobileNumber());
        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());

        return ResponseEntity.ok(dto);
    }
    @Override
    public ResponseEntity<MessageResponse> addRestaurants(Long ownerId, RestaurantDetailsDto restaurantDetails) {
        Optional<OwnerEntity> ownerExsists=ownerRepo.findByOwnerId(ownerId);
        if(!ownerExsists.isPresent()){
            throw new AuthenticationException("Owner not found");
        }
        OwnerEntity owner=ownerExsists.get();
        RestaurantEntity restaurant=new RestaurantEntity();
        restaurant.setOwner(owner);
        restaurant.setName(restaurantDetails.getName());
        String nameKey = nameNormalizer.normalize(restaurantDetails.getName());
        restaurant.setNameKey(nameKey);
        restaurant.setArea(restaurantDetails.getArea());
        restaurant.setCity(restaurantDetails.getCity());
        restaurant.setOpen(restaurantDetails.isOpen());
        restaurantRepo.save(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Restaurant saved successfully"));
    }
    @Override
    public ResponseEntity<List<RestaurantResponseDto>> getResataurantsOfOwner(Long ownerId) {
        List<RestaurantEntity> restaurants=restaurantRepo.findByOwner_OwnerId(ownerId);
        if(restaurants==null || restaurants.isEmpty()){
            throw new RestaurantsNotFoundException("No restaurants found for ownerID "+ownerId);
        }
        List<RestaurantResponseDto> restaurantDtos = new ArrayList<>(restaurants.size());
        for (RestaurantEntity restaurant : restaurants) {
            RestaurantResponseDto dto = new RestaurantResponseDto();
            dto.setRestaurantId(restaurant.getRestaurantId());
            dto.setName(restaurant.getName());
            dto.setCity(restaurant.getCity());
            dto.setArea(restaurant.getArea());
            dto.setOpen(restaurant.isOpen());
            restaurantDtos.add(dto);
        }
        // Return 200 OK with the list
        return ResponseEntity.ok(restaurantDtos);
    }
    @Override
    public ResponseEntity<RestaurantResponseDto> getRestaurantsById(Long restaurantId) {
        Optional<RestaurantEntity> restaurant=restaurantRepo.findByRestaurantId(restaurantId);
        if(restaurant.isEmpty()){
            throw new NoRestaurantFoundException("No restaurant found with Id "+restaurantId);
        }
        RestaurantEntity restaurantFound=restaurant.get();
        RestaurantResponseDto dto=new RestaurantResponseDto();
        dto.setRestaurantId(restaurantFound.getRestaurantId());
        dto.setName(restaurantFound.getName());
        dto.setCity(restaurantFound.getCity());
        dto.setArea(restaurantFound.getArea());
        dto.setOpen(restaurantFound.isOpen());
        return ResponseEntity.ok(dto);
    }
    @Override
    public ResponseEntity<MessageResponse> toggleOpenStatus(boolean status, Long restaurantId) {
        Optional<RestaurantEntity> restaurant = restaurantRepo.findByRestaurantId(restaurantId);
        if (restaurant.isEmpty()) {
            throw new NoRestaurantFoundException("No restaurant found with Id " + restaurantId);
        }
        RestaurantEntity toggleRestaurant = restaurant.get();
        toggleRestaurant.setOpen(status);
        restaurantRepo.save(toggleRestaurant);
        return ResponseEntity.ok(new MessageResponse("Restaurant open status updated to: " + status));
    }

    @Override
    public ResponseEntity<List<RestaurantResponseDto>> searchRestaurantByName(String name) {
        String nameKey=nameNormalizer.normalize(name);
        List<RestaurantEntity> restaurants=restaurantRepo.searchByNameKeyContainsNative(nameKey);
        if(restaurants==null || restaurants.isEmpty()){
            throw new RestaurantsNotFoundException("No restaurants found search correctly");
        }
        List<RestaurantResponseDto> restaurantDtos = new ArrayList<>(restaurants.size());
        for (RestaurantEntity restaurant : restaurants) {
            RestaurantResponseDto dto = new RestaurantResponseDto();
            dto.setRestaurantId(restaurant.getRestaurantId());
            dto.setName(restaurant.getName());
            dto.setCity(restaurant.getCity());
            dto.setArea(restaurant.getArea());
            dto.setOpen(restaurant.isOpen());
            restaurantDtos.add(dto);
        }
        // Return 200 OK with the list
        return ResponseEntity.ok(restaurantDtos);
    }

    @Override
    public ResponseEntity<RestaurantResponseDto> updateRestaurantById(Long restaurantId, RestaurantDetailsDto restaurantDetails) {
        Optional<RestaurantEntity> restaurant=restaurantRepo.findByRestaurantId(restaurantId);
        if(restaurant==null || restaurant.isEmpty()){
            throw new NoRestaurantFoundException("No restaurant found with Id " + restaurantId);
        }
        RestaurantEntity restaurantEntity=restaurant.get();
        restaurantEntity.setName(restaurantDetails.getName());
        restaurantEntity.setCity(restaurantDetails.getCity());
        restaurantEntity.setArea(restaurantDetails.getArea());
        String nameKey = nameNormalizer.normalize(restaurantDetails.getName());
        restaurantEntity.setNameKey(nameKey);
        RestaurantEntity saved=restaurantRepo.save(restaurantEntity);
        return ResponseEntity.ok(new RestaurantResponseDto(restaurantEntity));
    }

    @Override
    public ResponseEntity<MessageResponse> deleteRestaurantById(Long restaurantId,String token) {
        Optional<RestaurantEntity> restaurant=restaurantRepo.findByRestaurantId(restaurantId);
        if(restaurant.isEmpty()){
            throw new NoRestaurantFoundException("No restaurant found with Id " + restaurantId);

        }
        client.deleteItemsOfRestaurant(token,restaurantId);
        restaurantRepo.deleteById(restaurantId);
        return ResponseEntity.ok(new MessageResponse("Restaurant with id "+restaurantId+" deleted successfully"));
    }
    @Override
    @Transactional
    //make sure to send only the changed details from the frontend (If the email is unchanged and you thet email it will give conflict)
    public ResponseEntity<OwnerDetailsDto> updateOwnerById(Long ownerId, OwnerDetailsDto ownerDetails,String token) {
        OwnerEntity owner = ownerRepo.findByOwnerId(ownerId)
                .orElseThrow(() -> new AuthenticationException("Owner not found"));
        boolean emailChanged = ownerDetails.getEmail() != null
                && !ownerDetails.getEmail().equals(owner.getEmail());
        if (emailChanged) {
            System.out.println("email changed");
            ResponseEntity<MessageResponse> authResp = client.updateEmail(token,owner.getAuthId(), ownerDetails.getEmail());
            String message = (authResp != null ? authResp.getBody().getMessage() : null);
            if ("Email already registered".equals(message)) {
               throw new DuplicateEmailException("Email already registered try with another Mail.");
            }
            owner.setEmail(ownerDetails.getEmail());
        }
        if (ownerDetails.getFirstName() != null) {
            owner.setFirstName(ownerDetails.getFirstName());
        }
        if (ownerDetails.getLastName() != null) {
            owner.setLastName(ownerDetails.getLastName());
        }
        if (ownerDetails.getMobileNumber() != null) {
            owner.setMobileNumber(ownerDetails.getMobileNumber());
        }
        OwnerEntity saved = ownerRepo.save(owner);
        OwnerDetailsDto responseDto = new OwnerDetailsDto(saved);
        System.out.println("how are you");
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<MessageResponse> deleteOwnerById(Long ownerId,String token) {
        Optional<OwnerEntity> userEntity = ownerRepo.findByOwnerId(ownerId);
        if(userEntity.isEmpty()){
            throw new AuthenticationException("User not found");
        }
        MessageResponse response=client.deleteUserByAuthId(token,userEntity.get().getAuthId()).getBody();
        if(response.getMessage().equals("User Deleted Successfully.")){
            ownerRepo.deleteById(ownerId);
            return ResponseEntity.ok(new MessageResponse(response.getMessage()));
        }
        else {
            return ResponseEntity.ok(new MessageResponse("Error ocuures while deleting the user"));
        }
    }
	@Override
	public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurantsForUser() {
		List<RestaurantEntity> listOfRest = restaurantRepo.findAll();
		List<RestaurantResponseDto> restaurantResponse = new ArrayList<>();
		for(RestaurantEntity e: listOfRest) {
			
			RestaurantResponseDto dto = new RestaurantResponseDto(e);
			restaurantResponse.add(dto);
			
		}
		return ResponseEntity.ok(restaurantResponse);
	}
}


