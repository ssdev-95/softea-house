/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.handler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class ExceptionsHandler {
	public static final String DEFAULT_EXCEPTION_MESSAGE = "[EXCEPTION] Operation failed while processing request";
	private Map<String, String> handle(
			RuntimeException rex) {
		Map<String,String> map = new HashMap<>();
		map.put("Err", rex.getMessage());
		return map;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public Map<String, String> handleUserNotFoundException(UserNotFoundException exception) {
		return handle(exception);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ProductNotFoundException.class)
	public Map<String, String> handleProductNotFoundException(ProductNotFoundException exception) {
		return handle(exception);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(OrderNotFoundException.class)
	public Map<String, String> handleOrderNotFoundException(OrderNotFoundException exception) {
		return handle(exception);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(OrderPaymentException.class)
	public Map<String, String> handleOrderPaymentException(OrderPaymentException exception) {
		return handle(exception);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(OrderCancelmentException.class)
	public Map<String, String> handleOrderCancelmentException(OrderCancelmentException exception) {
		return handle(exception);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(OrderReversalException.class)
	public Map<String, String> handleOrderReversalException(OrderReversalException exception) {
		return handle(exception);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(OrderProcessingException.class)
	public Map<String, String> handleOrderProcessingException(OrderProcessingException exception) {
		return handle(exception);
	}
}
