





package com.exception;

public class CheckoutInitException extends RuntimeException {
	public CheckoutInitException(String className, Exception cause) {
		super("Cought at: " + className, cause);
	}
}
