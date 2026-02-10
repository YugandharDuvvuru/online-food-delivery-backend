package com.cts.orderservice.exception;

import com.cts.orderservice.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(CartEmptyException.class)
	public ResponseEntity<MessageResponse> handleCartEmptyException(CartEmptyException ex){
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
	}
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<MessageResponse> OrderNotFoundException(OrderNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }
	@ExceptionHandler(ItemUnavailableException.class)
	public ResponseEntity<MessageResponse> handleItemUnavailableException(ItemUnavailableException ex){
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
	}
	@ExceptionHandler(PaymentFailedException.class)
	public ResponseEntity<MessageResponse> handlePaymentFailedExceptionException(PaymentFailedException ex){
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
	}
	

}
