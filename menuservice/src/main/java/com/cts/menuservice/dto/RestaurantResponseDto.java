package com.cts.menuservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDto {
    private Long restaurantId;
    private String name;
    private String city;
    private String area;
    private boolean open;
}
