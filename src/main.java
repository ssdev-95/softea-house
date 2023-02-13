//package teahouse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.io.File;
import javax.swing.filechooser.FileSystemView;

import com.checkout.Checkout;

class Main {
	public static void main(String...args) {
		final String initMsg = "Welcome to my SofTea House\nHere we do what js libs don't: cherish the Dev's Experience";
		String rootDir;

		try {
			Path rootPath = Paths
				.get(".")
				.normalize()
				.toAbsolutePath();

			rootDir = String.format(
				"%1$s/assets",
				rootPath.getParent().toString()
			);

			System.out.println(rootDir);

			Checkout checkout = new Checkout(rootDir);
			System.out.println(initMsg);
			checkout.init();
		} catch(Exception exception) {
			System.out.println(exception);
		}

		/*
		long time = LocalDateTime
			.of(2023,8,18,0,0,0)
			.atZone(ZoneId.of("America/Manaus"))
			.toInstant()
			.toEpochMilli();
		
		System.out.println(time);
		*/
	}
}
