package com.cts.ownerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantDetailsDto {
    private String name;
    private String city;
    private String area;
    private boolean open;
}
