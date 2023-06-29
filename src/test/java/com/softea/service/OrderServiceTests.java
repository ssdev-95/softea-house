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
import com.softea.modules.entity.enums.OrderStatus;
import com.softea.modules.handler.CheckoutFailureException;
import com.softea.modules.repository.IOrderRepository;
import com.softea.modules.service.OrderService;
import com.softea.modules.repository.OrderRepository;

import java.time.LocalDateTime;
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
	void drop() {
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
		
		var exception = Assertions.assertThrows(
			CheckoutFailureException.class,
			()->orderService.retrieveOrder(id));

		Assertions.assertEquals(
			"[EXCEPTION] Order not found",
			exception.getMessage());
	}

	@Test
	void should_find_an_order() {
		String id = "18w82j";

		Mockito.when(or.findById(id)).thenReturn(
			Optional.of(OrderFactory.create()));

		Assertions.assertDoesNotThrow(
			()->orderService.retrieveOrder(id));
	}

	@Test
	void should_not_close_an_order_that_is_not_open() {
		String id = "17fhsks";

		Mockito.when(or.findById(id)).thenReturn(
			Optional.of(OrderFactory.create()
				.setOrderStatus(OrderStatus.PAID_ORDER)));

		var exception = Assertions.assertThrows(
			CheckoutFailureException.class,
			()->orderService.closeOrder(id));

		Assertions.assertEquals(
			"[EXCEPTION] Cannot close not open order",
			exception.getMessage());
	}

	@Test
	void should_close_an_order_without_fail() {
		String id = "118ejfkss";
		
		Mockito.when(or.findById(id)).thenReturn(
			Optional.of(OrderFactory.create()));
		
		Assertions.assertDoesNotThrow(
			()->orderService.closeOrder(id));   
	}

	@Test
	void should_not_update_an_order_from_past_days() {
		String id = "bduq81991";

		Mockito.when(or.findById(id)).thenReturn(
			Optional.of(OrderFactory.create()
				.setCreated_at(LocalDateTime.parse(
					"2023-06-20T14:23:00.000"))));

		var exception = Assertions.assertThrows(
			CheckoutFailureException.class,
			()->orderService.closeOrder(id));

		Assertions.assertEquals(
			"[EXCEPTION] Can only update orders from current date",
			exception.getMessage());
	}
}
