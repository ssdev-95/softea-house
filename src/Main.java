import java.lang.Exception;

import com.checkout.Checkout;

public class Main {
	private static final String INIT_LOG = ""+
			"Welcome to my SofTea House\n"+
			"Here we do what js libs don't: "+
			"cherish the Dev's Experience";

	public static void main(String...args) {
		try {
			Checkout checkout = Checkout.getInstance();
			System.out.println(INIT_LOG);
			checkout.init();
		} catch(Exception exception) {
			System.out.println(exception);
		}
	}
}
