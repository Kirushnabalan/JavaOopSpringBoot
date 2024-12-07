package com.example.finaljava.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    private int maximumTicketCapacity;
    private int vendorCount;
    private int ticketCount;
    private int vendorRetrievalRate;
    private int customerCount;
    private int customerTicketQuantity;
    private int customerRetrievalRate;

    // Constructor
    public Configuration(int maximumTicketCapacity, int vendorCount, int ticketCount,
                         int vendorRetrievalRate, int customerCount, int customerTicketQuantity,
                         int customerRetrievalRate) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.vendorCount = vendorCount;
        this.ticketCount = ticketCount;
        this.vendorRetrievalRate = vendorRetrievalRate;
        this.customerCount = customerCount;
        this.customerTicketQuantity = customerTicketQuantity;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    // Method to save configuration to a JSON file
    public void saveConfiguration(String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
            System.out.println("Configuration saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    // Static method to load configuration from a JSON file
    public static Configuration loadConfiguration(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
            return null;
        }
    }

    // Getters (optional, for accessing individual fields)
    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public int getVendorRetrievalRate() {
        return vendorRetrievalRate;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public int getCustomerTicketQuantity() {
        return customerTicketQuantity;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }
}
