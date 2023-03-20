package com.checkout.operations;

import java.io.IOException;
import java.util.List;

import java.util.Scanner;

import com.product.*;
import com.checkout.*;

public class CreateOrderCheckoutOperation
	  implements CheckoutOperation {
	public static void performOperation(
		List<String> persistence,
		String fileName,
		Menu menu
	) throws RuntimeException, IOException {
		menu.listProducts();

		Scanner sc = new Scanner(System.in);
		char hasNext = 'y';

		Order order = new Order.OrderBuilder().build();
		System.out.println(menu.products);

		while(hasNext != 'n') {
			if(hasNext != 'y') {
				sc.close();
				String LOG = "Option not allowed: '%1$s'";
				throw new RuntimeException(String.format(LOG, hasNext));
			}

			System.out.printf("What should you drink next? ");  
			int productSelection = sc.nextInt();

			System.out.printf("How many do you need? ");
			int quantity = sc.nextInt();

			if(quantity < 1) {
				System.out.println("None tea was bought, exiting.");
				break;
			}

			Product prod = menu.products.get(productSelection);

			OrderItem orderItem = order.addItem(prod, quantity);
			persistence.add(orderItem.toString());

			System.out.printf("Need more tea? (y/n) ");       
			hasNext = sc.next().charAt(0);
		}

		order.save(fileName, persistence);
		order.getSummary(menu);

		sc.close();
	}
}
