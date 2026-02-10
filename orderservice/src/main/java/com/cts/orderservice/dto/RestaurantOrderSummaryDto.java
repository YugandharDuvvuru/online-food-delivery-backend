package com.cts.orderservice.dto;

import com.cts.orderservice.entity.DeliveryAddress;
import java.time.LocalDateTime;
import java.util.List;

public class RestaurantOrderSummaryDto {

    private Long orderId;
    private String invoiceNumber;
    private LocalDateTime orderTime;
    private String status;
    private Integer subtotal; // amount for THIS restaurant
    private DeliveryAddress address;
    private List<RestaurantOrderItemDto> items;

    public RestaurantOrderSummaryDto() {
    }
    public RestaurantOrderSummaryDto(Long orderId,
                                     String invoiceNumber,
                                     LocalDateTime orderTime,
                                     String status,
                                     Integer subtotal,
                                     DeliveryAddress address,
                                     List<RestaurantOrderItemDto> items
                                     ) {
        this.orderId = orderId;
        this.invoiceNumber = invoiceNumber;
        this.orderTime = orderTime;
        this.status = status;
        this.subtotal = subtotal;
        this.address = address;
        this.items = items;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public DeliveryAddress getAddress() {
        return address;
    }

    public void setAddress(DeliveryAddress address) {
        this.address = address;
    }

    public List<RestaurantOrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<RestaurantOrderItemDto> items) {
        this.items = items;
    }
    @Override
    public String toString() {
        return "RestaurantOrderSummaryDto{" +
                "orderId=" + orderId +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", orderTime=" + orderTime +
                ", status='" + status + '\'' +
                ", subtotal=" + subtotal +
                ", address=" + address +
                ", items=" + items +
                '}';
    }
}

