package com.checkout;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import com.checkout.enums.OrderStatus;
import com.checkout.operations.*;
import com.product.*;

public class Checkout {
	//private double cash = 0.00;
	private final String FILE_NAME;

	private Menu menu;
	private Path filePath;
	public List<String> externalFile;

	int operation = 0;

	public Checkout(String rootDir) {
		externalFile = new ArrayList<String>();
		FILE_NAME = String.format(
			"%1$s/orders.csv",
			rootDir
		);

		try {
			filePath = Paths.get(FILE_NAME);
			menu = new Menu(rootDir);
		} catch(Exception error) {
			System.out.println("Failed to init checkout session.");
			System.out.println(error);
		}
	}

	public void init() throws IOException {
		getCheckoutInfo();
		Scanner sc = new Scanner(System.in);

		System.out.println("Available operations:\n0. Buy something;\n1. Close order;\n2. Get daily revenues;\n");
		System.out.printf("What's your need? ");
		operation = sc.nextInt();

		switch(operation) {
			case 0:
				CreateOrderCheckoutOperation
					.performOperation(externalFile, FILE_NAME, menu);
				break;
			case 1:
				PayOrderCheckoutOperation
					.performOperation(externalFile, FILE_NAME, null);
				break;
			case 2:
				CloseCheckoutOperation
					.performOperation(externalFile, null, null);
				break;
			default:
				System.out.println("Operation not allowed, contact sysadmin.");
		}

		sc.close();
	}

	/*private void createOrder() throws IOException {
		menu.listTeas();

		Scanner sc = new Scanner(System.in);
		char hasNext = 'y';

		Order order = new Order();

		while(hasNext != 'n') {
			System.out.printf("What should you drink next? ");  
			int teaSelection = sc.nextInt();

			System.out.printf("How many do you need? ");
			int quantity = sc.nextInt();

			if(quantity < 1) {

				System.out.println("None tea was bought, exiting.");
				break;
			}

			Tea tea = menu.teas.get(teaSelection);
			OrderItem orderItem = order.addItem(tea, quantity);
			externalFile.add(orderItem.toString());

			System.out.printf("Need more tea? (y/n) ");       
			hasNext = sc.next().charAt(0);
	}

		order.save(FILE_NAME, externalFile);
		order.getSummary(menu);

		sc.close();
	}

	public void payOrder() throws IOException {
		String ioException = "Order id not supplied, try again.";
		Scanner sc = new Scanner(System.in);
		String orderId = sc.nextLine();
		int paymentMethod = 0;

		if(orderId.isBlank() || orderId.isEmpty()) {
			throw new IOException(ioException);
		}

		List<OrderItem> items = findOrderItemsByOrderId(orderId);
		OrderItem firstItem = items.get(0);

		Order order = new Order(orderId, "", items, OrderStatus.PENDING_PAYMENT_ORDER);

		System.out.println("Order billing ammount: " + order.totalPrice);
		System.out.printf("Pqyment methods:\n0. Cash[Experimental]\n1. Credit[Unnavailable]\n2. Pix[Unnavailable]\n\nWhich method you may use? ");

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

			externalFile.add(orderLine);
		}

		System.out.println("\nAmount in cash: $" + cash);
		order.save(FILE_NAME, externalFile);

		sc.close();
	}

	private List<OrderItem> findOrderItemsByOrderId(
		String id) {
		List<OrderItem> mappedList = new ArrayList();
		List<String> filteredList = externalFile
			.stream()
			.filter(str -> str.contains(id))
			.toList();

		List<String> restOfTheOrders = externalFile
			.stream()
			.filter(str -> !str.contains(id))
			.toList();

		for(String item : filteredList) {
			mappedList.add(new OrderItem(item));
		}

		for(String lastingOrder : restOfTheOrders) {
			externalFile.add(lastingOrder);
		}

		return mappedList;
	}*/

	public void getCheckoutInfo() throws IOException {
	  try {
  		List<String> externalFileReaden = Files
				.readAllLines(filePath);

			for(String line : externalFileReaden) {
				int index = externalFileReaden.indexOf(line);

				if(index > 0) {
					externalFile.add(externalFileReaden.get(index));
				}
			}
		} catch(IOException exception) {
			if(exception != null) {
			  Files.createFile(filePath);
			}
		}
	}
}
