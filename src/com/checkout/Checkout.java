package com.checkout;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.checkout.operations.*;
import com.exception.CheckoutInitException;
import com.utils.FileSystem;

public class Checkout {
	private final String filter = "" +
	"order_id,tea_id,unity_price,quantity,created_at,order_status";
	private final String INIT_LOG = ""+
		"Available operations:\n"+
		"0. Buy something;\n"+
		"1. Close order;\n"+
		"2. Reverse an Order;\n"+
		"3. Get daily revenues;\n"+
		"4. Get a order info by it's id;\n\n" +
		"What's your need? ";
	
	private static Checkout instance;
	private final String FILE_NAME;

	public List<String> externalFile;

	private int operation = 0;

	private Checkout() {
		FILE_NAME = FileSystem.getRootPath("orders.csv");

		try {
			externalFile = FileSystem.readFile(FILE_NAME)
				.stream()
				.filter(t-> !t.contains(filter))
				.collect(Collectors.toList());

			Treasury
				.getInstance()
				.getCashInfoFromTreasury();
		} catch(Exception error) {
			System.out.println(error);
			throw new CheckoutInitException(
					"com.checkout.Checkout", error);
		}
	}

	public static Checkout getInstance() {
		if(instance == null) {
			instance = new Checkout();
		}

		return instance;
	}

	public void init() throws Exception {
		Scanner sc = new Scanner(System.in);

		System.out.printf(INIT_LOG);
		operation = sc.nextInt();

		switch(operation) {
			case 0:
				CreateOrderCheckoutOperation.placeOrder(
						externalFile, FILE_NAME);
				break;
			case 1:
				PayOrderCheckoutOperation.payOrder(
						externalFile, FILE_NAME);
				break;
			case 2:
				ReverseOrderCheckoutOperation.reverseOrder(
						externalFile, FILE_NAME);
				break;
			case 3:
				CloseCheckoutOperation.closeCheckout(
						externalFile, FILE_NAME);
				break;
			case 4:
				RetrieveOrderInfoCheckoutOperation.retrieveOrder(
						externalFile, FILE_NAME);
				break;
			default:
				System.out.println("Operation not allowed, contact sysadmin.");
		}

		sc.close();
	}
}
