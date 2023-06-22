/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.service;

import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderService {
	private final IOrderRepository orderRepository;

	public Order retrieveOrder(String id) {
		Order order = orderRepository.findById(id)
			.orElseThrow(()->new RuntimeException(
				"Order not found for id: `"+id+"`"));
		return order;
	}
	
	public Order placeOrder(OrderDTO dto) {
		return orderRepository.save(dto);
	}
}
