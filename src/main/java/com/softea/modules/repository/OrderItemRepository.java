/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import org.springframework.stereotype.Service;

import com.softea.modules.dto.OrderItemDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.entity.OrderItem;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class OrderItemRepository
	  implements IOrderItemRepository {
	private final OrderItemJPARepository jpaRepository;
	private final ProductRepository prodRepository;

	public OrderItem save(
			Order order, OrderItemDTO dto) {
		var product = prodRepository
			.findById(dto.getProductId())
			.orElseThrow(()->new RuntimeException(
				"[EXCEPTION] Product not found"));

		OrderItem item = new OrderItem()
			.setOrder(order)
			.setProduct(product)
			.setQuantity(dto.getQuantity());
		
		return jpaRepository.save(item);
	}
}
