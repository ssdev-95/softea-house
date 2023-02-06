import java.util.Scanner;

import com.product.*;
import com.checkout.*;

class Main {
	public static void main(String...args) {
		try {
		  Menu menu = new Menu();
			System.out.println("Menu - Available Teas");

			for(int i = 0; i<menu.teas.size(); i++) {
				Tea tea = menu.teas.get(i);
				String teaEntry = String
					.format("%1$s. - %2$s [R$ %3$s]", i, tea.sku, tea.price);
				System.out.println(teaEntry);
		
			}

			Scanner sc = new Scanner(System.in);
			char addNext = "y".charAt(0);

			Order order = new Order();

			while(addNext != "n".charAt(0)) {
				System.out.printf("What should you drink next? ");
				int teaSelection = sc.nextInt();

				System.out.printf("How many do you need? ");
				int quantity = sc.nextInt();

				if(quantity < 1) {
					System.out.println("None tea was bought, exiting.");
					break;
				}

				Tea tea = menu.teas.get(teaSelection);
				order.addItem(tea, quantity);

				System.out.printf("Need more tea? (y/n) ");
				addNext = sc.next().charAt(0);
			}

			order.getSummary(menu);
	
			sc.close();
		} catch(Exception err) {
			System.out.println(err);
		}
	}
}
