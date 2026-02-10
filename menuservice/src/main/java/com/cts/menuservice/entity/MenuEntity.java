package com.cts.menuservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

@Table(
        name = "menu_items",
        uniqueConstraints = {
                @UniqueConstraint(name = "ux_restaurant_itemnamekey",
                        columnNames = {"restaurantId", "itemNameKey"})
        })

public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;
    private Long restaurantId;
    private String itemName;
    private String itemNameKey;
    private Integer price;
    private Integer estimatedItemsDelivered;
    private boolean availaible;
    private String category;
}
