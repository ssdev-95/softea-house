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
	private final String FILE_NAME;

	private Menu menu;
	private Treasury treasury;
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

			treasury = new Treasury(rootDir);
			treasury.getCashInfoFromTreasury();
		} catch(Exception error) {
			System.out.println("Failed to init checkout session.");
			System.out.println(error);
		}
	}

	public void init() throws IOException {
		getCheckoutInfo();
		Scanner sc = new Scanner(System.in);

		System.out.println("Available operations:\n0. Buy something;\n1. Close order;\n2. Reverse an Order;\n3. Get daily revenues;\n");
		System.out.printf("What's your need? ");
		operation = sc.nextInt();

		switch(operation) {
			case 0:
				CreateOrderCheckoutOperation
					.performOperation(externalFile, FILE_NAME, menu);
				break;
			case 1:
				PayOrderCheckoutOperation
					.performOperation(externalFile, FILE_NAME, treasury);
				break;
			case 2:
				ReverseOrderCheckoutOperation
					.performOperation(externalFile, FILE_NAME, treasury);
				break;
			case 3:
				CloseCheckoutOperation
					.performOperation(externalFile, FILE_NAME, menu);
				break;
			default:
				System.out.println("Operation not allowed, contact sysadmin.");
		}

		sc.close();
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
