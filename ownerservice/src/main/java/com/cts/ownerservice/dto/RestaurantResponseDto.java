package com.cts.ownerservice.dto;

import com.cts.ownerservice.entity.RestaurantEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantResponseDto {
    private Long restaurantId;
    private String name;
    private String city;
    private String area;
    private boolean open;
    public RestaurantResponseDto(RestaurantEntity entity){
        this.restaurantId=entity.getRestaurantId();
        this.name=entity.getName();
        this.area= entity.getArea();
        this.city=entity.getCity();
        this.open=entity.isOpen();
    }
}
