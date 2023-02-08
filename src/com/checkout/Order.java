package com.checkout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.product.Menu;
import com.product.Tea;

public class Order {
  public String id;
  public List<OrderItem> cart;
	public LocalDateTime createdAt;
	public double totalPrice = 0;

	public Order() {
		this.id = UUID.randomUUID().toString();
		this.createdAt = LocalDateTime.now();
		this.cart = new ArrayList<OrderItem>();
	}

	public OrderItem addItem(Tea tea,int quantity) {
		OrderItem item = new OrderItem(tea, quantity);
		totalPrice += item.ammount_price;

		this.cart.add(item);
		return item;
	}

	public void getSummary(Menu menu) {
		String totalLog = "";
		String date = formatOrderCreationDate();
		// TODO: clean log with 59x '='
		System.out.println("===========================================================");
		System.out.println("  Casa das Teas                 " + date);
		System.out.println("-----------------------------------------------------------");
		System.out.println("  Order - " + this.id);
		System.out.println("===========================================================");

		for (OrderItem item : cart) {
			int index = cart.indexOf(item);
			String sku = menu.findOne(item.tea_id).sku;
			String log = String
				.format("  %1$s. %2$s ->       %3$s x %4$s = %5$s", index, sku, item.quantity, item.unity_price, item.ammount_price);

			System.out.println(log);
		}

		totalLog = String.format("  Items: %2$s        Total price: R$ %1$s", totalPrice, cart.size());
	
		System.out.println("===========================================================");
		System.out.println(totalLog);
		System.out.println("-----------------------------------------------------------");
		System.out.println("");
		System.out.println("                    Comeback anytime !!                    ");
		System.out.println("");
		System.out.println("===========================================================");
	}

	private String formatOrderCreationDate() {
		String formatedDate = DateTimeFormatter
			.ofPattern("E dd MMM, yyyy - HH:mm")
			.format(createdAt);

		return formatedDate;
	}

	public void save(OrderItem item, Checkout checkout) {
		String itemLine = String.format("");
		checkout.externalFile.add(itemLine);
		System.out.println("Order saved!");
	}
}

