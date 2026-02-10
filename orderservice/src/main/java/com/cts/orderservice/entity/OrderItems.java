package com.cts.orderservice.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Fetch;

@Entity
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemsId;
    private Long itemId;
    private Long restaurantId;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private boolean availaible; // consider renaming to `available`
    private String category;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private OrderEntity order;

    // No-args constructor
    public OrderItems() {
    }

    // All-args constructor
    public OrderItems(Long orderItemsId,
                      Long itemId,
                      Long restaurantId,
                      String itemName,
                      Integer price,
                      Integer quantity,
                      boolean availaible,
                      String category,
                      OrderEntity order) {
        this.orderItemsId = orderItemsId;
        this.itemId = itemId;
        this.restaurantId = restaurantId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.availaible = availaible;
        this.category = category;
        this.order = order;
    }

    public Long getOrderItemsId() {
        return orderItemsId;
    }

    public void setOrderItemsId(Long orderItemsId) {
        this.orderItemsId = orderItemsId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailaible() {
        return availaible;
    }

    public void setAvailaible(boolean availaible) {
        this.availaible = availaible;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "orderItemsId=" + orderItemsId +
                ", itemId=" + itemId +
                ", restaurantId=" + restaurantId +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", availaible=" + availaible +
                ", category='" + category + '\'' +
                // avoid printing 'order' to prevent recursion or huge output
                '}';
    }
}