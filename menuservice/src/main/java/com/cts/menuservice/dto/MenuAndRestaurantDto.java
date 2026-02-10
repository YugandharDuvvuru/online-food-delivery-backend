package com.cts.menuservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuAndRestaurantDto {
    private Long itemId;
    private Long restaurantId;
    private String itemName;
    private Integer price;
    private boolean availaible;
    private Integer estimatedItemsDelivered;
    private String category;
    private String name;
    private String city;
    private String area;
    private boolean open;
}
