/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import com.softea.factory.OrderFactory;
import com.softea.modules.dto.OrderDTO;
import com.softea.modules.handler.OrderNotFoundException;
import com.softea.modules.repository.IOrderRepository;
import com.softea.modules.service.OrderService;
import com.softea.modules.repository.OrderRepository;
import java.util.Collections;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTests {
	private OrderService orderService;
	private IOrderRepository or;
	
	@BeforeAll
	void setup() {
		or = Mockito.mock(OrderRepository.class);
		orderService = new OrderService(or);
	}

	@AfterAll
	void driop() {
		Mockito.reset(or);
	}

	@Test
	void should_not_add_an_order() {
		Mockito.when(or.save(null))
			.thenThrow(NullPointerException.class);

		Assertions.assertThrows(
			NullPointerException.class,
			()->orderService.placeOrder(null));
	}

	@Test
	void should_add_an_order() {
		OrderDTO dto = new OrderDTO().setTable(0)
			.setCustomer(123l)
			.setOrderItems(Collections.emptyList());
		Mockito.when(or.save(dto))
			.thenReturn(OrderFactory.create());
		Assertions.assertDoesNotThrow(
			()->orderService.placeOrder(dto));
	}

	@Test
	void should_not_find_an_order() {
		String id = "jqjejdjw";
		
		Mockito.when(or.findById(id)).thenReturn(		
			Optional.empty());
		
		Assertions.assertThrows(
			OrderNotFoundException.class,
			()->orderService.retrieveOrder(id));
	}

	@Test
	void should_find_an_order() {
		String id = "18w82j";

		Mockito.when(or.findById(id)).thenReturn(
			Optional.of(OrderFactory.create()));

		Assertions.assertDoesNotThrow(
			()->orderService.retrieveOrder(id));
	}
}
