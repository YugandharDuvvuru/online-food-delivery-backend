package com.cts.menuservice.service;

import com.cts.menuservice.dto.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface MenuService {
    public ResponseEntity<MessageResponse> addItemToMenu(Long restaurantId, MenuDto menuDto);
    public ResponseEntity<List<MenuResponseDto>> getItemsFromRestaurant(Long restaurantId);
    public ResponseEntity<MenuResponseDto> getParticularItemDetails(Long itemID);
    public ResponseEntity<MessageResponse> toggleAvailbility(Long itemId,boolean status);
    public ResponseEntity<MenuResponseDto> updateItem(Long itemId,MenuDto menuDto);
    public ResponseEntity<List<MenuAndRestaurantDto>> searchItemByName(String name,String token);
    public ResponseEntity<List<MenuAndRestaurantDto>> fitlterByCategoryAndPrice(FilterDto filterDto,String token);
    public ResponseEntity<MessageResponse> deleteItemById(Long itemId);
    public ResponseEntity<MessageResponse> updateEstimatedItemsDelivered(Long itemId,Integer itemsDelivered);
    public void deleteItemsOfRestaurant(Long restaurantId);

}
