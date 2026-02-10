package com.cts.cartservice.exceptions;

public class UserCartNotFoundException extends RuntimeException {
    public UserCartNotFoundException(String message){
        super(message);
    }
}
