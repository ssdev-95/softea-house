package com.checkout.operations;

import java.io.IOException;
import java.util.List;

import com.product.Menu;

public interface CheckoutOperation {
	static void performOperation(
		List<String> persistence,
		String fileName,
		Menu menu
	) throws IOException {}
}
