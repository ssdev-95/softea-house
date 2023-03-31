package com.checkout.operations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.checkout.Order;
import com.checkout.OrderItem;
import com.checkout.enums.OrderStatus;
import com.exception.CheckoutOperationException;
import com.product.Menu;

public class RetrieveOrderInfoCheckoutOperation
	  implements CheckoutOperation {
	public static void performOperation(
			List<String> persistence,
			String fileName,
			Menu menu) throws IOException {
		Scanner sc = new Scanner(System.in);

		System.out.println("What's the order's id:\n");
		String orderId = sc.nextLine();

		if(orderId.isBlank() || orderId.isEmpty()) {
			sc.close();
			throw new CheckoutOperationException(
		  	"Order id not supplied, try again with a valid order id.");
		}

		Order order = findOrderItemsByOrderId(orderId, persistence);
		order.getSummary(menu);
		sc.close();
	}

	private static Order findOrderItemsByOrderId(
			String id, List<String> orders) {
		List<OrderItem> list = new ArrayList<OrderItem>();

		List<String> filteredList = orders
			.stream()
			.filter(str -> str.contains(id))
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
		String createdOrderAt = firstItem[4];
		OrderStatus status = OrderStatus.valueOf(firstItem[5]);
	
		Order order = new Order.OrderBuilder()
			.cart(list)
			.createdAt(Long.parseLong(createdOrderAt))
			.orderId(id)
			.orderStatus(status)
			.build();

		return order;
	}
}
