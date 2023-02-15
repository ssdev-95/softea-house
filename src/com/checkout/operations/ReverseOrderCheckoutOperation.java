package com.checkout.operations;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

import com.checkout.*;
import com.checkout.enums.OrderStatus;
import com.product.*;

public class ReverseOrderCheckoutOperation implements CheckoutOperation {
	private static List<String> lastingOrders;
	private static String createdAt;

	public static void performOperation(
		List<String> persistence,
		String fileName,
		Treasury treasury
	) {
		try {
  		lastingOrders = new ArrayList<String>();
  		Scanner sc = new Scanner(System.in);

  		System.out.printf("What order you would like to reverse? (id)  ");
  		String orderId = sc.next();

  		List<OrderItem> cart = findOrderItemsByOrderId(
  			orderId,
  			persistence
  		);

  		Order order = new Order(
  			orderId,
  			createdAt,
  			cart,
  			OrderStatus.REVERSED_ORDER
  		);

  		for(OrderItem orderItem : order.cart) {
  			lastingOrders.add(orderItem.toString());
  		}

  		treasury.withdrawCash(order.totalPrice);

  		order.save(fileName, lastingOrders);
  		sc.close();
		} catch (IOException exception) {
			System.out.println(exception);
		}
	}

	private static List<OrderItem> findOrderItemsByOrderId(
			String id, List<String> orders) {
		List<OrderItem> mappedOrders = new ArrayList<OrderItem>();

		List<String> filteredList = orders
			.stream()
			.filter(str -> str.contains(id))
			.toList();

		lastingOrders = orders
			.stream()
			.filter(str -> !str.contains(id))
			.toList();

		createdAt = filteredList.get(0).split(",")[4];

		for(String order : filteredList) {
			mappedOrders.add(new OrderItem(order));
		}

		return mappedOrders;
	}
}
