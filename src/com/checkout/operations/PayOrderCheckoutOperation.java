package com.checkout.operations;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import com.checkout.enums.*;
import com.checkout.*;
import com.product.*;

public class PayOrderCheckoutOperation {
	private static OrderStatus status;
	private static String createdOrderAt;
	private static List<String> lastingOrders;
	private static List<String> filteredList;
	private static List<String> finalCart;

	public static void performOperation(
		List<String> persistence,
		String fileName,
		Menu menu
	) throws IOException {
		finalCart = new ArrayList<String>();
		double cash = 0.0;
		String ioException = "Order id not supplied, try again.";
		Scanner sc = new Scanner(System.in);

		System.out.println("What's the order's id? ");
		String orderId = sc.next();
		int paymentMethod = 0;

		if(orderId.isBlank() || orderId.isEmpty()) {
			sc.close();
			throw new IOException(ioException);
		}

		List<OrderItem> mappedList = findOrderItemsByOrderId(orderId, persistence);

		Order order = new Order(orderId, createdOrderAt, mappedList, status);

		System.out.println("Order billing ammount: " + order.totalPrice);
		System.out.printf("Pqyment methods:\n0. Cash[Experimental]\n1. Credit[Unnavailable]\n2. Pix[Unnavailable]\n\nWhich method you may use? ");

		paymentMethod = sc.nextInt();

		while(paymentMethod != 0) {
			paymentMethod = sc.nextInt();
			if(paymentMethod != 0) {
				System.out.println("Payment method unnavailable at this time, please select another.");
			}
		}

		System.out.printf("\nPay the order now: ");
		double paymentAmmount = sc.nextDouble();

		if(paymentAmmount < order.totalPrice) {
			System.out.print("Quer me dar calote, é? Pague o resto ou lave a louça! Faltam: $" + (order.totalPrice - paymentAmmount));
		} else if(paymentAmmount > order.totalPrice) {
			System.out.println("Conta paga, volta sempre! Seu troco: $" + (paymentAmmount - order.totalPrice));
			paymentAmmount += sc.nextDouble();
		} else {
			System.out.println("Conta paga, volta sempre!\nAgradeçemos a preferencia");
		}

		order.setOrderStatus("PAID_ORDER");
		cash += paymentAmmount;

		for(OrderItem item : order.cart) {
			String orderLine = String.format("%1$s,%2$s,%3$s",
					item.toString(),
					order.createdAt,
					order.showStatus()
			);

			finalCart.add(orderLine);
		}

		for(String lastingOrder : lastingOrders) {
			finalCart.add(lastingOrder);
		}

		System.out.println("\nAmount in cash: $" + cash);
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
			list.add(new OrderItem(item));
		}

		System.out.println("filtered: " + filteredList.size());
		System.out.println("rest: " + lastingOrders.size());

		String[] firstItem = filteredList.get(0).split(",");
		createdOrderAt = firstItem[4];
		status = OrderStatus.valueOf(firstItem[5]);

		/*for(String lastingOrder : restOfTheOrders) {
			orders.add(lastingOrder);
		}*/

		return list;
	}
}
