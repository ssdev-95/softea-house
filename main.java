package teahouse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

class Tea {
	public String cod;
	public String sku;
	public String category;
	public double price;
	public String pic;

	Tea (double price, String cod, String sku, String pic) {
		this.cod = cod;
		this.sku = sku;
		this.price = price;
		this.pic = pic;
	}
}

class OrderItem {
	public String tea_id;
	public int quantity;
	public  double ammount_price;

	OrderItem(Tea tea, int quantity) {
		this.quantity = quantity;
		this.tea_id = tea.cod;
		this.ammount_price = quantity * tea.price;
	}
}

class Order {
  public	String id;
  public	List<OrderItem> cart;

	Order() {
		this.id = UUID.randomUUID().toString();
		this.cart = new ArrayList<OrderItem>();
	}

	public void addItem(Tea tea,int quantity) {
		OrderItem item = new OrderItem(tea, quantity);
		this.cart.add(item);
	}
}

class Menu {
	public List<Tea> teas;

	public Menu() throws Exception {
		// TODO: TEA SHAPE IN MENU;
		// COD,SKU,UNITY_PRICE,PICTURE_URL;

		File file = new File("./tea.menu.csv");
		
		Scanner sc = new Scanner(file);
		sc.useDelimiter("\n");

		while (sc.hasNext()) {
			String rowTea = sc.next();
			String[] split = rowTea.split(",");

			Tea tea = new Tea(
				Double.parseDouble(split[2]),
				split[0],
				split[1],
				split[3]
			);

			this.teas.add(tea);
		}

		sc.close();
	}
}

class Main {
	public static void main(String[] args) {
		try {
			Menu menu = new Menu();
			System.out.println(menu.teas.toString());
		} catch(Exception err) {
			System.out.println(err);
		}

		/*Scanner sc = new Scanner(System.in);
		char addNext = "y".charAt(0);

		Order order = new Order();

		while(addNext == "y".charAt(0)) {
			System.out.println("What you should drink next? ");

			Tea tea= new Tea("U1hrJ2ir7fhjs82eufj", 4.50);
			int quantity = sc.nextInt();

			order.addItem(tea, quantity);

			System.out.println("Need more tea?");
			addNext = sc.next().charAt(0);
		}

		List<OrderItem> summary = order.cart;
		System.out.println("Order summary:\n" + summary);
	
		sc.close();*/
	}
}
