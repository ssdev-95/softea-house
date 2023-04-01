package com.checkout.operations;

import java.io.IOException;
import java.util.List;

public interface CheckoutOperation {
	static void performOperation(
		List<String> persistence,
		String fileName
	) throws IOException {}
}
