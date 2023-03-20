package com.checkout.operations;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

import com.checkout.*;
import com.checkout.enums.OrderStatus;

public class ReverseOrderCheckoutOperation implements CheckoutOperation {
	private static  final String LOG = "What order you would like to reverse? (id)  ";
	private static List<String> lastingOrders;
	private static String createdAt;

	public static void performOperation(
			List<String> persistence,
			String fileName,
			Treasury treasury
	) throws IOException {
  	lastingOrders = new ArrayList<String>();
  	Scanner sc = new Scanner(System.in);

		System.out.printf(LOG);
  	String orderId = sc.next();

		List<OrderItem> cart = findOrderItemsByOrderId(
				orderId, persistence);

  	Order order = new Order.OrderBuilder()
			.orderId(orderId)
			.createdAt(Long.parseLong(createdAt))
			.cart(cart)
			.orderStatus(OrderStatus.REVERSED_ORDER)
			.build();

  	for(OrderItem orderItem : order.getCart()) {
  		lastingOrders.add(orderItem.toString());
		}

		treasury.withdrawCash(order.getTotalPrice());

		order.save(fileName, lastingOrders);
		sc.close();
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
			String[] itemSplt = order.split(",");

			OrderItem orderItem = new OrderItem.OrderItemBuilder()
				.prodUnityPrice(Double.parseDouble(itemSplt[2]))
				.prodQuantity(Integer.parseInt(itemSplt[3]))
				.prodOrderId(itemSplt[1])
				.prodId(itemSplt[0])
				.prodAmount()
				.build();

			mappedOrders.add(orderItem);
		}

		return mappedOrders;
	}
}
