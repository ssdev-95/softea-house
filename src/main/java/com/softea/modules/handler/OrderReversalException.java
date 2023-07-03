/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.handler;

public class OrderReversalException
	  extends RuntimeException {
	public OrderReversalException() {
		super(
			ExceptionsHandler.DEFAULT_EXCEPTION_MESSAGE);
	}

	public OrderReversalException(String message) {
		super(message);
	}

	public OrderReversalException(Throwable throwable) {
		super(throwable);
	}

	public OrderReversalException(
			String message,
			Throwable throwable) {
		super(message, throwable);
	}
}
