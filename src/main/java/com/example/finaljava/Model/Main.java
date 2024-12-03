package com.example.finaljava.Model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner getInput = new Scanner(System.in);
        int maximumTicketCapacity = 0;
        int vendorCount = 0;
        int ticketCount = 0;
        int vendorRetrievalRate = 0;
        int customerCount = 0;
        int customerTicketQuantity = 0;
        int customerRetrievalRate = 0;
        Configuration config = new Configuration();
        // Input for maximum ticket capacity
        while (maximumTicketCapacity <= 0) {
            try {
                System.out.print(">> Enter the Maximum Ticket Capacity (positive value): ");
                maximumTicketCapacity = getInput.nextInt();
                if (maximumTicketCapacity <= 0) {
                    System.out.println("Error: Maximum Ticket Capacity must be a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                getInput.next(); // Clear invalid input
            }
        }

        // Input for vendor count
        while (vendorCount <= 0) {
            try {
                System.out.print(">> Vendor \n  Enter the vendor count (positive value): ");
                vendorCount = getInput.nextInt();
                if (vendorCount <= 0) {
                    System.out.println("Error: Vendor count must be a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                getInput.next(); // Clear invalid input
            }
        }

// Input for ticket count
        while (ticketCount <= 0 || ticketCount > maximumTicketCapacity) {
            try {
                System.out.print("  Enter the tickets each vendor will create (lower than Maximum Ticket Capacity): ");
                ticketCount = getInput.nextInt();

                // Validate the range in the same try block
                if (ticketCount <= 0 || ticketCount > maximumTicketCapacity) {
                    throw new IllegalArgumentException("Error: Ticket count must be a positive integer and lower than the Maximum Ticket Capacity.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                getInput.next(); // Clear invalid input
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); // Print specific range error message
            }
        }


        // Input for vendor retrieval rate
        while (vendorRetrievalRate <= 0) {
            try {
                System.out.print("  Enter the vendor retrieval rate (seconds, positive value): ");
                vendorRetrievalRate = getInput.nextInt();
                if (vendorRetrievalRate <= 0) {
                    System.out.println("Error: Vendor retrieval rate must be a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                getInput.next(); // Clear invalid input
            }
        }

        // Input for customer count
        while (customerCount <= 0) {
            try {
                System.out.print(">> Customer \n  Enter the customer count (positive value): ");
                customerCount = getInput.nextInt();
                if (customerCount <= 0) {
                    System.out.println("Error: Customer count must be a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                getInput.next(); // Clear invalid input
            }
        }

        // Input for customer ticket quantity
        while (customerTicketQuantity <= 0) {
            try {
                System.out.print("  Enter the tickets each customer will buy (positive value): ");
                customerTicketQuantity = getInput.nextInt();
                if (customerTicketQuantity <= 0) {
                    System.out.println("Error: Customer ticket quantity must be a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                getInput.next(); // Clear invalid input
            }
        }

        // Input for customer retrieval rate
        while (customerRetrievalRate <= 0) {
            try {
                System.out.print("  Enter the customer retrieval rate (seconds, positive value): ");
                customerRetrievalRate = getInput.nextInt();
                if (customerRetrievalRate <= 0) {
                    System.out.println("Error: Customer retrieval rate must be a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                getInput.next(); // Clear invalid input
            }
        }

        try {
            // Initialize the TicketPool and participants
            TicketPool ticketPool = new TicketPool(maximumTicketCapacity);
            Vendor[] vendors = new Vendor[vendorCount];
            Customer[] customers = new Customer[customerCount];

            // Start vendor threads
            for (int i = 0; i < vendors.length; i++) {
                vendors[i] = new Vendor(ticketCount, vendorRetrievalRate, ticketPool);
                Thread vendorThread = new Thread(vendors[i], "Vendor-" + i);
                vendorThread.start();
            }

            // Start customer threads
            for (int i = 0; i < customers.length; i++) {
                customers[i] = new Customer(ticketPool,customerRetrievalRate, customerTicketQuantity);
                Thread customerThread = new Thread(customers[i], "Customer-" + i);
                customerThread.start();
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            getInput.close();
        }

        String jsonData = String.format("""
            {
              "maximumTicketCapacity": %d,
              "vendorCount": %d,
              "ticketCount": %d,
              "vendorRetrievalRate": %d,
              "customerCount": %d,
              "customerTicketQuantity": %d,
              "customerRetrievalRate": %d
            }
            """,
                maximumTicketCapacity, vendorCount, ticketCount, vendorRetrievalRate,
                customerCount, customerTicketQuantity, customerRetrievalRate
        );

        // Save configuration
        config.saveConfiguration(jsonData);
        String loadedJson = config.loadConfiguration();

    }
}
