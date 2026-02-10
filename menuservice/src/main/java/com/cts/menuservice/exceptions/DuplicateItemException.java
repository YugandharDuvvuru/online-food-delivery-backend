package com.cts.menuservice.exceptions;

public class DuplicateItemException extends RuntimeException{
    public DuplicateItemException(String message){
        super(message);
    }
}
