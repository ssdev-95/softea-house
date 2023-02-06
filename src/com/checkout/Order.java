package com.checkout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.product.Menu;
import com.product.Tea;

public class Order {
  public String id;
  public List<OrderItem> cart;
	public LocalDateTime createdAt;

	public Order() {
		this.id = UUID.randomUUID().toString();
		this.createdAt = LocalDateTime.now();
		this.cart = new ArrayList<OrderItem>();
	}

	public void addItem(Tea tea,int quantity) {
		OrderItem item = new OrderItem(tea, quantity);
		this.cart.add(item);
	}

	public void getSummary(Menu menu) {
		double total = 0;
		String totalLog = "";
		String date = formatDate();
		
		System.out.println("===========================================================");
		System.out.println("  Casa das Teas                 " + date);
		System.out.println("-----------------------------------------------------------");
		System.out.println("  Order - " + this.id);
		System.out.println("===========================================================");

		for (int i = 0; i < cart.size(); i++) {
			OrderItem item = cart.get(i);
			total += item.ammount_price;

			String sku = menu
				.teas
				.stream()
				.filter(t -> Objects.equals(t.cod, item.tea_id))
				.findFirst()
				.get()
				.sku;

			String log = String
				.format("  %1$s. %2$s ->       %3$s x %4$s = %5$s", i, sku, item.quantity, item.unity_price, item.ammount_price);
			System.out.println(log);
		}

		totalLog = String.format("  Items: %2$s        Total price: R$ %1$s", total, cart.size());
	
		System.out.println("===========================================================");
		System.out.println(totalLog);
		System.out.println("-----------------------------------------------------------");
		System.out.println("");
		System.out.println("                    Comeback anytime !!                    ");
		System.out.println("");
		System.out.println("===========================================================");
	}

	private String formatDate() {
		String formatedDate = DateTimeFormatter
			.ofPattern("E dd MMM, yyyy - HH:mm")
			.format(createdAt);

		return formatedDate;
	}
}

