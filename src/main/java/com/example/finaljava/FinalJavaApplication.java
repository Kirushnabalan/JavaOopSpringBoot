package com.example.finaljava;

import java.awt.Desktop;
import java.net.URI;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinalJavaApplication {

	public static void main(String[] args) {
		// Start Spring Boot backend in a non-blocking manner
		SpringApplication.run(FinalJavaApplication.class, args);

		// Prompt the user for interface choice
		Scanner scanner = new Scanner(System.in);

		System.out.println("1. CLI");
		System.out.println("2. GUI");
		System.out.println("Choose an interface:");

		int choice = -1;
		try {
			choice = scanner.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid input. Exiting.");
			return; // Exit if input is invalid
		}

		if (choice == 1) {
			runCLI(); // Run CLI logic
		} else if (choice == 2) {
			runGUI(); // Run GUI logic
		} else {
			System.out.println("Invalid choice. Exiting.");
		}

		scanner.close();
	}

	private static void runCLI() {
		// Run the CLI-specific logic here
		System.out.println("Starting CLI...");
		// TicketConfigManager.main();  // Uncomment this to run CLI backend logic if required
	}

	private static void runGUI() {
		// Run the GUI-specific logic here
		System.out.println("Starting GUI...");
		openBrowser();
	}

	private static void openBrowser() {
		try {
			// Open browser to React frontend served by Spring Boot
			Desktop.getDesktop().browse(new URI("http://localhost:8080/"));
		} catch (Exception e) {
			System.out.println("Error opening browser: " + e.getMessage());
		}
	}
}