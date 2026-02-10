package com.cts.orderservice.dto;


import com.cts.orderservice.entity.DeliveryAddress;


public class OrderRequestDto {

    private DeliveryAddress deliveryAddress;

    // No-args constructor
    public OrderRequestDto() {
    }

    // All-args constructor
    public OrderRequestDto(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
