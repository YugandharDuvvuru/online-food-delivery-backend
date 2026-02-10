package com.cts.cartservice.service;

import com.cts.cartservice.client.MenuAndRestaurantClient;
import com.cts.cartservice.dto.CartItemDto;
import com.cts.cartservice.dto.MenuResponseDto;
import com.cts.cartservice.dto.MessageResponse;
import com.cts.cartservice.dto.RestaurantResponseDto;
import com.cts.cartservice.entity.CartItem;
import com.cts.cartservice.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private MenuAndRestaurantClient client;
    @Autowired
    private CartRepository cartRepo;
    @Transactional
    @Override
    public ResponseEntity<MessageResponse> addItemToCart(CartItemDto cartItemDto,String token) {
        MenuResponseDto menuDeatils=client.getParticularItemDetails(token,cartItemDto.getItemId()).getBody();
        if(!menuDeatils.isAvailaible()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Item is unavailable today."));
        }
//        if(cartItemDto.getQuantity()<1){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity should be atleast one");
//        }
//        if(cartItemDto.getQuantity()>menuDeatils.getEstimatedItemsDelivered()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Items are out of stock today.Available Items are : "+menuDeatils.getEstimatedItemsDelivered());
//        }
        Optional<CartItem> existingItem = cartRepo.findByUserIdAndItemId(cartItemDto.getUserId(), cartItemDto.getItemId());

        if (existingItem.isPresent()) {
            CartItem existing = existingItem.get();

            // Calculate the new total quantity we want in the cart
            int newTotal = existing.getQuantity() + cartItemDto.getQuantity();

            // Validate against today's available stock
            if (newTotal > menuDeatils.getEstimatedItemsDelivered()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("Items are out of stock today. " +
                                "In cart: " + existing.getQuantity() +
                                ", Requested add: " + cartItemDto.getQuantity() +
                                ", Available Items are: " + menuDeatils.getEstimatedItemsDelivered()));
            }

            // Update the existing cart item quantity and timestamp
            existing.setQuantity(newTotal);
            existing.setAddedAt(LocalDateTime.now());
            cartRepo.save(existing);

            return ResponseEntity.ok(new MessageResponse("Item quantity updated in cart successfully"));
        }

        CartItem cartItem=new CartItem(cartItemDto);
        cartItem.setAddedAt(LocalDateTime.now());
        cartRepo.save(cartItem);
        return ResponseEntity.ok(new MessageResponse("Item added to cart successfully"));
    }
    @Transactional
    @Override
    public ResponseEntity<MessageResponse> clearCartItems(Long userId){
        int deletedCount=cartRepo.deleteAllByUserId(userId);
        if(deletedCount==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("No Items are there in cart for userId "+userId));
        }
        return ResponseEntity.ok(new MessageResponse("Cart Items are deleted successfully"));

    }

    @Override
    public ResponseEntity<MessageResponse> updateCartitem(CartItemDto cartItemDto,String token) {
        Optional<CartItem> existingItem = cartRepo.findByUserIdAndItemId(cartItemDto.getUserId(), cartItemDto.getItemId());
        if(existingItem.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Item with "+cartItemDto.getItemId()+"for user with Id "+cartItemDto.getUserId()+"not found."));
        }
        MenuResponseDto menuDeatils=client.getParticularItemDetails(token,cartItemDto.getItemId()).getBody();
        if(!menuDeatils.isAvailaible()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Item is unavailable today."));
        }
        if(cartItemDto.getQuantity()<1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Quantity should be atleast one"));
        }
        if(cartItemDto.getQuantity()>menuDeatils.getEstimatedItemsDelivered()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Items are out of stock today.Available Items are : "+menuDeatils.getEstimatedItemsDelivered()));
        }
        CartItem itemFound=existingItem.get();
        itemFound.setQuantity(cartItemDto.getQuantity());
        cartRepo.save(itemFound);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Item Updated Successfully"));
    }
    @Transactional
    @Override
    public ResponseEntity<MessageResponse> deleteItemByUserIdAndItemId(Long userId, Long itemId) {
        Optional<CartItem> itemExsists=cartRepo.findByUserIdAndItemId(userId,itemId);
        if(itemExsists.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Item with "+itemId+"for user with Id "+userId+"not found."));
        }
        cartRepo.deleteByUserIdAndItemId(userId,itemId);
        return ResponseEntity.ok(new MessageResponse("Item deleted from cart successfully"));
    }

    @Override
    public ResponseEntity<List<MenuResponseDto>> getAllCartItemsForUser(Long userId,String token) {
        List<CartItem> cartItems=cartRepo.findByUserId(userId);
//        if(cartItems.isEmpty()){
//            throw  new UserCartNotFoundException("No cart Items found for the user with userId:"+userId);
//        }
        List<MenuResponseDto> dto=new ArrayList<>();
        for(CartItem item:cartItems){
            MenuResponseDto menuDeatils=client.getParticularItemDetails(token,item.getItemId()).getBody();
            menuDeatils.setQuantity(item.getQuantity());
            RestaurantResponseDto restaurantDto=client.getRestaurantById(token,menuDeatils.getRestaurantId()).getBody();
            menuDeatils.setRestaurantName(restaurantDto.getName());
            dto.add(menuDeatils);
        }
        return ResponseEntity.ok(dto);
    }
}
