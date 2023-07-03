/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.handler;

public class AuthFailureException
	  extends RuntimeException {
	public AuthFailureException() {
		super("[EXCEPTION] Unknown auth failure");
	}

	public AuthFailureException(String message) {
		super(message);
	}

	public AuthFailureException(
			Throwable throwable) {
		super(throwable);
	}

	public AuthFailureException(
			String message,
			Throwable throwable) {
		super(message, throwable);
	}
}
