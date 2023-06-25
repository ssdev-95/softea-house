/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.handler;

public class ProductNotFoundException
	  extends RuntimeException {
	public ProductNotFoundException() {
		super("Order not found");
	}

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(
			Throwable throwable) {
		super(throwable);
	}

	public ProductNotFoundException(
			String message,
			Throwable throwable) {
		super(message, throwable);
	}
}
