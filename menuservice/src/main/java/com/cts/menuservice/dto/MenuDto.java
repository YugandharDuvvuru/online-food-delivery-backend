package com.cts.menuservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuDto {
    private String itemName;
    private Integer price;
    private boolean availaible;
    private Integer estimatedItemsDelivered;
    private String category;
}
