/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.softea.modules.handler.OrderNotFoundException;
import com.softea.modules.repository.IOrderRepository;
import com.softea.modules.service.OrderService;
import com.softea.repository.OrderRepository;
import static com.softea.factory.OrderFactory.create;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTests {
	private OrderService orderService;
	
	@BeforeAll
	void setup() {
		IOrderRepository or = new OrderRepository();
		orderService = new OrderService(or);
	}

	@Test
	void should_not_add_an_order() {
		Assertions.assertThrows(
			Exception.class,
			()->orderService.placeOrder(null));
	}

	@Test
	void should_add_an_order() {
		Assertions.assertDoesNotThrow(
			()->orderService.placeOrder(create()));
	}

	@Test
	void should_not_find_an_order() {
		Assertions.assertThrows(
			OrderNotFoundException.class,
			()->orderService.retrieveOrder("jqjejdjw"));
	}
}
