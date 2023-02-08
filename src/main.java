import java.io.IOException;

import com.checkout.Checkout;

class Main {
	public static void main(String...args) {
		Checkout checkout = new Checkout();
		try {
			checkout.init();
		} catch(IOException err) {
			System.out.println(err);
		}
	}
}
