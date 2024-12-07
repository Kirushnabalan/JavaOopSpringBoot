package com.example.finaljava.Service;

import com.example.finaljava.Entity.DetailsEntity;
import com.example.finaljava.LogsFileforThredSave.LogsSave;
import com.example.finaljava.Model.*;
import com.example.finaljava.Repository.TicketRepository; // Correct repository import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SimulationService {

    @Autowired
    private TicketRepository detailsRepository; // Correct repository for DetailsEntity

    private boolean isSimulationRunning = false;

    private Vendor[] vendors;
    private Customer[] customers;

    private Thread[] vendorThreads;
    private Thread[] customerThreads;

    public void startSimulation(DetailsEntity config) {
        if (!isSimulationRunning) {
            isSimulationRunning = true;

            // Initialize ticket pool
            TicketPool ticketPool = new TicketPool(config.getTicketCapacity());

            // Create vendor threads
            vendors = new Vendor[config.getVendorCount()];
            vendorThreads = new Thread[config.getVendorCount()];

            for (int i = 0; i < vendors.length; i++) {
                vendors[i] = new Vendor(config.getTicketsPerVendor(), config.getVendorReleaseRate(), ticketPool);
                vendorThreads[i] = new Thread(vendors[i], "Vendor-" + i);
                vendorThreads[i].start();
            }

            // Create customer threads
            customers = new Customer[config.getCustomerCount()];
            customerThreads = new Thread[config.getCustomerCount()];

            for (int i = 0; i < customers.length; i++) {
                customers[i] = new Customer(ticketPool, config.getCustomerBuyCount(),config.getCustomerReleaseRate());
                customerThreads[i] = new Thread(customers[i], "Customer-" + i);
                customerThreads[i].start();
            }

            System.out.println("Simulation started successfully!");
        }

        try{
            // Wait for all vendor threads to complete
            for (Thread vendorThread : vendorThreads) {
                vendorThread.join();
            }

            // Wait for all customer threads to complete
            for (Thread customerThread : customerThreads) {
                customerThread.join();
            }

            LogsSave.writeToJsonFile("simulation_events.json");
            System.out.println("All customers have bought tickets.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopSimulation() {
        if (isSimulationRunning) {
            // Interrupt vendor threads
            if (vendorThreads != null) {
                for (Thread vendorThread : vendorThreads) {
                    if (vendorThread != null) {
                        vendorThread.interrupt();
                    }
                }
            }

            // Interrupt customer threads
            if (customerThreads != null) {
                for (Thread customerThread : customerThreads) {
                    if (customerThread != null) {
                        customerThread.interrupt();
                    }
                }
            }

            isSimulationRunning = false;
            System.out.println("Simulation stopped.");
        }
    }

    public DetailsEntity saveConfiguration(DetailsEntity config) {
        return detailsRepository.save(config); // Use the correct repository
    }

    public List<DetailsEntity> getAllConfigs() {
        return detailsRepository.findAll(); // Fetch all details from the repository
    }

    public Optional<DetailsEntity> loadConfiguration(Long id) {
        // Use findById to retrieve the DetailsEntity by id
        return detailsRepository.findById(Math.toIntExact(id));
    }

}