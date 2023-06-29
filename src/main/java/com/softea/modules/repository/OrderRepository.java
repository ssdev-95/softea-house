/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.handler.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class OrderRepository
	  implements IOrderRepository {
	private final OrderJPARepository jpaRepository;
	private final ProductJPARepository pjpaRepository;

	@Override
	public Optional<Order> findById(String id) {
	  return jpaRepository.findById(id);
	}

	@Override
	public List<Order> findAllByTable(int table) {
	  return jpaRepository.findAllByTableNumber(table);
	}

	@Override
	public Order save(OrderDTO dto) {
	  var order = new Order()
			.setTableNumber(dto.getTable())
			.setCustomer(dto.getCustomer())
			.setOrderItems(
				dto.getOrderItems()
				.stream()
				.map(id->pjpaRepository.findById(id).
					orElseThrow(ProductNotFoundException::new))
				.collect(toList()));
		return jpaRepository
		  .save(order);
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
