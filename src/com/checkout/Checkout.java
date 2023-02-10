package com.checkout;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import com.checkout.enums.OrderStatus;
import com.product.*;

public class Checkout {
	private final String rootDir = System.getProperty("user.dir");
	private final String FILE_NAME = rootDir + "/assets/orders.csv";

	private Menu menu;
	private Path filePath;
	public List<String> externalFile;

	int operation = 0;

	public Checkout() {
		externalFile = new ArrayList<String>();
		try {
			filePath = Paths.get(FILE_NAME);
			menu = new Menu();
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
				System.out.println("");
				createOrder();
				break;
			case 1:
				payOrder();
				break;
			case 2:
				System.out.println("Show daily revenue;");
				break;
			default:
				System.out.println("Operation not allowed, contact sysadmin.");
		}

		sc.close();
	}

	private void createOrder() throws IOException {
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

		if(orderId.isBlank() || orderId.isEmpty()) {
			throw new IOException(ioException);
		}

		List<OrderItem> items = findOrderItemsByOrderId(orderId);
		OrderItem firstItem = items.get(0);
		Order order = new Order(orderId, "", items, OrderStatus.PENDING_PAYMENT_ORDER);
		System.out.println(order.id);
		sc.close();
	}

	private List<OrderItem> findOrderItemsByOrderId(
		String id) {
		List<OrderItem> mappedList = new ArrayList();
		List<String> filteredList = externalFile
			.stream()
			.filter(str -> str.contains(id))
			.toList();

		for(String item : filteredList) {
			mappedList.add(OrderItem.parse(item));
		}

		return mappedList;
	}

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
