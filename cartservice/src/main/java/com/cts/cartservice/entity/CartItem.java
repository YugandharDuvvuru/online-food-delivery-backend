package com.cts.cartservice.entity;

import com.cts.cartservice.dto.CartItemDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;
    private Long userId;
    private Long itemId;
    private Integer quantity;
    private LocalDateTime addedAt;
    public CartItem(CartItemDto cartItemDto){
        this.userId=cartItemDto.getUserId();
        this.itemId=cartItemDto.getItemId();
        this.quantity=cartItemDto.getQuantity();
    }
}
