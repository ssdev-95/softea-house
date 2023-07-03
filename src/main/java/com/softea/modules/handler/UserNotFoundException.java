/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.handler;

public class UserNotFoundException
	  extends RuntimeException {
	public UserNotFoundException() {
		super(
			ExceptionsHandler.DEFAULT_EXCEPTION_MESSAGE);
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(
			Throwable throwable) {
		super(throwable);
	}

	public UserNotFoundException(
			String message,
			Throwable throwable) {
		super(message, throwable);
	}
}
