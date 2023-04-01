package com.checkout;

import java.io.IOException;
import java.util.List;

import com.utils.FileSystem;

public class Treasury {
	private static Treasury instance;
	private final String FILE_NAME;
	private Double cash = 0d;

	private Treasury() {
		FILE_NAME = FileSystem.getRootPath("treasury.csv");
	}

	public Double getCash() {
		return this.cash;
	}

	public void addCash(double ammount) throws IOException {
		this.cash += ammount;
		sendCashToTreasury();

		System.out.println("Updated cash: " + cash);
	}

	public void withdrawCash(double ammount) throws IOException {
		this.cash -= ammount;
		sendCashToTreasury();

		System.out.println("Updated cash: " + cash);
	}

	public void sendCashToTreasury() throws IOException {
		String cashFormatted = String.format("%.2f", this.cash);
		FileSystem.save(
				FILE_NAME, List.of("ammount", cashFormatted));
	}

	public void getCashInfoFromTreasury() throws IOException {
		List<String> treasuryFile = FileSystem.readFile(FILE_NAME);

		if(treasuryFile.size() >= 2) {
			cash += Double.parseDouble(treasuryFile.get(1));
		}
	}

	public static Treasury getInstance() {
		if(instance == null)
			instance = new Treasury();

		return instance;
	}
}
