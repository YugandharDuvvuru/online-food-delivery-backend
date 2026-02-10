package com.cts.orderservice.exception;

public class ItemUnavailableException extends RuntimeException {
	public ItemUnavailableException(String message) {
		super(message);
	}

}
