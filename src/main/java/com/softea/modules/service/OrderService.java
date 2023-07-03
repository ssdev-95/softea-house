/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.service;

import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.entity.enums.OrderStatus;
import com.softea.modules.handler.*;
import com.softea.modules.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final String defaultException = 
		"[EXCEPTION] Order not found";
	private final IOrderRepository orderRepository;

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public Order retrieveOrder(String id) {
		var order = orderRepository
			.findById(id)
			.orElseThrow(()->new OrderNotFoundException(
				"[EXCEPTION] Order not found"));
		return order;
	}

	public List<Order> retrieveOrdersByDate(
			String date) {
		LocalDateTime dateTime = LocalDateTime.parse(
			date);
		
		LocalDateTime endOfDay = LocalDateTime.now()
			.toLocalDate()
			.atTime(LocalTime.MAX);
		
		if(dateTime.isAfter(endOfDay))
			throw new OrderNotFoundException(
				"[EXCEPTION] Future orders query are not allowed");

		List<Order> order = orderRepository
			.findByCreatedAt(dateTime);

		return order;
	}
	
	public Order placeOrder(OrderDTO dto) {
		return orderRepository.save(dto);
	}

	public Order closeOrder(String id) {
		return updateOrder(
			id,
			"PAID_ORDER",
			(Order o)->!o.isOpen(),
			()-> new OrderPaymentException(
			  "[EXCEPTION] Cannot close not open order"));
	}

	public Order cancelOrder(String id) {
		return updateOrder(
			id,
			"CANCELED_ORDER",
			(Order o)->!o.isOpen(),
			()->new OrderCancelmentException(
				"[EXCEPTION] Cannot cancel not open order"));
	}

	public Order reverseOrder(String id) {
		return updateOrder(
			id,
			"REVERSED_ORDER",
			(Order o)->!o.isPaid(),
			() -> new OrderReversalException(
				"[EXCEPTION] Cannot reverse not paid order"));
	}

	private Order updateOrder(
		  String id,
			String status,
			Function<Order, Boolean> evaluate,
			Supplier<RuntimeException> callback) {
		Order order = orderRepository.findById(id)
			.orElseThrow(()->new OrderNotFoundException(
				defaultException));

		if(validateCreationDate(order.getCreatedAt()))
			throw new OrderProcessingException(
				"[EXCEPTION] Can only update orders from current date");

		if(evaluate.apply(order)) throw callback.get();

		order.setOrderStatus(OrderStatus.valueOf(status))
			.setUpdatedAt(LocalDateTime.now());
		
		return orderRepository.patch(order);
	}

	private boolean validateCreationDate(
			LocalDateTime createdAt) {
		LocalDateTime startOfDay = LocalDateTime.now()
			.toLocalDate()
			.atStartOfDay();
		return createdAt.isBefore(startOfDay);
	}
}
