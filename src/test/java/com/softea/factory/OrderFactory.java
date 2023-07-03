/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.factory;

import java.util.Collections;
import java.util.List;
import com.softea.modules.entity.*;

public class OrderFactory {
	public static Order create(
			String id,
			User customer,
			List<OrderItem> items) {
		return new Order()
			.setCustomer(customer)
			.setOrderItems(items);
	}

	public static Order create() {
		return create(
			"1822",
			UserFactory.create(),
			Collections.emptyList());
	}
}
