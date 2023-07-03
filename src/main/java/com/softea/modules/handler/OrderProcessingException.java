/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.handler;

public class OrderProcessingException
	  extends RuntimeException {
	public OrderProcessingException() {
		super(
			ExceptionsHandler.DEFAULT_EXCEPTION_MESSAGE);
	}

	public OrderProcessingException(String message) {
		super(message);
	}

	public OrderProcessingException(
			Throwable throwable) {
		super(throwable);
	}

	public OrderProcessingException(
			String message,
			Throwable throwable) {
		super(message, throwable);
	}
}
