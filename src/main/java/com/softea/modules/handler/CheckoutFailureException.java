/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.handler;

public class CheckoutFailureException
	  extends RuntimeException {
	public CheckoutFailureException() {
		super("[EXCEPTION] Unknown checkout failure");
	}

	public CheckoutFailureException(String message) {
		super(message);
	}

	public CheckoutFailureException(
			Throwable throwable) {
		super(throwable);
	}

	public CheckoutFailureException(
			String message,
			Throwable throwable) {
		super(message, throwable);
	}
}
