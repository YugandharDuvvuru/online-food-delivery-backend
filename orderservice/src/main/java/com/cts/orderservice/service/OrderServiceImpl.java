package com.cts.orderservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cts.orderservice.dto.*;
import com.cts.orderservice.exception.OrderNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.orderservice.Repository.OrderRepository;
import com.cts.orderservice.client.CartAndMenuClient;
import com.cts.orderservice.entity.OrderEntity;
import com.cts.orderservice.entity.OrderItems;
import com.cts.orderservice.exception.CartEmptyException;
import com.cts.orderservice.exception.ItemUnavailableException;
import com.cts.orderservice.exception.PaymentFailedException;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private CartAndMenuClient client;

	@Override
    @Transactional
	public ResponseEntity<OrderResponseDto> placeOrder(Long userId, OrderRequestDto request,String token) {
		List<MenuResponseDto> cartItems=client.getAllCartItemsForUser(token,userId).getBody();
		if(cartItems.size()==0) {
            System.out.println("cart items is empty");
			throw new CartEmptyException("Cart is empty");
		}
		Integer total=0;
		List<OrderItems> orderItems = new ArrayList<>();
		for(MenuResponseDto item:cartItems) {
			if(item.getEstimatedItemsDelivered()<item.getQuantity()) {
				throw new ItemUnavailableException("Item is not availaible at the moment (out of stock) for "+item.getItemName()+" in this restuarant");
			}
            client.updateEstimatedItemsDelivered(token,item.getItemId(),item.getEstimatedItemsDelivered()-item.getQuantity());
			OrderItems items=new OrderItems();
			items.setItemId(item.getItemId());
			items.setItemName(item.getItemName());
			items.setRestaurantId(item.getRestaurantId());
			items.setPrice(item.getPrice());
			items.setCategory(item.getCategory());
			items.setAvailaible(item.isAvailaible());
			items.setQuantity(item.getQuantity());
			orderItems.add(items);
			total += item.getPrice() * item.getQuantity();
		}
        System.out.println(orderItems);
		boolean paymentSuccess = true;
        if (!paymentSuccess) {
            throw new PaymentFailedException("Payment failed");
        }
        OrderEntity entity=new OrderEntity();
        entity.setUserId(userId);
        entity.setAddress(request.getDeliveryAddress());
        entity.setInvoiceNumber(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        entity.setTotalAmount(total);
        entity.setOrderStatus("CREATED");
        entity.setAmountPaid(true);
        entity.setOrderTime(LocalDateTime.now());
        orderItems.forEach(i->i.setOrder(entity));
        entity.setOrderItems(orderItems);
        orderRepo.save(entity);
        OrderResponseDto dto=new OrderResponseDto(entity);
        client.deleteAllCartItems(token,userId);
        
		return ResponseEntity.ok(dto);
	}

    @Override
    public ResponseEntity<MessageResponse> toggleOrderStatus(String status, Long orderId) {
        OrderEntity order=orderRepo.findByOrderId(orderId);
        if(order==null){
            throw new OrderNotFoundException("Order not found with orderId: "+orderId);
        }
        order.setOrderStatus(status);
        orderRepo.save(order);
        return ResponseEntity.ok(new MessageResponse("Order status updated successfully"));
    }

    @Override
    public ResponseEntity<OrderResponseDto> getOrderDetails(Long orderId) {
        OrderEntity order=orderRepo.findByOrderId(orderId);
        if(order==null){
            throw new OrderNotFoundException("Order not found with orderId: "+orderId);
        }
        OrderResponseDto dto=new OrderResponseDto(order);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<MessageResponse> cancelOrder(Long orderId) {
        OrderEntity order=orderRepo.findByOrderId(orderId);
        if(order==null){
            throw new OrderNotFoundException("Order not found with orderId: "+orderId);
        }
         orderRepo.deleteById(orderId);
        return ResponseEntity.ok(new MessageResponse("order cancelled successfully"));
    }
    @Transactional
    @Override
    public ResponseEntity<List<RestaurantOrderSummaryDto>> showIncommingOrdersToRestaurant(Long restaurantId) {
        List<OrderEntity> orders=orderRepo.findCreatedOrdersForRestaurant(restaurantId);

        return ResponseEntity.ok(orders.stream().map(order -> {
            // Keep only items for this restaurant
            List<OrderItems> restaurantItems = order.getOrderItems().stream()
                    .filter(i -> restaurantId.equals(i.getRestaurantId()))
                    .toList();

            int subtotal = restaurantItems.stream()
                    .mapToInt(i -> (i.getPrice() != null ? i.getPrice() : 0) *
                            (i.getQuantity() != null ? i.getQuantity() : 0))
                    .sum();

            List<RestaurantOrderItemDto> itemDtos = restaurantItems.stream().map(i ->
                    new RestaurantOrderItemDto(
                            i.getItemId(),
                            i.getItemName(),
                            i.getPrice(),
                            i.getQuantity(),
                            i.getCategory()
                    )
            ).toList();

            RestaurantOrderSummaryDto summary = new RestaurantOrderSummaryDto();
            summary.setOrderId(order.getOrderId());
            summary.setInvoiceNumber(order.getInvoiceNumber());
            summary.setOrderTime(order.getOrderTime());
            summary.setStatus(order.getOrderStatus());
            summary.setSubtotal(subtotal);
            summary.setAddress(order.getAddress());
            summary.setItems(itemDtos);
            return summary;
        }).toList());
    }

    @Override
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(Long userId) {
        List<OrderEntity> orders=orderRepo.findByUserId(userId);
        if(orders.size()==0){
            throw new OrderNotFoundException("No orders placed yet history is empty");
        }
        List<OrderResponseDto> orderResponseDto=new ArrayList<>();
        for(OrderEntity order:orders){
            OrderResponseDto responseDto=new OrderResponseDto(order);
            orderResponseDto.add(responseDto);
        }
        return ResponseEntity.ok(orderResponseDto);
    }


}

