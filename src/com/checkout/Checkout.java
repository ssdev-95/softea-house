package com.checkout;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import com.product.*;

public class Checkout {
	private final String rootDir = System.getProperty("user.dir");
	private final String FILE_NAME = rootDir + "/assets/orders.csv";

	private Menu menu;
	private Path filePath;
	public List<String> externalFile;

	int operation = 0;

	public Checkout() {
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

		System.out.println("Welcome to the Casa das Teas");
		System.out.println("Available operations:\n0. Buy something;\n1. Close order;\n2. Get daily revenues;\n");
		System.out.printf("What's your need? ");
		operation = sc.nextInt();

		switch(operation) {
			case 0:
				System.out.println("");
				createOrder();
				break;
			case 1:
				System.out.println("Pay order or wash pratos.");
				break;
			case 2:
				System.out.println("Show daily revenue;");
				break;
			default:
				System.out.println("Operation not allowed, contact sysadmin.");
		}

		sc.close();
	}

	private void createOrder() {
		menu.listTeas();

		Scanner sc = new Scanner(System.in);
		char addNext = 'y';

		Order order = new Order();

		while(addNext != 'n') {
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
			order.save(orderItem, this);

			System.out.printf("Need more tea? (y/n) ");       
			addNext = sc.next().charAt(0);
		}

		order.getSummary(menu);

		sc.close();
	}

	public void getCheckoutInfo() throws IOException {
	  try {
  		externalFile = Files.readAllLines(filePath);
		} catch(IOException exception) {
			if(exception != null) {
			  Files.createFile(filePath);
			}
		}
	}
}