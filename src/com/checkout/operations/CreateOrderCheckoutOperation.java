package com.checkout.operations;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

import com.checkout.enums.OrderStatus;
import com.checkout.operations.*;

import com.product.*;
import com.checkout.*;

public class CreateOrderCheckoutOperation implements CheckoutOperation {
	public static void performOperation(
		List<String> persistence,
		String fileName,
		Menu menu
	) throws IOException {
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
			persistence.add(orderItem.toString());

			System.out.printf("Need more tea? (y/n) ");       
			hasNext = sc.next().charAt(0);

		}

		order.save(fileName, persistence);
		order.getSummary(menu);

		sc.close();
	}
}
