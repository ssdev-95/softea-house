/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.handler;

public class OrderNotFoundException
	  extends RuntimeException {
	public OrderNotFoundException() {
		super(
			ExceptionsHandler.DEFAULT_EXCEPTION_MESSAGE);
	}

	public OrderNotFoundException(String message) {
		super(message);
	}

	public OrderNotFoundException(
			Throwable throwable) {
		super(throwable);
	}

	public OrderNotFoundException(
			String message, Throwable throwable) {
		super(message, throwable);
	}
}
