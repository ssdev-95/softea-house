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

	public OrderItem(String orderItem) {
		String[] item = orderItem.split(",");
		this.tea_id = item[1];
		this.order_id = item[0];
		this.unity_price = Double.parseDouble(item[2]);
		this.quantity = Integer.parseInt(item[3]);
		this.ammount_price = quantity * unity_price;
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
}

