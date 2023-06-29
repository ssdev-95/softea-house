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
	  Order order = orderRepository.findById(id)
		  .orElseThrow(()->new CheckoutFailureException(
				defaultException));

		if(validateCreationDate(order.getCreated_at()))
			throw new CheckoutFailureException(
				"[EXCEPTION] Can only update orders from current date");

		if(!order.isOpen())
			throw new CheckoutFailureException(
				"[EXCEPTION] Cannot close not open order");

		order.setOrderStatus(OrderStatus.PAID_ORDER)
			.setUpdated_at(LocalDateTime.now());
		return orderRepository.patch(order);
	}

	public Order cancelOrder(String id) {
		Order order = orderRepository
		  .findById(id)
			.orElseThrow(()->new CheckoutFailureException(
				defaultException));

		if(validateCreationDate(order.getCreated_at()))
			throw new CheckoutFailureException(
			  "[EXCEPTION] Can only update orders from current date");

		if(!order.isOpen())
			throw new CheckoutFailureException(
				"[EXCEPTION] Cannot cancel not open order");

		order.setOrderStatus(OrderStatus.CANCELED_ORDER)
			.setUpdated_at(LocalDateTime.now());
		return orderRepository.patch(order);
	}

	public Order reverseOrder(String id) {
		Order order = orderRepository
			.findById(id)
			.orElseThrow(()->new CheckoutFailureException(
				defaultException));

		if(validateCreationDate(order.getCreated_at()))
		  throw new CheckoutFailureException(
				"[EXCEPTION] Can only update orders from current date");

		if(!order.isPaid())
			throw new CheckoutFailureException(
				"[EXCEPTION] Cannot reverse not paid order");

		order.setOrderStatus(OrderStatus.REVERSED_ORDER)
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
