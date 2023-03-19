import java.lang.Exception;
import java.nio.file.Paths;

import com.checkout.Checkout;

class Main {
	public static void main(String...args) {
		final String initMsg = "Welcome to my SofTea House\nHere we do what js libs don't: cherish the Dev's Experience";
		private String rootDir;

		try {
				String rootPath = Paths
				.get(".")
				.normalize()
				.toAbsolutePath()
				.getParent()
				.toString();

		System.out.println();
	

			rootDir = String.format("%1$s/assets", rootPath);

			System.out.println(rootDir);

			Checkout checkout = new Checkout(rootDir);
			System.out.println(initMsg);
			checkout.init();
		} catch(Exception exception) {
			System.out.println(exception);
		}
	}
}
