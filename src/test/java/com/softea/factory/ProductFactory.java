/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.factory;

import com.softea.modules.entity.Product;
import java.util.List;

public class ProductFactory {
	public static Product create(
			String id,
			String sku,
			double price) {
		return new Product()
			.setId(id)
			.setSku(sku)
			.setPrice(price);
			//.setOrders(List.of());
	}

	public static Product create() {
		return create(
			"1irjq8d8j1nr8dajam",
			"Salame",
			18.37d);
	}
}
