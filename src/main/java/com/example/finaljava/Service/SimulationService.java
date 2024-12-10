package com.example.finaljava.Service;

import com.example.finaljava.Model.*;
import com.example.finaljava.Repository.CustomerEntityRepo;
import com.example.finaljava.Repository.VendorRepository;
import com.example.finaljava.Entity.CustomerEntity;
import com.example.finaljava.Entity.VendorEntity;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationService {
    @Autowired
    private CustomerEntityRepo customerEntityRepo; // Repository for Customer
    @Autowired
    private VendorRepository vendorRepository; // VendorRepository for saving vendor entities

    private static final String CONFIG_FILE_PATH = "configurations.json";
    private boolean isSimulationRunning = false;
    private static final ArrayList<Thread> activeThreads = new ArrayList<>();

    // Start simulation based on configuration
    public void startSimulation(Configuration config) {
        if (!isSimulationRunning) {
            isSimulationRunning = true;

            TicketPool ticketPool = new TicketPool(config.getMaximumTicketCapacity());
            Vendor[] vendors = new Vendor[10];
            Customer[] customers = new Customer[10];

            // Start vendor threads and save Vendor data to database
            for (int i = 0; i < vendors.length; i++) {
                vendors[i] = new Vendor(config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool,
                        config.getTheaterName(), config.getEventName(),
                        config.getTicketPrice());

                VendorEntity vendorEntity = new VendorEntity(
                        "Vendor-" + (i + 1),               // Vendor name
                        config.getTheaterName(),           // Theater name
                        config.getEventName(),             // Event name
                        config.getTicketPrice() // Ticket price as BigDecimal
                );
                vendorRepository.save(vendorEntity);

                // Start the vendor thread
                Thread vendorThread = new Thread(vendors[i], "Vendor-" + (i + 1));
                activeThreads.add(vendorThread);
                vendorThread.start();
            }

            // Start customer threads and save Customer data to database
            for (int i = 0; i < customers.length; i++) {
                // Initialize the Customer instance
                customers[i] = new Customer(
                        ticketPool,
                        config.getTotalTickets(),
                        config.getCustomerRetrievalRate()
                );

                // Save CustomerEntity to the database
                CustomerEntity customerEntity = new CustomerEntity(
                        "Customer-" + (i + 1),                // Customer name
                        config.getTotalTickets(),             // Total tickets
                        config.getCustomerRetrievalRate()     // Ticket retrieval rate
                );
                customerEntityRepo.save(customerEntity);

                // Start the customer thread
                Thread customerThread = new Thread(customers[i], "Customer-" + (i + 1));
                activeThreads.add(customerThread);
                customerThread.start();
            }

            System.out.println("Simulation started. Enter 'stop' to halt threads and view the summary.");

            // Wait for all threads to finish and join
            for (Thread thread : activeThreads) {
                try {
                    thread.join();  // Wait for each thread to finish
                } catch (InterruptedException e) {
                    System.err.println("Error waiting for thread to finish: " + e.getMessage());
                }
            }

            // Clear the list of active threads
            activeThreads.clear();
        }

        // Save the Ticket Log data to the database
    }

    // Stop the simulation
    public void stopSimulation() {
        if (isSimulationRunning) {
            for (Thread thread : activeThreads) {
                thread.interrupt();  // Interrupt all threads
            }
            Logger.newMessage("All threads stopped.");
            isSimulationRunning = false;
        } else {
            System.out.println("No simulation is running.");
        }
    }

    // Save the configuration to the JSON file
    public void saveConfiguration(Configuration config) {
        try {
            // Load existing configurations
            List<Configuration> configs = loadAllConfigurations();

            // Assign a new Theater ID based on the size of the configurations list
            config.setTheaterID(configs.size() + 1);

            // Add the new configuration to the list
            configs.add(config);

            // Save the updated list of configurations back to the file
            Gson gson = new Gson();
            try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
                gson.toJson(configs, writer);
            }

            System.out.println("Configuration saved successfully with Theater ID: " + config.getTheaterID());
        } catch (Exception e) {
            System.err.println("Error saving configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Load all configurations from the JSON file
    public List<Configuration> loadAllConfigurations() {
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
            System.err.println("Error loading configurations: " + e.getMessage());
        }

        return new ArrayList<>(); // Return an empty list if any errors occur
    }

    // Load a configuration by its ID
    public Configuration loadConfigurationById(int id) {
        List<Configuration> configs = loadAllConfigurations();
        if (id >= 0 && id < configs.size()) {
            return configs.get(id);
        }
        throw new IllegalArgumentException("Configuration with ID " + id + " not found.");
    }
}
