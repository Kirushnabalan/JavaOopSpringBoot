package com.example.finaljava.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Configuration {
    private String eventName;
    private String theaterName;
    private int theaterID; // Auto-incrementing Theater ID
    private int totalTickets;
    private int ticketReleaseRate;
    private int VendorCount;
    private int CustomerCount;
    private int customerRetrievalRate;
    private int maximumTicketCapacity;
    private Double ticketPrice;

    private static final String CONFIG_FILE_PATH = "configurations.json";

    // Save the current configuration to the JSON file
    public void SaveDataToJsonFile() throws IOException {
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
// Load all configurations from the JSON file
    public static List<Configuration> loadAllConfigurations() {
        try {
            File file = new File(CONFIG_FILE_PATH);

            // Create file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
                return new ArrayList<>(); // Return an empty list
            }

            // Read configurations from the JSON file
            try (FileReader reader = new FileReader(file)) {
                Configuration[] configs = new Gson().fromJson(reader, Configuration[].class);
                if (configs != null) {
                    return new ArrayList<>(List.of(configs));
                }
            }

        } catch (Exception e) {
            Logger.error("Error loading configurations: " + e.getMessage());
        }

        return new ArrayList<>(); // Return an empty list if any errors occur
    }

    // Collect a new configuration from user input
    public static Configuration getDataFromPrompt(Scanner scanner) {
        Configuration config = new Configuration();

        // Enter Event Name
        System.out.println(formatTextStyle("===== Configuration Summary =====", "style"));
        while (true) {
            System.out.print(formatTextStyle(">> Enter Event Name: ", "bold"));
            config.eventName = scanner.nextLine();
            if (config.eventName.trim().isEmpty()) {
                Logger.warn("Error: Event Name cannot be empty.");
            } else {
                break;
            }
        }

        // Enter Theater Name
        while (true) {
            System.out.print(formatTextStyle(">> Enter Theater Name: ", "bold"));
            config.theaterName = scanner.nextLine();
            if (config.theaterName.trim().isEmpty()) {
                Logger.warn("Error: Theater Name cannot be empty.");
            } else {
                break;
            }
        }

        // Enter Event Ticket Price
        while (true) {
            try {
                System.out.print(formatTextStyle(">> Enter Event Ticket Price (greater than 0): ", "bold"));
                config.ticketPrice = scanner.nextDouble();
                if (config.ticketPrice <= 0) {
                    Logger.warn("Error: Ticket price must be a positive value.");
                } else {
                    System.out.println(formatTextStyle("Success: Ticket price set successfully!","success"));
                    break;
                }
            } catch (Exception e) {
                Logger.error("Error: Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the buffer
            }
        }

        // Enter Total Number of Tickets
        while (true) {
            try {
                System.out.print(formatTextStyle(">> Enter Total Number of Tickets: ", "bold"));
                config.totalTickets = scanner.nextInt();
                if (config.totalTickets <= 0) {
                    Logger.warn("Total number of tickets must be greater than zero.");
                } else {
                    System.out.println(formatTextStyle("Success: Total tickets set successfully!", "success"));
                    break;
                }
            } catch (Exception e) {
                Logger.warn(" Invalid input for Total Number of Tickets. Please enter a valid number.");
                scanner.nextLine(); // Clear the buffer
            }
        }

        // Enter Ticket Release Rate
        while (true) {
            try {
                System.out.print(formatTextStyle(">> Enter Ticket Release Rate (Enter seconds 1-10): ", "bold"));
                config.ticketReleaseRate = scanner.nextInt();
                if (config.ticketReleaseRate <= 0 || config.ticketReleaseRate > 10) {
                    Logger.warn("Warn: Ticket release rate must be between 1 and 10 seconds.");
                } else {
                    System.out.println(formatTextStyle("Success: Ticket release rate set successfully!", "success"));
                    break;
                }
            } catch (Exception e) {
                Logger.error("Error: Invalid input for Ticket Release Rate. Please enter a valid number.");
                scanner.nextLine(); // Clear the buffer
            }
        }

        // Enter Customer Retrieval Rate
        while (true) {
            try {
                System.out.print(formatTextStyle(">> Enter Customer Retrieval Rate (1-10 seconds): ", "bold"));
                config.customerRetrievalRate = scanner.nextInt();
                if (config.customerRetrievalRate < 1 || config.customerRetrievalRate > 10) {
                    Logger.error("Error: Rate must be between 1 and 10 seconds.");
                } else {
                    System.out.println(formatTextStyle("Success: Customer Retrieval Rate set successfully!", "success"));
                    break;
                }
            } catch (Exception e) {
                Logger.error("Error: Invalid input for Customer Retrieval Rate. Please enter a valid number.");
                scanner.nextLine(); // Clear the buffer
            }
        }

        // Enter Maximum Ticket Capacity
        while (true) {
            try {
                System.out.print(formatTextStyle(">> Enter Maximum Ticket Capacity of the Pool (cannot be greater than Total Tickets): ", "bold"));
                config.maximumTicketCapacity = scanner.nextInt();
                if (config.maximumTicketCapacity <= 0) {
                    Logger.warn("warn: Maximum Ticket Capacity must be greater than zero.");
                } else if (config.maximumTicketCapacity > config.totalTickets) {
                    Logger.warn("Warn: Maximum Ticket Capacity cannot be greater than Total Tickets.");
                } else {
                    System.out.println(formatTextStyle("Success: Maximum Ticket Capacity set successfully!", "success"));
                    break;
                }
            } catch (Exception e) {
                Logger.error("Error: Invalid input for Maximum Ticket Capacity. Please enter a valid number.");
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
                    Logger.warn("Invalid input. Please enter 'yes' or 'no'.");
                }
            } catch (Exception e) {
                // Catch any unexpected exceptions and show an error message
                Logger.error("An unexpected error occurred while reading input: " + e.getMessage());
            }
        }
    }


    // Format text with styles (bold, italic, error, success, etc.)
    private static String formatTextStyle(String text, String style) {
        String reset = "\u001B[0m";
        String color = "";
        switch (style.toLowerCase()) {
            case "bold":
                color = "\u001B[1m";
                break;
            case "italic":
                color = "\u001B[3m";
                break;
            case "success":
                color = "\u001B[32m"; // Green for success
                break;
            case "style":
                color = "\u001B[46m\u001B[35m"; // Cyan background with Magenta text
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

    public int getVendorCount() {
        return VendorCount;
    }

    public int getCustomerCount() {
        return CustomerCount;
    }
}

