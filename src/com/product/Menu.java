package com.product;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
	public List<Tea> teas;

	public Menu() throws Exception {
		 String rootDir = System.getProperty("user.dir");

		// TODO: TEA SHAPE IN MENU;
		// COD,SKU,UNITY_PRICE,PICTURE_URL;
		this.teas = new ArrayList<Tea>();

		File file = new File(rootDir + "/assets/tea.menu.csv");
		
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

