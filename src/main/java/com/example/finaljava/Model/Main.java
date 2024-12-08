package com.example.finaljava.Model;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String DEFAULT_FILE_PATH = "config.json"; // Default file path for saving configuration

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config = null;

        while (true) {
            try {
                System.out.println(">> Would you like to save the configuration to a file? (yes/no)");
                String saveOption = readInput(scanner);

                if ("yes".equalsIgnoreCase(saveOption)) {
                    config = Configuration.collectFromInput(scanner);

                    try {
                        config.saveToJson(DEFAULT_FILE_PATH);
                        System.out.println("Configuration saved to file: " + DEFAULT_FILE_PATH);
                    } catch (Exception e) {
                        System.out.println("Error saving configuration to file: " + e.getMessage());
                    }
                } else if ("no".equalsIgnoreCase(saveOption)) {
                    config = Configuration.collectFromInput(scanner);
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                    continue;
                }

                while (true) {
                    System.out.println(">> Enter an action: (start/restart/exit)");
                    String action = readInput(scanner);

                    switch (action.toLowerCase()) {
                        case "start":
                            System.out.println("Thread started. Type 'stop' to stop the threads and view the summary.");
                            startThread(config, scanner);
                            break;
                        case "restart":
                            System.out.println("Restarting configuration collection...");
                            break;
                        case "stop":
                            stopThreads();
                            displaySummary(config);
                            break;
                        case "exit":
                            System.out.println("Exiting the program. Goodbye!");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Invalid action. Please enter 'start', 'restart', 'stop', or 'exit'.");
                            continue;
                    }

                    if ("restart".equalsIgnoreCase(action)) {
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear the scanner buffer in case of invalid input
            }
        }
    }

    // Add a list of threads to be stopped later
    private static ArrayList<Thread> activeThreads = new ArrayList<>();

    public static void startThread(Configuration config, Scanner scanner) {
        TicketPool ticketPool = new TicketPool(config.getMaximumTicketCapacity());
        Vendor[] vendors = new Vendor[config.getVendorCount()];
        Customer[] customers = new Customer[config.getCustomerCount()];

        // Initialize and start vendor threads
        for (int i = 0; i < config.getVendorCount(); i++) {
            vendors[i] = new Vendor(config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool);
            Thread vendorThread = new Thread(vendors[i]);
            activeThreads.add(vendorThread);  // Add to active threads list
            vendorThread.start();
        }

        // Initialize and start customer threads
        for (int i = 0; i < config.getCustomerCount(); i++) {
            customers[i] = new Customer(ticketPool, config.getCustomerTicketQuantity(), config.getCustomerRetrievalRate());
            Thread customerThread = new Thread(customers[i]);
            activeThreads.add(customerThread); // Add to active threads list
            customerThread.start();
        }

        // Monitor for stop command while threads are running
        while (true) {
            String action = readInput(scanner);
            if ("stop".equalsIgnoreCase(action)) {
                stopThreads();
                displaySummary(config);
                break;
            }
        }

        // Wait for all threads to finish by calling join on each thread
        for (Thread thread : activeThreads) {
            try {
                thread.join(); // This ensures the main thread waits for the worker threads to finish
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle interruption properly
            }
        }
        System.out.println("All threads have completed execution.");
    }

    // Method to stop all threads
    public static void stopThreads() {
        // Set flag or interrupt threads to stop
        for (Thread thread : activeThreads) {
            thread.interrupt();
        }

        System.out.println("Threads have been stopped.");
    }

    private static void displaySummary(Configuration config) {
        System.out.println("===== Configuration Summary =====");
        System.out.println("Total Tickets: " + config.getTotalTickets());
        System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate() + " seconds per ticket");
        System.out.println("Number of Vendors: " + config.getVendorCount());
        System.out.println("Number of Customers: " + config.getCustomerCount());
        System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate() + " seconds per ticket");
        System.out.println("Customer Ticket Quantity: " + config.getCustomerTicketQuantity());
        System.out.println("Maximum Ticket Capacity: " + config.getMaximumTicketCapacity());
    }

    private static String readInput(Scanner scanner) {
        while (!scanner.hasNextLine()) {
            System.out.println("No input detected. Please try again.");
        }
        return scanner.nextLine().trim();
    }
}
