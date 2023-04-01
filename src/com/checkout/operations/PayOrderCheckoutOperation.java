package com.checkout.operations;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import com.checkout.enums.*;
import com.exception.CheckoutOperationException;
import com.checkout.*;

public class PayOrderCheckoutOperation {
	private static OrderStatus status;
	private static String createdOrderAt;
	private static List<String> lastingOrders;
	private static List<String> filteredList;
	private static List<String> finalCart;

	public static void payOrder(
		List<String> persistence,
		String fileName
	) throws IOException {
		final Treasury treasury = Treasury.getInstance();
		finalCart = new ArrayList<String>();
		final String cException = "Order id not supplied, try again.";
		Scanner sc = new Scanner(System.in);

		System.out.println("What's the order's id? ");
		String orderId = sc.next();
		int paymentMethod = 0;

		if(orderId == null || orderId.isBlank() || orderId.isEmpty()) {
			sc.close();
			throw new CheckoutOperationException(cException);
		}

		List<OrderItem> mappedList = findOrderItemsByOrderId(
				orderId, persistence);

		Order order = new Order.OrderBuilder()
			.orderId(orderId)
			.createdAt(Long.parseLong(createdOrderAt))
			.cart(mappedList)
			.orderStatus(status)
			.build();

		System.out.println(
			"Order billing ammount: " + order.getTotalPrice());
		System.out.printf("Payment methods:\n0. Cash[Experimental]\n1. Credit[Unnavailable]\n2. Pix[Unnavailable]\n\nWhich method you may use? ");

		paymentMethod = sc.nextInt();

		while(paymentMethod != 0) {
			paymentMethod = sc.nextInt();
			if(paymentMethod != 0) {
				System.out.println("Payment method unnavailable at this time, please select another.");
			}
		}

		System.out.printf("\nPay the order now: ");
		double paymentAmmount = sc.nextDouble();

		if(paymentAmmount < order.getTotalPrice()) {
			System.out.print("Quer me dar calote, é? Pague o resto ou lave a louça! Faltam: $" + (order.getTotalPrice() - paymentAmmount));
		} else if(paymentAmmount > order.getTotalPrice()) {
			System.out.println("Conta paga, volta sempre! Seu troco: $" + (paymentAmmount - order.getTotalPrice()));
			paymentAmmount += sc.nextDouble();
		} else {
			System.out.println("Conta paga, volta sempre!\nAgradeçemos a preferencia");
		}

		order.setOrderStatus("PAID_ORDER");
		treasury.addCash(paymentAmmount);

		for(OrderItem item : order.getCart()) {
			String orderLine = String.format("%1$s,%2$s,%3$s",
					item.toString(),
					order.getCreatedAt(),
					order.showStatus()
			);

			finalCart.add(orderLine);
		}

		for(String lastingOrder : lastingOrders) {
			finalCart.add(lastingOrder);
		}

		order.save(fileName, finalCart);

		sc.close();
	}

	private static List<OrderItem> findOrderItemsByOrderId(String id, List<String> orders) {
		List<OrderItem> list = new ArrayList<OrderItem>();
		filteredList = orders
			.stream()
			.filter(str -> str.contains(id))
			.toList();

		lastingOrders = orders
			.stream()
			.filter(str -> !str.contains(id))
			.toList();

		for(String item : filteredList) {
			String[] itemSplt = item.split(",");

			OrderItem orderItem = new OrderItem.OrderItemBuilder()
				.prodUnityPrice(Double.parseDouble(itemSplt[2]))
				.prodQuantity(Integer.parseInt(itemSplt[3]))
				.prodOrderId(itemSplt[0])
				.prodId(itemSplt[1])
				.prodAmount()
				.build();

			list.add(orderItem);
		}

		String[] firstItem = filteredList.get(0).split(",");
		createdOrderAt = firstItem[4];
		status = OrderStatus.valueOf(firstItem[5]);

		return list;
	}
}
