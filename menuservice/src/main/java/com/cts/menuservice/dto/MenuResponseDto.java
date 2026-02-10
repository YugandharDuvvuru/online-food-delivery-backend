package com.cts.menuservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    //@JsonProperty("available")
    private boolean availaible;
    private Integer estimatedItemsDelivered;
    private String category;
}
