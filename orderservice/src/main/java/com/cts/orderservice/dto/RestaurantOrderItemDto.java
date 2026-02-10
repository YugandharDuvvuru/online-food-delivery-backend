package com.cts.orderservice.dto;

public class RestaurantOrderItemDto {

    private Long itemId;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private String category;
    public RestaurantOrderItemDto() {
    }

    public RestaurantOrderItemDto(Long itemId,
                                  String itemName,
                                  Integer price,
                                  Integer quantity,
                                  String category) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
