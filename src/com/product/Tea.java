package com.product;

public class Tea extends Product {
	public Tea (double price, String cod, String sku, String pic) {
		this.cod = cod;
		this.sku = sku;
		this.price = price;
		this.pic = pic;
		this.category = "TEA";
	}

	public String toString(int index) {
		String strTea = String
			.format("%1$s. - %2$s [R$ %3$s]", index, sku, price);

		return strTea;
	}
}

