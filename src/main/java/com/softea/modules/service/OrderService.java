/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.service;

import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.handler.OrderNotFoundException;
import com.softea.modules.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final IOrderRepository orderRepository;

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public List<Order> getAllTableOrders(int table) {
		return orderRepository.findAllByTable(table);
	}

	public Order retrieveOrder(String id) {
		var order = orderRepository.findById(id)
			.orElseThrow(OrderNotFoundException::new);
		return order;
	}
	
	public Order placeOrder(OrderDTO dto) {
		return orderRepository.save(dto);
	}
}
