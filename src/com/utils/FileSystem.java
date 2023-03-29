package com.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileSystem {
	public static List<String> readFile(String fileName)
			throws IOException {
		try {
			return Files.readAllLines(getFilePath(fileName));
		} catch(IOException exception) {
			System.out.println("[ERROR] " + exception.getMessage());
			Files.createFile(getFilePath(fileName));				
			return new ArrayList<String>();
		}
	}

	public static Path getFilePath(String fileName)
	    throws IOException {
		return Path.of(fileName);
	}

	public static void save(String fileName,List<String> items)
			throws IOException {
		FileWriter fileWriter = new FileWriter(fileName);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		for(String line : items) {
			if(items.indexOf(line) == 0) {
				printWriter.printf(line);
			} else {
				printWriter.printf('\n' + line);
			} 
		}

		printWriter.close();
		fileWriter.close();
	}
}
