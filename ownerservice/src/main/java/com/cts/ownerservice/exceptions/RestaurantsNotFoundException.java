package com.cts.ownerservice.exceptions;

public class RestaurantsNotFoundException extends RuntimeException{
    public RestaurantsNotFoundException(String message){
        super(message);
    }
}
