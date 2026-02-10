package com.cts.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDto {
    private Long userId;
    private Long itemId;
    private Integer quantity;
    private LocalDateTime addedAt;
}
