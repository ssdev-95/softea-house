/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.service;

import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.entity.enums.OrderStatus;
import com.softea.modules.handler.CheckoutFailureException;
import com.softea.modules.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final String defaultException = "[EXCEPTION] Order not found";
	private final IOrderRepository orderRepository;

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public List<Order> getAllTableOrders(int table) {
		return orderRepository.findAllByTable(table);
	}

	public Order retrieveOrder(String id) {
		var order = orderRepository
			.findById(id)
			.orElseThrow(()->new CheckoutFailureException(
				defaultException));
		return order;
	}
	
	public Order placeOrder(OrderDTO dto) {
		return orderRepository.save(dto);
	}

	public Order closeOrder(String id) {
		/**
		 * TODO: Convert and pass as callback to update method
		 * if(!order.isOpen())
		 * throw new CheckoutFailureException(
		 * "[EXCEPTION] Cannot close not open order");
		**/

		return updateOrder(
			id,
			"PAID_ORDER",
			"[EXCEPTION] Cannot close not open order",
			(Order o)->!o.isOpen());
	}

	public Order cancelOrder(String id) {
		/**
		 * TODO: Convert and pass as callback to update method
		 * if(!order.someBoolean())
		 * throw new CheckoutFailureException(
		 * "[EXCEPTION] Cannot cancel not open order");
		**/

		return updateOrder(
			id,
			"CANCELED_ORDER",
			"[EXCEPTION] Cannot cancel not open order",
			(Order o)->!o.isOpen());
	}

	public Order reverseOrder(String id) {
		/**
		 * TODO: Convert and pass as callback to update method
		 * if(!order.isPaid())
		 * throw new CheckoutFailureException(
		 * "[EXCEPTION] Cannot reverse not paid order");
		**/

		return updateOrder(
			id,
			"REVERSED_ORDER",
			"[EXCEPTION] Cannot reverse not paid order",
			(Order o)->!o.isPaid());
	}

	private Order updateOrder(
		  String id,
			String status,
			String exitMsg,
			Function<Order, Boolean> callback) {
		Order order = orderRepository.findById(id)
			.orElseThrow(()->new CheckoutFailureException(
				defaultException));

		if(validateCreationDate(order.getCreated_at()))
			throw new CheckoutFailureException(
				"[EXCEPTION] Can only update orders from current date");

		if(callback.apply(order))
			throw new CheckoutFailureException(exitMsg);

		order.setOrderStatus(OrderStatus.valueOf(status))
			.setUpdated_at(LocalDateTime.now());
		
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
