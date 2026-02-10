package com.cts.cartservice.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuResponseDto {
    private Long itemId;
    private Long restaurantId;
    private String itemName;
    private Integer price;
    private String restaurantName;
    private boolean availaible;
    private Integer quantity;
    private Integer estimatedItemsDelivered;
    private String category;
}
