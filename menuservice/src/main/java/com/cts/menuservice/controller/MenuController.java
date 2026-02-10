package com.cts.menuservice.controller;

import com.cts.menuservice.dto.*;
import com.cts.menuservice.service.MenuService;
import com.cts.menuservice.service.MenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/add-item/{restaurantId}")
    public ResponseEntity<MessageResponse> addItemsToMenu(@PathVariable Long restaurantId, @RequestBody MenuDto menuDto) {
        return menuService.addItemToMenu(restaurantId, menuDto);
    }
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    @GetMapping("/get-items/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuResponseDto>> getItemsFromRestaurant(@PathVariable Long restaurantId) {
        return menuService.getItemsFromRestaurant(restaurantId);
    }
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    @GetMapping("/get-item/details/{itemId}")
    public ResponseEntity<MenuResponseDto> getParticularItemDetails(@PathVariable Long itemId) {
        return menuService.getParticularItemDetails(itemId);
    }
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/toggle/availaibility/item/{itemId}/{status}")
    public ResponseEntity<MessageResponse> toggleAvailbility(@PathVariable Long itemId, @PathVariable boolean status) {
    	System.out.println(status);
    	return menuService.toggleAvailbility(itemId, status);
    }
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/update/item-details/{itemId}")
    public ResponseEntity<MenuResponseDto> updateItem(@PathVariable Long itemId, @RequestBody MenuDto menuDto) {
        return menuService.updateItem(itemId, menuDto);
    }
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    @GetMapping("/get-item-by-name/{name}")
    public ResponseEntity<List<MenuAndRestaurantDto>> searchItem(HttpServletRequest request, @PathVariable String name) {
        String authHeader=request.getHeader("Authorization");
        return menuService.searchItemByName(name,authHeader);
    }
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    @GetMapping("/filter/item-by/category-or-price")
    public ResponseEntity<List<MenuAndRestaurantDto>> filterItemsBasedOnCategoryAndPrice(HttpServletRequest request,@RequestBody FilterDto filterDto) {
        String authHeader=request.getHeader("Authorization");
        return menuService.fitlterByCategoryAndPrice(filterDto,authHeader);
    }
    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/remove/item/{itemId}")
    public ResponseEntity<MessageResponse> deleteItemById(@PathVariable Long itemId) {
        return menuService.deleteItemById(itemId);
    }
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/update/no-of-items-delivered/{itemId}/{estimatedItemsDelivered}")
    public ResponseEntity<MessageResponse> updateEstimatedItemsDelivered(@PathVariable Long itemId,@PathVariable Integer estimatedItemsDelivered){
        return menuService.updateEstimatedItemsDelivered(itemId,estimatedItemsDelivered);
    }
    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/delete/items/by/{restaurantId}")
    public void deleteItemsOfRestaurant(@PathVariable Long restaurantId) {
    	menuService.deleteItemsOfRestaurant(restaurantId);
    }
}
