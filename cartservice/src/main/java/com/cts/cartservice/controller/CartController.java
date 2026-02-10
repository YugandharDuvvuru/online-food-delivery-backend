package com.cts.cartservice.controller;


import com.cts.cartservice.dto.CartItemDto;
import com.cts.cartservice.dto.MenuResponseDto;
import com.cts.cartservice.dto.MessageResponse;
import com.cts.cartservice.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add/item/to-cart")
    public ResponseEntity<MessageResponse> addItemToCart(HttpServletRequest request, @RequestBody CartItemDto cartItemDto){
        String authHeader=request.getHeader("Authorization");
      return cartService.addItemToCart(cartItemDto,authHeader);
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/clear/cart/{userId}")
    public ResponseEntity<MessageResponse> deleteAllCartItems(@PathVariable Long userId){
     return cartService.clearCartItems(userId);
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/cart/item")
    public ResponseEntity<MessageResponse> updateCartItem(HttpServletRequest request,@RequestBody CartItemDto cartItemDto){
        String authHeader=request.getHeader("Authorization");
      return cartService.updateCartitem(cartItemDto,authHeader);
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/item/{userId}/{itemId}")
    public ResponseEntity<MessageResponse> deleteItemByUserIdAndItemId(@PathVariable Long userId,@PathVariable Long itemId){
        return cartService.deleteItemByUserIdAndItemId(userId,itemId);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get/all-cart-items/for/user-with/{userId}")
    public ResponseEntity<List<MenuResponseDto>> getAllCartItemsForUser(HttpServletRequest request,@PathVariable Long userId){
        String authHeader=request.getHeader("Authorization");
        return cartService.getAllCartItemsForUser(userId,authHeader);
    }
}
