import java.lang.Exception;
import java.nio.file.Paths;

import com.checkout.Checkout;

public class Main {
	private static final String INIT_LOG = ""+
			"Welcome to my SofTea House\n"+
			"Here we do what js libs don't: "+
			"cherish the Dev's Experience";

	public static void main(String...args) {
		try {
			String rootPath = Paths
				.get(".")
				.normalize()
				.toAbsolutePath()
				.getParent()
				.toString();

			String rootDir = String.format("%1$s/assets", rootPath);
			Checkout checkout = Checkout.getInstance(rootDir);
			
			System.out.println(INIT_LOG);
			checkout.init();
		} catch(Exception exception) {
			System.out.println(exception);
		}
	}
}
