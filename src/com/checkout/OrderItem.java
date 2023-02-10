package com.checkout;

import com.product.Tea;

public class OrderItem {
	public String tea_id;
	public int quantity;
	public double ammount_price;
	public double unity_price;

	private String order_id;

	public OrderItem(	
		Tea tea,
		int quantity,
		String orderId
	) {
		this.order_id = orderId;
		this.quantity = quantity;
		this.tea_id = tea.cod;
		this.ammount_price = quantity * tea.price;
		this.unity_price = tea.price;
	}

	public OrderItem(	
		String teaCod,
		String orderId,
		double teaPrice,
		int quantity
	) {
		this.order_id = orderId;
		this.quantity = quantity;
		this.tea_id = teaCod;
		this.ammount_price = quantity * teaPrice;
		this.unity_price = teaPrice;
	}

	@Override
	public String toString() {
		return String.format("%1$s,%2$s,%3$s,%4$s",
			order_id,
			tea_id,
			unity_price,
			quantity
		);
	}

	public static OrderItem parse(String orderItem) {
		String[] item = orderItem.split(",");
		Constructor<OrderItem> constructor = OrderItem
			.class
			.getConstructor(com.checkout.OrderItem);

		return constructor(
			item[1],
			item[0],
			Double.parseDouble(item[2]),
			item[3]
		);
	}
}

