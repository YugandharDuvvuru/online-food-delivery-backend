package com.cts.cartservice.exceptions;

import com.cts.cartservice.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserCartNotFoundException.class)
    public ResponseEntity<MessageResponse> handleUserCartNotFoundException(UserCartNotFoundException ex){
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(ex.getMessage()));
    }
}
