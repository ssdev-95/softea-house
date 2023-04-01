package com.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.utils.FileSystem;

public class Menu {
	private String FILE_NAME;
	public List<Product> products;

	private static Menu instance;

	private Menu() throws Exception {
		FILE_NAME = FileSystem.getRootPath("tea.menu.csv");

		products = new ArrayList<Product>();

		for (String rowTea : FileSystem.readFile(FILE_NAME)) {
			String[] split = rowTea.split(",");

			Product product = new Product.ProductBuilder()
				.category("tea")
				.cod(split[0])
				.price(Double.parseDouble(split[2]))
				.pic(split[3])
				.sku(split[1])
				.build();

			products.add(product);
		}
	}

	public static Menu getInstance() throws Exception {
		if(instance == null) {
			instance = new Menu();
		}
		return instance;
	}

	public void listProducts() {
		System.out.println("Menu - Available Teas");

		for(Product sku : products) {
			int index = products.indexOf(sku);
			String skuEntry = sku.toString(index);
			System.out.println(skuEntry);
		}
	}

	public Product findOne(String prodId) {
		Product prod = products
			.stream()
			.filter(t -> Objects.equals(t.getCod(), prodId))
			.findFirst()
			.get();

		return prod;
	}
}

