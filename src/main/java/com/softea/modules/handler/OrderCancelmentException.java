/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.handler;

public class OrderCancelmentException
	  extends RuntimeException {
	public OrderCancelmentException() {
		super(
			ExceptionsHandler.DEFAULT_EXCEPTION_MESSAGE);
	}

	public OrderCancelmentException(String message) {
		super(message);
	}

	public OrderCancelmentException(
			Throwable throwable) {
		super(throwable);
	}

	public OrderCancelmentException(
			String message,
			Throwable throwable) {
		super(message, throwable);
	}
}
