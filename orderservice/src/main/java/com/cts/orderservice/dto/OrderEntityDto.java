package com.cts.orderservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.cts.orderservice.entity.DeliveryAddress;
import com.cts.orderservice.entity.OrderItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntityDto {
	private Long userId;
	private Integer totalAmount;
	private String status;
	private LocalDateTime orderTime;
	private List<OrderItems> orderItems;
	private DeliveryAddress address;
	
}
