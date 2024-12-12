package com.example.finaljava.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String SetFilePath = "config.json"; // Path to save the configuration file
    private static final ArrayList<Thread> activeThreads = new ArrayList<>(); // List to store active threads

    public static void main(String[] args) {
        Scanner getInput = new Scanner(System.in); // Scanner to read user input
        List<Configuration> configs = Configuration.loadAllConfigurations(); // Load all saved configurations
        Configuration selectedConfig;

        while (true) {
            try {
                System.out.println("===== Available Configurations =====");
                if (configs.isEmpty()) {
                    System.out.println("No configurations found.");
                } else {
                    for (int i = 0; i < configs.size(); i++) {
                        Configuration config = configs.get(i);
                        System.out.printf("%d. Theater ID: %d, Event: %s, Theater Name: %s%n",
                                i + 1, config.getTheaterID(), config.getEventName(), config.getTheaterName());
                    }
                }

                // Asking for Yes/No input for loading a configuration
                System.out.println(">> Would you like to load a configuration from the file? (yes/no)");
                String loadOption = Configuration.readInput(getInput);  // readInput() used for yes/no question

                if ("yes".equalsIgnoreCase(loadOption)) {
                    if (configs.isEmpty()) {
                        System.out.println("No configurations available to load.");
                        continue;
                    }

                    System.out.print(">> Enter the Theater ID of the configuration to load: ");
                    try {
                        int theaterID = getInput.nextInt();  // Handling user input for Theater ID
                        getInput.nextLine(); // Clear newline

                        selectedConfig = configs.stream()
                                .filter(config -> config.getTheaterID() == theaterID)
                                .findFirst()
                                .orElse(null);

                        if (selectedConfig == null) {
                            System.out.println("No configuration found with the provided Theater ID.");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input for Theater ID. Please enter a valid number.");
                        getInput.nextLine(); // Clear invalid input
                        continue;
                    }

                } else if ("no".equalsIgnoreCase(loadOption)) {
                    // Gather configuration data from the user
                    selectedConfig = Configuration.getDataFromPrompt(getInput);

                    // Asking if the user wants to save the configuration (Yes/No)
                    System.out.println(">> Would you like to save this configuration to the file? (yes/no)");
                    String saveOption = Configuration.readInput(getInput);  // readInput() used for yes/no question

                    if ("yes".equalsIgnoreCase(saveOption)) {
                        try {
                            saveConfiguration(selectedConfig); // Saving the configuration
                        } catch (Exception e) {
                            Logger.error("Error saving configuration: " + e.getMessage());
                        }
                    } else {
                        handleStartRestartOrExit(getInput); // Proceed to handle start/restart/exit
                    }
                } else {
                    Logger.warn("Invalid input. Please enter 'yes' or 'no'.");
                    continue;
                }

                startThreads(selectedConfig, getInput); // Start threads with the selected configuration
                break; // Exit the loop once threads are started

            } catch (Exception e) {
                Logger.error("An error occurred: " + e.getMessage());
                getInput.nextLine(); // Clear the scanner buffer
            }
        }
    }

    private static void showInputSummary(Configuration config) {
        // Display a summary of the configuration that the user has inputted
        System.out.println("\n===== Configuration Summary =====");
        System.out.println("Event Name: " + config.getEventName());
        System.out.println("Theater Name: " + config.getTheaterName());
        System.out.println("Total Tickets: " + config.getTotalTickets());
        System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate() + " seconds per ticket");
        System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate() + " seconds per ticket");
        System.out.println("Maximum Ticket Capacity: " + config.getMaximumTicketCapacity());
        System.out.println("Ticket Price: " + config.getTicketPrice());
    }

    private static void startThreads(Configuration config, Scanner getInput) {
        TicketPool ticketPool = new TicketPool(config.getMaximumTicketCapacity());
        Vendor[] vendors = new Vendor[config.getVendorCount()];
        Customer[] customers = new Customer[config.getCustomerCount()];

        // Start vendor threads
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = new Vendor(config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool, config.getTheaterName(), config.getEventName(), config.getTicketPrice());
            Thread vendorThread = new Thread(vendors[i], "Vendor-" + (i + 1));
            activeThreads.add(vendorThread);
            vendorThread.start();
        }

        // Start customer threads
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(ticketPool, config.getTotalTickets(), config.getCustomerRetrievalRate());
            Thread customerThread = new Thread(customers[i], "Customer-" + (i + 1));
            activeThreads.add(customerThread);
            customerThread.start();
        }

        Logger.info("Threads have started. Type 'stop' to halt threads and view the summary.");

        // Wait for user input to stop threads
        while (true) {
            try {
                String command = getInput.nextLine();  // readInput() used for 'stop' command
                if ("stop".equalsIgnoreCase(command)) {
                    stopThreads(); // Stop all threads
                    showInputSummary(config); // Show input summary
                    handleRestartOrExit(getInput); // Handle restart/exit decision
                    break;
                }
            } catch (Exception e) {
                Logger.error("Error reading input: " + e.getMessage());
            }
        }

        // Wait for all threads to finish and join
        for (Thread thread : activeThreads) {
            try {
                thread.join();  // Wait for each thread to finish
                Logger.info(thread.getName() + " has finished.");
            } catch (InterruptedException e) {
                Logger.error("Error waiting for thread to finish: " + e.getMessage());
            }
        }

        // Clear the list of active threads
        activeThreads.clear();
    }

    private static void stopThreads() {
        for (Thread thread : activeThreads) {
            thread.interrupt(); // Interrupt each thread
        }
        System.out.println(" ");
        Logger.newMessage("Threads have been stopped.");
    }

    private static void saveConfiguration(Configuration config) {
        try {
            config.SaveDataToJsonFile(); // Save the configuration to a file
            Logger.info("Configuration saved to file: " + SetFilePath);
            System.out.println("Configuration saved successfully.");
            handleStartRestartOrExit(new Scanner(System.in)); // Ask the user what to do next
        } catch (Exception e) {
            Logger.error("Error saving configuration to file: " + e.getMessage());
        }
    }

    private static void handleRestartOrExit(Scanner getInput) {
        while (true) {
            try {
                System.out.println(">> Would you like to restart or exit? (restart/exit)");
                String choice = getInput.nextLine();  // Read the user's input

                // Validation to ensure valid input
                if ("restart".equalsIgnoreCase(choice)) {
                    main(new String[0]); // Restart the program
                    break; // Exit the loop
                } else if ("exit".equalsIgnoreCase(choice)) {
                    Logger.newMessage("Exiting the program. Goodbye!");
                    System.exit(0); // Exit the program
                } else {
                    Logger.warn("Invalid input. Please enter 'restart' or 'exit'.");
                    System.out.println("Invalid choice. Please enter either 'restart' or 'exit'."); // User prompt for valid input
                }
            } catch (Exception e) {
                Logger.error("Error handling restart/exit choice: " + e.getMessage());
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private static void handleStartRestartOrExit(Scanner getInput) {
        while (true) {
            try {
                System.out.println(">> Would you like to start, restart, or exit? (start/restart/exit)");
                String choice = getInput.nextLine();  // Read the user's input

                // Validation to ensure valid input
                if ("start".equalsIgnoreCase(choice)) {
                    Logger.newMessage("Starting with the saved configuration...");
                    break; // Proceed with the saved configuration
                } else if ("restart".equalsIgnoreCase(choice)) {
                    main(new String[0]); // Restart the program
                    break; // Exit the loop
                } else if ("exit".equalsIgnoreCase(choice)) {
                    Logger.newMessage("Exiting the program. Goodbye!");
                    System.exit(0); // Exit the program
                } else {
                    Logger.warn("Invalid input. Please enter 'start', 'restart', or 'exit'.");
                    System.out.println("Invalid choice. Please enter either 'start', 'restart', or 'exit'."); // User prompt for valid input
                }
            } catch (Exception e) {
                Logger.error("Error handling start/restart/exit choice: " + e.getMessage());
                System.out.println("An error occurred. Please try again.");
            }
        }
    }
}