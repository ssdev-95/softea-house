/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import com.softea.factory.OrderFactory;
import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.enums.OrderStatus;
import com.softea.modules.handler.*;
import com.softea.modules.repository.IOrderRepository;
import com.softea.modules.service.OrderService;
import com.softea.modules.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTests {
	private OrderService orderService;
	private IOrderRepository or;
	
	@BeforeAll
	void setup() {
		or = mock(OrderRepository.class);
		orderService = new OrderService(or);
	}

	@AfterAll
	void drop() {
		reset(or);
	}

	@Test
	void should_not_add_an_order() {
		when(or.save(null)).thenThrow(
			NullPointerException.class);

		assertThrows(NullPointerException.class,
			()->orderService.placeOrder(null));
	}

	@Test
	void should_add_an_order() {
		final OrderDTO dto = new OrderDTO().setTable(0)
			.setCustomer("1234567890")
			.setOrderItems(Collections.emptyList());
		
		when(or.save(dto)).thenReturn(
			OrderFactory.create());
		
		assertDoesNotThrow(()->orderService.placeOrder(
		  dto));
	}

	@Test
	void should_not_find_an_order() {
		final String id = "jqjejdjw";
		
		when(or.findById(id)).thenReturn(
			Optional.empty());

		var exception = assertThrows(
			OrderNotFoundException.class,
			()->orderService.retrieveOrder(id));

		assertEquals(
			"[EXCEPTION] Order not found",
			exception.getMessage());
	}

	@Test
	void should_find_an_order() {
		final String id = "18w82j";

		when(or.findById(id)).thenReturn(Optional.of(
			OrderFactory.create()));

		assertDoesNotThrow(
			()->orderService.retrieveOrder(id));
	}

	@Test
	void should_not_close_an_order_that_is_not_open() {
		final String id = "17fhsks";

		when(or.findById(id)).thenReturn(Optional.of(
			OrderFactory.create().setOrderStatus(
			  OrderStatus.PAID_ORDER)));

		var exception = assertThrows(
			OrderPaymentException.class,
			()->orderService.closeOrder(id));

		assertEquals(
			"[EXCEPTION] Cannot close not open order",
			exception.getMessage());
	}

	@Test
	void should_close_an_order_without_fail() {
		final String id = "118ejfkss";
		
		when(or.findById(id)).thenReturn(Optional.of(
			OrderFactory.create()));
		
		assertDoesNotThrow(
			()->orderService.closeOrder(id));   
	}

	@Test
	void should_not_update_an_order_from_past_days() {
		final String id = "bduq81991";

		when(or.findById(id)).thenReturn(Optional.of(
			OrderFactory.create().setCreatedAt(
				LocalDateTime.parse(
					"2023-06-20T14:23:00.000"))));

		var exception = assertThrows(
			OrderProcessingException.class,
			()->orderService.closeOrder(id));

		assertEquals("[EXCEPTION] Can only update orders from current date",
			exception.getMessage());
	}

	@Test
	void should_not_query_for_future_orders() {
		final String isoDate = "2024-12-31T12:00:00.000";

		var exception = assertThrows(
			OrderNotFoundException.class,
			()->orderService.retrieveOrdersByDate(isoDate));

		verify(or,times(0)).findByCreatedAt(
			LocalDateTime.parse(isoDate));

		assertEquals("[EXCEPTION] Future orders query are not allowed",
			exception.getMessage());
	}

	@Test
	void should_get_orders_by_date() {
		final LocalDateTime date = LocalDateTime.now()
			.minusDays(1l);

		when(or.findByCreatedAt(date)).thenReturn(
			Collections.emptyList());

		assertDoesNotThrow(()->orderService
			.retrieveOrdersByDate(date.toString()));

		verify(or,times(1)).findByCreatedAt(date);
	}
}
