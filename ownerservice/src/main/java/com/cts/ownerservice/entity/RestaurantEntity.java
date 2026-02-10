package com.cts.ownerservice.entity;

import com.cts.ownerservice.dto.RestaurantDetailsDto;
import com.cts.ownerservice.dto.RestaurantResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long restaurantId;
    private String name;
    private String nameKey;
    private String city;
    private String area;
    private boolean open;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;
}
