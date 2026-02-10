package com.cts.menuservice.service;

import com.cts.menuservice.client.RestaurantClient;
import com.cts.menuservice.dto.*;
import com.cts.menuservice.entity.MenuEntity;
import com.cts.menuservice.exceptions.DuplicateItemException;
import com.cts.menuservice.exceptions.ItemNotFoundException;
import com.cts.menuservice.exceptions.MenuItemsNotFoundException;
import com.cts.menuservice.repository.MenuRepository;
import com.cts.menuservice.util.MenuItemNameNormalizer;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements  MenuService{
    @Autowired
    private RestaurantClient restaurantClient;
    @Autowired
    private MenuItemNameNormalizer Normalizer;
    @Autowired
    private MenuRepository menuRepo;
    @Override
    public ResponseEntity<MessageResponse> addItemToMenu(Long restaurantId, MenuDto menuDto) {
        String normalizedKey = MenuItemNameNormalizer.normalize(menuDto.getItemName());
        boolean itemExists=menuRepo.existsByRestaurantIdAndItemNameKey(restaurantId,normalizedKey);

        if (itemExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Item already exists in this restaurant: " + menuDto.getItemName()));
        }

        // Set the normalized key in the entity before saving
        MenuEntity entity = new MenuEntity();
        entity.setRestaurantId(restaurantId);
        entity.setItemName(menuDto.getItemName().trim());
        entity.setItemNameKey(normalizedKey);
        entity.setPrice(menuDto.getPrice());
        entity.setEstimatedItemsDelivered(menuDto.getEstimatedItemsDelivered());
        entity.setAvailaible(menuDto.isAvailaible());
        entity.setCategory(menuDto.getCategory());

        menuRepo.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Item added successfully: " + menuDto.getItemName()));

    }


    @Override
    public ResponseEntity<List<MenuResponseDto>> getItemsFromRestaurant(Long restaurantId) {
        // Fetch menu items for the given restaurant
        List<MenuEntity> menuItems = menuRepo.findByRestaurantId(restaurantId);

        // Throw if none found (keep consistent with your other methods)
        if (menuItems == null || menuItems.isEmpty()) {
            throw new MenuItemsNotFoundException("No menu items found for restaurantId " + restaurantId);
        }

        // Map entities to DTOs manually (same style as your UserAddress mapping)
        List<MenuResponseDto> menuDtos = new ArrayList<>(menuItems.size());
        for (MenuEntity item : menuItems) {
            MenuResponseDto dto = new MenuResponseDto();
            dto.setItemId(item.getItemId());
            dto.setRestaurantId(item.getRestaurantId());
            dto.setItemName(item.getItemName());
            dto.setPrice(item.getPrice());
            dto.setAvailaible(item.isAvailaible());
            dto.setEstimatedItemsDelivered(item.getEstimatedItemsDelivered());
            dto.setCategory(item.getCategory());
            menuDtos.add(dto);
        }

        // Return 200 OK with list
        return ResponseEntity.ok(menuDtos);
    }

    @Override
    public ResponseEntity<MenuResponseDto> getParticularItemDetails(Long itemId) {
        Optional<MenuEntity> item=menuRepo.findByItemId(itemId) ;
        if(item.isEmpty()){
            throw new ItemNotFoundException("Item not found with itemId "+itemId);
        }
        MenuEntity itemDetails=item.get();
        MenuResponseDto dto=new MenuResponseDto();
        dto.setItemId(itemDetails.getItemId());
        dto.setItemName(itemDetails.getItemName());
        dto.setPrice(itemDetails.getPrice());
        dto.setAvailaible(itemDetails.isAvailaible());
        dto.setRestaurantId(itemDetails.getRestaurantId());
        dto.setEstimatedItemsDelivered(itemDetails.getEstimatedItemsDelivered());
        dto.setCategory(itemDetails.getCategory());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<MessageResponse> toggleAvailbility(Long itemId, boolean status) {
        Optional<MenuEntity> item=menuRepo.findByItemId(itemId);
        if(item.isEmpty()){
            throw new ItemNotFoundException("Item not found with itemId "+ itemId);
        }
        MenuEntity itemDetails=item.get();
        itemDetails.setAvailaible(status);
        System.out.println(itemDetails.isAvailaible());
        menuRepo.save(itemDetails);
        return ResponseEntity.ok(new MessageResponse("Availaiblity updated to "+status));
    }



    @Override
    public ResponseEntity<MenuResponseDto> updateItem(Long itemId, MenuDto menuDto) {
        // 1) Fetch existing item or fail fast
        MenuEntity item = menuRepo.findByItemId(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with itemId " + itemId));

        // 2) Check if name actually changed

        String newDisplayName = menuDto.getItemName(); // raw name from client
        String newName = MenuItemNameNormalizer.normalize(newDisplayName); // normalized

        String currentKey = item.getItemNameKey(); // already normalized in DB

        boolean nameChanged = (currentKey == null && newName!= null)
                || (currentKey != null && !currentKey.equals(newName));


        // 3) If name changed, normalize and check for duplicates within the same restaurant
        if (nameChanged) {
            String newKey = MenuItemNameNormalizer.normalize(newName);
            boolean duplicate = menuRepo.existsByRestaurantIdAndItemNameKey(item.getRestaurantId(), newKey);
            if (duplicate) {
                throw new DuplicateItemException(
                        "Duplicate Item Found in restaurant with restaurantID " + item.getRestaurantId()
                );
            }
            // Update name + normalized key ONLY when name changed
            item.setItemName(newName);
            item.setItemNameKey(newKey);
        }

        // 4) Update other fields (do not touch item_name_key here)
        item.setPrice(menuDto.getPrice());
        item.setCategory(menuDto.getCategory());
        //item.setEstimatedItemsDelivered(menuDto.getEstimatedItemsDelivered());

        // 5) Save
        MenuEntity saved = menuRepo.save(item);

        // 6) Map to response
        MenuResponseDto response = new MenuResponseDto();
        response.setItemId(saved.getItemId());
        response.setRestaurantId(saved.getRestaurantId());
        response.setItemName(saved.getItemName());
        response.setPrice(saved.getPrice());
        response.setAvailaible(saved.isAvailaible());
        response.setCategory(saved.getCategory());
        response.setEstimatedItemsDelivered(saved.getEstimatedItemsDelivered());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<MenuAndRestaurantDto>> searchItemByName(String name,String token) {
        String normalizedKey = MenuItemNameNormalizer.normalize(name);
        List<MenuEntity> itemFoundByName=menuRepo.searchByItemNameKeyContainsNative(name);
        if (itemFoundByName == null || itemFoundByName.isEmpty()) {
            throw new MenuItemsNotFoundException("No menu items found");
        }
        List<MenuAndRestaurantDto> itemDto=new ArrayList<>(itemFoundByName.size());
        for (MenuEntity item : itemFoundByName) {
            MenuAndRestaurantDto dto = new MenuAndRestaurantDto();
            dto.setItemId(item.getItemId());
            dto.setRestaurantId(item.getRestaurantId());
            dto.setItemName(item.getItemName());
            dto.setPrice(item.getPrice());
            dto.setAvailaible(item.isAvailaible());
            dto.setEstimatedItemsDelivered(item.getEstimatedItemsDelivered());
            dto.setCategory(item.getCategory());
            RestaurantResponseDto restaurantDto=restaurantClient.getRestaurantById(token,item.getRestaurantId()).getBody();
            dto.setName(restaurantDto.getName());
            dto.setArea(restaurantDto.getArea());
            dto.setCity(restaurantDto.getCity());
            dto.setOpen(restaurantDto.isOpen());
            itemDto.add(dto);
        }
        return ResponseEntity.ok(itemDto);

    }

    @Override
    public ResponseEntity<List<MenuAndRestaurantDto>> fitlterByCategoryAndPrice(FilterDto filterDto,String token) {
        List<MenuEntity> items;
        if (filterDto.getCategory() != null && filterDto.getPrice() != null) {
            items = menuRepo.findByCategoryAndPriceLessThanEqualNative(filterDto.getCategory(), filterDto.getPrice());
        } else if (filterDto.getCategory() != null) {
            items = menuRepo.findByCategoryNative(filterDto.getCategory());
        } else {
            items = menuRepo.findByPriceLessThanEqualNative(filterDto.getPrice());
        }

        List<MenuAndRestaurantDto> itemDto = new ArrayList<>(items.size());
        for (MenuEntity item : items) {
            MenuAndRestaurantDto dto = new MenuAndRestaurantDto();
            dto.setItemId(item.getItemId());
            dto.setRestaurantId(item.getRestaurantId());
            dto.setItemName(item.getItemName());
            dto.setPrice(item.getPrice());
            dto.setAvailaible(item.isAvailaible());
            dto.setCategory(item.getCategory());
            dto.setEstimatedItemsDelivered(item.getEstimatedItemsDelivered());
            RestaurantResponseDto restaurantDto = restaurantClient.getRestaurantById(token,item.getRestaurantId()).getBody();
            dto.setName(restaurantDto.getName());
            dto.setArea(restaurantDto.getArea());
            dto.setCity(restaurantDto.getCity());
            dto.setOpen(restaurantDto.isOpen());
            itemDto.add(dto);
        }
        return ResponseEntity.ok(itemDto);
    }

    @Override
    public ResponseEntity<MessageResponse> deleteItemById(Long itemId) {
        Optional<MenuEntity> item=menuRepo.findByItemId(itemId);
        if( item==null || item.isEmpty()){
            throw new ItemNotFoundException("Item not found with itemId "+itemId);
        }
        menuRepo.deleteById(itemId);
        return ResponseEntity.ok(new MessageResponse("Menu Item with id "+itemId+" deleted successfully"));
    }

    @Override
    public ResponseEntity<MessageResponse> updateEstimatedItemsDelivered(Long itemId,Integer itemsDelivered) {
        Optional<MenuEntity> itemToUpdate=menuRepo.findByItemId(itemId) ;
        if(itemToUpdate.isEmpty()){
            throw new ItemNotFoundException("Item not found with itemId "+ itemId);
        }
        MenuEntity entity=itemToUpdate.get();
        entity.setEstimatedItemsDelivered(itemsDelivered);
        menuRepo.save(entity);
        return ResponseEntity.ok(new MessageResponse("Items to be delivered successfully updated"));

    }


	@Override
	@Transactional
	public void deleteItemsOfRestaurant(Long restaurantId) {
		menuRepo.deleteByRestaurantId(restaurantId);
		
		
	}
}
