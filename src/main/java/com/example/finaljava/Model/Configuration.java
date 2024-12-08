package com.example.finaljava.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int vendorCount;
    private int customerCount;
    private int customerRetrievalRate;
    private int customerTicketQuantity;
    private int maximumTicketCapacity;

    // File path for JSON storage
    private static final String CONFIG_FILE_PATH = "configurations.json";

    public static Configuration loadFromJson(String filePath) throws Exception {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        }
    }

    public void saveToJson(String defaultFilePath) throws IOException {
        Gson gson = new Gson();

        // Load existing configurations
        List<Configuration> configs = loadAllConfigurations();

        // Add the current configuration
        configs.add(this);

        // Write back to the file
        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
            gson.toJson(configs, writer);
        }
    }

    public static List<Configuration> loadAllConfigurations() {
        Gson gson = new Gson();
        List<Configuration> configs = new ArrayList<>();

        try (FileReader reader = new FileReader(CONFIG_FILE_PATH)) {
            // Define the type of the list to be deserialized
            Type listType = new TypeToken<List<Configuration>>() {}.getType();
            configs = gson.fromJson(reader, listType);

            if (configs == null) {
                configs = new ArrayList<>(); // Ensure a non-null list is returned
            }
        } catch (IOException e) {
            System.out.println("No existing configurations found. Starting fresh.");
        }

        return configs;
    }

    public static Configuration collectFromInput(Scanner scanner) {
        Configuration config = new Configuration();
        try {
            System.out.print(">> Enter total number of tickets: ");
            config.totalTickets = scanner.nextInt();
            System.out.print(">> Enter ticket release rate (seconds per ticket): ");
            config.ticketReleaseRate = scanner.nextInt();
            System.out.print(">> Enter number of vendors: ");
            config.vendorCount = scanner.nextInt();
            System.out.print(">> Enter number of customers: ");
            config.customerCount = scanner.nextInt();
            System.out.print(">> Enter customer retrieval rate (seconds per ticket): ");
            config.customerRetrievalRate = scanner.nextInt();
            System.out.print(">> Enter quantity of tickets each customer will buy: ");
            config.customerTicketQuantity = scanner.nextInt();

            // Error handling for maximum ticket capacity
            while (true) {
                System.out.print(">> Enter maximum ticket capacity of the pool: ");
                config.maximumTicketCapacity = scanner.nextInt();
                if (config.maximumTicketCapacity <= config.totalTickets) {
                    break; // Exit loop if valid input
                } else {
                    System.out.println("Error: Maximum ticket capacity cannot be greater than total tickets. Please try again.");
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numeric values.");
            scanner.nextLine(); // Clear invalid input
            return collectFromInput(scanner); // Retry input collection
        }
        scanner.nextLine(); // Consume leftover newline
        return config;
    }


    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getCustomerTicketQuantity() {
        return customerTicketQuantity;
    }

    public void setCustomerTicketQuantity(int customerTicketQuantity) {
        this.customerTicketQuantity = customerTicketQuantity;
    }

    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }

    public void setMaximumTicketCapacity(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
    }
}
