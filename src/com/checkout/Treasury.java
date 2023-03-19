package com.checkout;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class Treasury {
	private static Treasury instance;
	private final String FILE_NAME;
	private Double cash = 0.00;

	private Treasury() { FILE_NAME = ""; }

	private Treasury(String fileName) {
		FILE_NAME = String
			.format("%1$s/treasury.csv", fileName);
	}

	public Double getCash() {
		return this.cash;
	}

	public void addCash(double ammount) {
		this.cash += ammount;
		sendCashToTreasury();

		System.out.println("Updated cash: " + cash);
	}

	public void withdrawCash(double ammount) {
		this.cash -= ammount;
		sendCashToTreasury();

		System.out.println("Updated cash: " + cash);
	}

	public void sendCashToTreasury() {
		try {
			FileWriter fileWriter = new FileWriter(FILE_NAME);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			String cashFormatted = String.format("%.2f", this.cash);

			printWriter.printf("ammount");
			printWriter.printf(cashFormatted);

			printWriter.close();
			fileWriter.close();
		} catch(IOException exception) {
			System.out.println(exception);
		}
	}

	public void getCashInfoFromTreasury() {
		try {
  		Boolean noCash = cash.isNaN() || cash.equals(null);
			Path filePath = Path.of(FILE_NAME);

			String cash = Files
				.readAllLines(filePath)
				.get(1);

			this.cash = noCash ? Double.parseDouble(cash) : 0.00;
		} catch(IOException exception) {
			System.out.println(exception);
		}
	}

	public static Treasury getInstance(String fileName) {
		if(instance.equals(null))
			instance = new Treasury(fileName);

		return instance;
	}
}
