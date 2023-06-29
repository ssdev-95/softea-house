/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.factory;

import com.softea.modules.entity.*;
import java.util.List;

public class OrderFactory {
	public static Order create(
			String id,
			long customer,
			int table,
			List<Product> items) {
		return new Order()
			.setCustomer(customer)
			.setOrderItems(items)
			.setTableNumber(table);
	}

	public static Order create() {
		return create(
			"1822",
			183788181881384L,
			9,
			List.of(ProductFactory.create()));
	}
}
