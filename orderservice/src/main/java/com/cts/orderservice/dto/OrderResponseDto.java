package com.cts.orderservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.cts.orderservice.entity.DeliveryAddress;
import com.cts.orderservice.entity.OrderEntity;
import com.cts.orderservice.entity.OrderItems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDto {

    private Long orderId;
    private Long userId;
    private Integer totalAmount;
    private String invoiceNumber;
    private String orderStatus;
    private LocalDateTime orderTime;
    private List<OrderItems> orderItems;
    private DeliveryAddress address;

    // No-args constructor
    public OrderResponseDto() {
    }

    // All-args constructor
    public OrderResponseDto(Long orderId,
                            Long userId,
                            Integer totalAmount,
                            String invoiceNumber,
                            String orderStatus,
                            LocalDateTime orderTime,
                            List<OrderItems> orderItems,
                            DeliveryAddress address) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.invoiceNumber = invoiceNumber;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.orderItems = orderItems;
        this.address = address;
    }

    // Mapping constructor from OrderEntity
    public OrderResponseDto(OrderEntity entity) {
        this.orderId = entity.getOrderId();
        this.userId = entity.getUserId();
        this.totalAmount = entity.getTotalAmount();
        this.invoiceNumber = entity.getInvoiceNumber();
        this.orderStatus = entity.getOrderStatus();
        this.orderTime = entity.getOrderTime();
        this.orderItems = entity.getOrderItems();
        this.address = entity.getAddress();
    }

    // Getters and Setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public DeliveryAddress getAddress() {
        return address;
    }

    public void setAddress(DeliveryAddress address) {
        this.address = address;
    }
}

