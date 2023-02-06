package com.checkout;

import com.product.Tea;

public class OrderItem {
	public String tea_id;
	public int quantity;
	public double ammount_price;
	public double unity_price;

	public OrderItem(Tea tea, int quantity) {
		this.quantity = quantity;
		this.tea_id = tea.cod;
		this.ammount_price = quantity * tea.price;
		this.unity_price = tea.price;
	}
}

