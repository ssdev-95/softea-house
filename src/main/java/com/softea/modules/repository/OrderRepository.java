/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderRepository
	  implements IOrderRepository {
	private final OrderJPARepository jpaRepository;

	@Override
	public Optional<Order> findById(String id) {
	  return jpaRepository.findById(id);
	}

	@Override
	public Order save(OrderDTO dto) {
	  var order = new Order()
			.setCustomer(dto.getCustomer())
			.setOrderItems(dto.getOrderItems());
		return jpaRepository.save(order);
	}
}
