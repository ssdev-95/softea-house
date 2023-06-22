/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.repository.IOrderRepository;

public class OrderRepository
	  implements IOrderRepository {
	List<Order> orders;
	public OrderRepository() {
		orders = new ArrayList<>();
	}

	@Override
	public Optional<Order> findById(String id) {
		return orders
			.stream()
			.filter(o->o.getId().equals(id))
			.findFirst();
	}

	@Override
	public Order save(OrderDTO dto) {
		var order = new Order()
			.setId(String.valueOf(dto.hashCode()))
			.setCustomer(dto.getCustomer())
			.setOrderItems(dto.getOrderItems());
		orders.add(order);
	  return order;
	}
}
