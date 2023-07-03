/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.entity.OrderItem;
import com.softea.modules.handler.*;
import lombok.RequiredArgsConstructor;
import static java.util.stream.Collectors.toList;

@Service @RequiredArgsConstructor
public class OrderRepository
	  implements IOrderRepository {
	private final OrderJPARepository jpaRepository;
	private final IUserRepository userRepository;
	private final IOrderItemRepository orderItemRepo;

	@Override
	public Optional<Order> findById(String id) {
	  return jpaRepository.findById(id);
	}

	@Override
	public List<Order> findByCreatedAt(
			LocalDateTime createdAt) {
		return jpaRepository.findByCreatedAt(createdAt);
	}

	@Override
	public Order save(OrderDTO dto) {
		var customer = userRepository
			.findByTaxId(dto.getCustomer())
			.orElseThrow(()->new AuthFailureException(
			  "[EXCEPTION] User not found"));

	  var order = new Order().setCustomer(customer);

		jpaRepository.save(order);

		List<OrderItem> orderItems = dto.getOrderItems()
			.stream()
			.map(item->orderItemRepo.save(order, item))
			.collect(toList());

		order.setOrderItems(orderItems);

		return order;
	}

	@Override
	public List<Order> findAll() {
	  return jpaRepository.findAll();
	}

	@Override
	public Order patch(Order order) {
	  return jpaRepository.save(order);
	}
}
