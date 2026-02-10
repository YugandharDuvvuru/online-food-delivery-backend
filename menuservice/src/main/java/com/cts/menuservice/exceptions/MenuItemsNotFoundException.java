package com.cts.menuservice.exceptions;

public class MenuItemsNotFoundException extends RuntimeException{
    public MenuItemsNotFoundException(String message){
        super(message);
    }
}
