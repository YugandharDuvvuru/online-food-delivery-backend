package com.cts.ownerservice.exceptions;

public class NoRestaurantFoundException extends  RuntimeException{
    public NoRestaurantFoundException(String message){
        super(message);
    }
}
