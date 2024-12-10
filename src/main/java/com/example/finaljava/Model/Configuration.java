package com.example.finaljava.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Configuration {
    private String eventName;
    private String theaterName;
    private int theaterID; // Auto-incrementing Theater ID
    private int totalTickets;
    private int ticketReleaseRate;
    private int vendorCount = 20; // Default value
    private int customerCount = 20; // Default value
    private int customerRetrievalRate;
    private int maximumTicketCapacity;
    private Double ticketPrice;

    private static final String CONFIG_FILE_PATH = "configurations.json";

    // Save the current configuration to the JSON file
    public void saveToJson() throws IOException {
        Gson gson = new Gson();
        List<Configuration> configs = loadAllConfigurations();

        // Assign auto-incremented Theater ID
        this.theaterID = configs.size() + 1;

        configs.add(this); // Add the current configuration

        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
            gson.toJson(configs, writer);
        } catch (IOException e) {
            throw new IOException("Error saving configuration to file: " + e.getMessage());
        }
    }

    // Load all configurations from the JSON file
    public static List<Configuration> loadAllConfigurations() {
        Gson gson = new Gson();
        List<Configuration> configs = new ArrayList<>();

        try (FileReader reader = new FileReader(CONFIG_FILE_PATH)) {
            Type listType = new TypeToken<List<Configuration>>() {}.getType();
            configs = gson.fromJson(reader, listType);

            if (configs == null || configs.isEmpty()) { // Handle the case where the file is empty
                configs = new ArrayList<>();
                System.out.println(formatText("Warn: No configurations found in the file. Starting fresh.", "warn"));
            } else {
                System.out.println(formatText("Info: Loaded existing configurations successfully.", "success"));
            }
        } catch (IOException e) {
            System.out.println(formatText("Warn: Could not load configurations file. Starting fresh.", "warn"));
        }

        return configs;
    }

    // Collect a new configuration from user input
    public static Configuration getDataFromPrompt(Scanner scanner) {
        Configuration config = new Configuration();

        // Enter Event Name
        System.out.println(formatText("===== Configuration Summary =====", "style"));
        while (true) {
            System.out.print(formatText(">> Enter Event Name: ", "bold"));
            config.eventName = scanner.nextLine();
            if (config.eventName.trim().isEmpty()) {
                System.out.println(formatText("Error: Event Name cannot be empty.", "error"));
            } else {
                break;
            }
        }

        // Enter Theater Name
        while (true) {
            System.out.print(formatText(">> Enter Theater Name: ", "bold"));
            config.theaterName = scanner.nextLine();
            if (config.theaterName.trim().isEmpty()) {
                System.out.println(formatText("Error: Theater Name cannot be empty.", "error"));
            } else {
                break;
            }
        }

        // Enter Event Ticket Price
        while (true) {
            try {
                System.out.print(formatText(">> Enter Event Ticket Price (greater than 0): ", "bold"));
                config.ticketPrice = scanner.nextDouble();
                if (config.ticketPrice <= 0) {
                    System.out.println(formatText("Error: Ticket price must be a positive value.", "error"));
                } else {
                    System.out.println(formatText("Success: Ticket price set successfully!", "success"));
                    break;
                }
            } catch (Exception e) {
                System.out.println(formatText("Error: Invalid input. Please enter a valid number.", "error"));
                scanner.nextLine(); // Clear the buffer
            }
        }

        // Enter Total Number of Tickets
        while (true) {
            try {
                System.out.print(formatText(">> Enter Total Number of Tickets: ", "bold"));
                config.totalTickets = scanner.nextInt();
                if (config.totalTickets <= 0) {
                    System.out.println(formatText("Error: Total number of tickets must be greater than zero.", "error"));
                } else {
                    System.out.println(formatText("Success: Total tickets set successfully!", "success"));
                    break;
                }
            } catch (Exception e) {
                System.out.println(formatText("Error: Invalid input for Total Number of Tickets. Please enter a valid number.", "error"));
                scanner.nextLine(); // Clear the buffer
            }
        }

        // Enter Ticket Release Rate
        while (true) {
            try {
                System.out.print(formatText(">> Enter Ticket Release Rate (Enter seconds 1-10): ", "bold"));
                config.ticketReleaseRate = scanner.nextInt();
                if (config.ticketReleaseRate <= 0 || config.ticketReleaseRate > 10) {
                    System.out.println(formatText("Error: Ticket release rate must be between 1 and 10 seconds.", "error"));
                } else {
                    System.out.println(formatText("Success: Ticket release rate set successfully!", "success"));
                    break;
                }
            } catch (Exception e) {
                System.out.println(formatText("Error: Invalid input for Ticket Release Rate. Please enter a valid number.", "error"));
                scanner.nextLine(); // Clear the buffer
            }
        }

        // Enter Customer Retrieval Rate
        while (true) {
            try {
                System.out.print(formatText(">> Enter Customer Retrieval Rate (1-10 seconds): ", "bold"));
                config.customerRetrievalRate = scanner.nextInt();
                if (config.customerRetrievalRate < 1 || config.customerRetrievalRate > 10) {
                    System.out.println(formatText("Error: Rate must be between 1 and 10 seconds.", "error"));
                } else {
                    System.out.println(formatText("Success: Customer Retrieval Rate set successfully!", "success"));
                    break;
                }
            } catch (Exception e) {
                System.out.println(formatText("Error: Invalid input for Customer Retrieval Rate. Please enter a valid number.", "error"));
                scanner.nextLine(); // Clear the buffer
            }
        }

        // Enter Maximum Ticket Capacity
        while (true) {
            try {
                System.out.print(formatText(">> Enter Maximum Ticket Capacity of the Pool (cannot be greater than Total Tickets): ", "bold"));
                config.maximumTicketCapacity = scanner.nextInt();
                if (config.maximumTicketCapacity <= 0) {
                    System.out.println(formatText("Error: Maximum Ticket Capacity must be greater than zero.", "error"));
                } else if (config.maximumTicketCapacity > config.totalTickets) {
                    System.out.println(formatText("Warn: Maximum Ticket Capacity cannot be greater than Total Tickets.", "warn"));
                } else {
                    System.out.println(formatText("Success: Maximum Ticket Capacity set successfully!", "success"));
                    break;
                }
            } catch (Exception e) {
                System.out.println(formatText("Error: Invalid input for Maximum Ticket Capacity. Please enter a valid number.", "error"));
                scanner.nextLine(); // Clear the buffer
            }
        }

        // Clear the newline character left by the last nextInt() call
        scanner.nextLine();

        return config;
    }

    static String readInput(Scanner getInput) {
        while (true) {
            try {
                // Prompt for input
                String input = getInput.nextLine().trim().toLowerCase();  // Convert input to lowercase for case-insensitive comparison

                // Check if the input is "yes" or "no"
                if ("yes".equalsIgnoreCase(input) || "no".equalsIgnoreCase(input)) {
                    return input;  // Return the valid input
                } else {
                    // Warn user if input is invalid
                    System.out.println(formatText("Invalid input. Please enter 'yes' or 'no'.", "warn"));
                }
            } catch (Exception e) {
                // Catch any unexpected exceptions and show an error message
                System.out.println(formatText("An unexpected error occurred while reading input: " + e.getMessage(), "warn"));
            }
        }
    }


    // Format text with styles (bold, italic, error, success, etc.)
    private static String formatText(String text, String style) {
        String reset = "\u001B[0m";
        String color = "";
        switch (style.toLowerCase()) {
            case "bold":
                color = "\u001B[1m";
                break;
            case "italic":
                color = "\u001B[3m";
                break;
            case "error":
                color = "\u001B[31m"; // Red for errors
                break;
            case "success":
                color = "\u001B[32m"; // Green for success
                break;
            case "style":
                color = "\u001B[46m\u001B[35m"; // Cyan background with Magenta text
                break;
            case "warn":
                color = "\u001B[33m"; // Yellow for warnings
                break;
            default:
                color = "";
        }
        return color + text + reset;
    }

    // Getters
    public String getEventName() {
        return eventName;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public int getTheaterID() {
        return theaterID;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTheaterID(int theaterID) {
        this.theaterID = theaterID;
    }
}
