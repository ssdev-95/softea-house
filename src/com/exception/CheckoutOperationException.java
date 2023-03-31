package com.exception;

public class CheckoutOperationException
	  extends RuntimeException {
		public CheckoutOperationException(String err) {
			super(err);
		}
}
