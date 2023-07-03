/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

//import java.util.List;
//import java.util.Optional;
import com.softea.modules.dto.OrderItemDTO;
import com.softea.modules.entity.Order;
import com.softea.modules.entity.OrderItem;

public interface IOrderItemRepository {
	OrderItem save(
		Order order, OrderItemDTO dto);
}
