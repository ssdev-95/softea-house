/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.factory;

import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Product;
import java.util.List;

public class OrderFactory {
	public static OrderDTO create(
			long customer,
			List<Product> items) {
		return new OrderDTO()
			.setCustomer(customer)
			.setOrderItems(items);
	}

	public static OrderDTO create() {
		return create(
			183788181881384L,
			List.of(ProductFactory.create()));
	}
}
