package com.checkout.operations;


import java.io.IOException;
import java.util.List;

import com.product.Menu;

public class CloseCheckoutOperation implements CheckoutOperation {	
	public static void performOperation(
		List<String> persistence,
		String fileName,   
		Menu menu
	) throws IOException {
	  System.out.println("Show daily revenue;");
	}
}
