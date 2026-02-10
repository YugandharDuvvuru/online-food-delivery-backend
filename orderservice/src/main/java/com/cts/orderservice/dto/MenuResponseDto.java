package com.cts.orderservice.dto;


public class MenuResponseDto {

    private Long itemId;
    private Long restaurantId;
    private String itemName;
    private Integer price;
    private boolean availaible;   // consider renaming to 'available'
    private Integer quantity;
    private Integer estimatedItemsDelivered;
    private String category;

    // No-args constructor
    public MenuResponseDto() {
    }

    // All-args constructor
    public MenuResponseDto(Long itemId,
                           Long restaurantId,
                           String itemName,
                           Integer price,
                           boolean availaible,
                           Integer quantity,
                           Integer estimatedItemsDelivered,
                           String category) {
        this.itemId = itemId;
        this.restaurantId = restaurantId;
        this.itemName = itemName;
        this.price = price;
        this.availaible = availaible;
        this.quantity = quantity;
        this.estimatedItemsDelivered = estimatedItemsDelivered;
        this.category = category;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean isAvailaible() {
        return availaible;
    }

    public void setAvailaible(boolean availaible) {
        this.availaible = availaible;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getEstimatedItemsDelivered() {
        return estimatedItemsDelivered;
    }

    public void setEstimatedItemsDelivered(Integer estimatedItemsDelivered) {
        this.estimatedItemsDelivered = estimatedItemsDelivered;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

