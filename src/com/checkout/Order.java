package com.checkout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.product.Menu;
import com.product.Tea;
import com.checkout.enums.*;

public class Order {
  public String id;
  public List<OrderItem> cart;
	public LocalDateTime createdAt;
	public double totalPrice = 0;

	private OrderStatus order_status;

	public Order() {
		this.id = UUID.randomUUID().toString();
		this.createdAt = LocalDateTime.now();
		this.cart = new ArrayList<OrderItem>();
		this.order_status = OrderStatus.OPEN_ORDER;
	}

	public Order(
		String orderId,
		String createdAt,
		List<OrderItem> cart,
		OrderStatus status
	) {
		this.id = orderId;
		this.createdAt = LocalDateTime.now();
		this.cart = cart;
		this.order_status = status;

		for(OrderItem item : cart) {
			this.totalPrice += item.ammount_price;
		}
	}

	public OrderItem addItem(Tea tea,int quantity) {
		OrderItem item = new OrderItem(tea, quantity, id);
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

	public void save(
	  String fileName,List<String> externalFile
	) throws IOException {
		FileWriter fileWriter = new FileWriter(fileName);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		printWriter.printf("order_id,tea_id,unity_price,quantity,created_at,order_status");

		for(String line : externalFile) {
			String newLine = String.format("%1$s,%2$s,%3$s",
				line,
				createdAt,
				order_status
			);

			System.out.println(newLine);
			printWriter.printf('\n' + newLine);
		}

		printWriter.close();
		fileWriter.close();

		System.out.println("Order saved!");
	}

	public void setOrderStatus(String status) {
		this.order_status = OrderStatus.valueOf(status);
	}

	public OrderStatus showStatus() {
		return this.order_status;
	}
}

