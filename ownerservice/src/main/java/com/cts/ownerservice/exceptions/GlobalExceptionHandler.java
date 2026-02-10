package com.cts.ownerservice.exceptions;

import com.cts.ownerservice.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<MessageResponse> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse(ex.getMessage()));
    }
    @ExceptionHandler(RestaurantsNotFoundException.class)
    public ResponseEntity<MessageResponse> handleRestaurantsNotFoundException(RestaurantsNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse(ex.getMessage()));
    }
    @ExceptionHandler(NoRestaurantFoundException.class)
    public ResponseEntity<MessageResponse> handleNoRestaurantFoundException(NoRestaurantFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse(ex.getMessage()));
    }
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<MessageResponse> handleDuplicateEmailException(DuplicateEmailException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new MessageResponse(ex.getMessage()));
    }
}
