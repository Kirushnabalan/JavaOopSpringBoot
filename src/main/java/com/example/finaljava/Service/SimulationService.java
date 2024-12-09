package com.example.finaljava.Service;

import com.example.finaljava.Model.*;
import com.example.finaljava.LogsFileforThredSave.LogsSave;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationService {

    private static final String CONFIG_FILE_PATH = "configurations.json";
    private boolean isSimulationRunning = false;

    private Vendor[] vendors;
    private Customer[] customers;

    private Thread[] vendorThreads;
    private Thread[] customerThreads;

    // Start simulation based on configuration
    public void startSimulation(Configuration config) {
        if (!isSimulationRunning) {
            isSimulationRunning = true;

            // Initialize ticket pool using the totalTickets and maximumTicketCapacity
            TicketPool ticketPool = new TicketPool(config.getMaximumTicketCapacity());

            // Create vendor threads
            vendors = new Vendor[config.getVendorCount()];
            vendorThreads = new Thread[config.getVendorCount()];

            for (int i = 0; i < vendors.length; i++) {
                vendors[i] = new Vendor(config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool);
                vendorThreads[i] = new Thread(vendors[i], "Vendor-" + i);
                vendorThreads[i].start();
            }

            // Create customer threads
            customers = new Customer[config.getCustomerCount()];
            customerThreads = new Thread[config.getCustomerCount()];

            for (int i = 0; i < customers.length; i++) {
                customers[i] = new Customer(ticketPool, config.getCustomerTicketQuantity(), config.getCustomerRetrievalRate());
                customerThreads[i] = new Thread(customers[i], "Customer-" + i);
                customerThreads[i].start();
            }

            System.out.println("Simulation started successfully!");
        }

        try {
            // Wait for all vendor threads to complete
            for (Thread vendorThread : vendorThreads) {
                vendorThread.join();
            }

            // Wait for all customer threads to complete
            for (Thread customerThread : customerThreads) {
                customerThread.join();
            }

            // Write simulation logs to file
            LogsSave.writeToJsonFile("simulation_events.json");
            System.out.println("All customers have bought tickets.");
        } catch (InterruptedException e) {
            System.err.println("Simulation interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    // Stop simulation and interrupt all threads
    public void stopSimulation() {
        if (isSimulationRunning) {
            // Interrupt vendor threads
            for (Thread vendorThread : vendorThreads) {
                if (vendorThread != null) {
                    vendorThread.interrupt();
                }
            }

            // Interrupt customer threads
            for (Thread customerThread : customerThreads) {
                if (customerThread != null) {
                    customerThread.interrupt();
                }
            }

            isSimulationRunning = false;
            System.out.println("Simulation stopped.");
        }
    }

    // Save the configuration to the JSON file
    public void saveConfiguration(Configuration config) {
        try {
            // Load existing configurations from the JSON file
            List<Configuration> configs = loadAllConfigurations();

            // Add the new configuration
            configs.add(config);

            // Save the updated list back to the file
            try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
                new Gson().toJson(configs, writer);
            }

            System.out.println("Configuration saved successfully!");
        } catch (Exception e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

    // Load all configurations from the JSON file
    public List<Configuration> loadAllConfigurations() {
        try {
            File file = new File(CONFIG_FILE_PATH);

            // Check if the file exists, create it if not
            if (!file.exists()) {
                file.createNewFile();
                return new ArrayList<>(); // Return an empty list as there are no configurations yet
            }

            // Read the JSON file and convert it to a list of Configuration objects
            try (FileReader reader = new FileReader(file)) {
                Configuration[] configs = new Gson().fromJson(reader, Configuration[].class);
                if (configs != null) {
                    return new ArrayList<>(List.of(configs));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading configurations: " + e.getMessage());
        }

        return new ArrayList<>(); // Return an empty list if any errors occur
    }

    // Load a configuration by its index (ID)
    public Configuration loadConfigurationById(int id) {
        List<Configuration> configs = loadAllConfigurations();
        if (id >= 0 && id < configs.size()) {
            return configs.get(id); // Return the configuration at the given index
        }
        throw new IllegalArgumentException("Configuration with ID " + id + " not found.");
    }

    public List<Configuration> getAllConfigs() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream("/configurations.json")) {
            return objectMapper.readValue(inputStream, new TypeReference<List<Configuration>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read configurations from JSON file", e);
        }
    }
}
