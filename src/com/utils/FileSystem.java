package com.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileSystem {
	public static List<String> readFile(String fileName)
			throws IOException {
		try {
			return Files.readAllLines(getFilePath(fileName));
		} catch(IOException exception) {
			if(exception != null) {
				Files.createFile(getFilePath(fileName));
				return FileSystem.readFile(fileName);
			}
		}
	}

	public static Path getFilePath(String fileName)
	    throws IOException {
		return Path.of(fileName);
	}
}
