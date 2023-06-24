/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.service;

import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
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

	public Optional<Order> retrieveOrder(String id) {
		return orderRepository.findById(id);
	}
	
	public Order placeOrder(OrderDTO dto) {
		return orderRepository.save(dto);
	}
}
