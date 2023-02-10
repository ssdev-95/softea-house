import java.io.IOException;

import com.checkout.Checkout;

class Main {
	public static void main(String...args) {
		Checkout checkout = new Checkout();

		try {
			System.out.println("Welcome to my SofTea House\nWhere we do what js libs don't: cherish the Dev's Experience");
			checkout.init();
		} catch(IOException err) {
			System.out.println(err);
		}
	}
}
