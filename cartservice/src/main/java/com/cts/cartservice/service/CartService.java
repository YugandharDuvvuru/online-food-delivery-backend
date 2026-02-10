package com.cts.cartservice.service;

import com.cts.cartservice.dto.CartItemDto;
import com.cts.cartservice.dto.MenuResponseDto;
import com.cts.cartservice.dto.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    public ResponseEntity<MessageResponse> addItemToCart(CartItemDto cartItemDto,String token);
    public ResponseEntity<MessageResponse> clearCartItems(Long userId);
    public ResponseEntity<MessageResponse> updateCartitem(CartItemDto cartItemDto,String token);
    public ResponseEntity<MessageResponse> deleteItemByUserIdAndItemId(Long userId,Long itemId);
    public ResponseEntity<List<MenuResponseDto>> getAllCartItemsForUser(Long userId,String token);
}
