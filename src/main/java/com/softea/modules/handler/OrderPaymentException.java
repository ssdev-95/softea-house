/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.handler;

public class OrderPaymentException
	  extends RuntimeException {
	public OrderPaymentException() {
		super(
			ExceptionsHandler.DEFAULT_EXCEPTION_MESSAGE);
	}

	public OrderPaymentException(String message) {
		super(message);
	}

	public OrderPaymentException(
			Throwable throwable) {
		super(throwable);
	}

	public OrderPaymentException(
			String message,
			Throwable throwable) {
		super(message, throwable);
	}
}
