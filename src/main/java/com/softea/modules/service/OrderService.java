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

		if(!order.isOpen())
			throw new CheckoutFailureException(
				"[EXCEPTION] Cannot close not open order");

		order.setOrderStatus(OrderStatus.PAID_ORDER);
		return orderRepository.patch(order);
	}

	public Order cancelOrder(String id) {
		Order order = orderRepository
		  .findById(id)
			.orElseThrow(()->new CheckoutFailureException(
				defaultException));

		if(!order.isOpen())
			throw new CheckoutFailureException(
				"[EXCEPTION] Cannot close not open order");

		order.setOrderStatus(OrderStatus.CANCELED_ORDER);
		return orderRepository.patch(order);
	}

	public Order reverseOrder(String id) {
		Order order = orderRepository
			.findById(id)
			.orElseThrow(()->new CheckoutFailureException(
				defaultException));

		if(!order.isPaid())
			throw new CheckoutFailureException(
				"[EXCEPTION] Cannot close not paid order");

		order.setOrderStatus(OrderStatus.REVERSED_ORDER);
		return orderRepository.patch(order);
	}
}
