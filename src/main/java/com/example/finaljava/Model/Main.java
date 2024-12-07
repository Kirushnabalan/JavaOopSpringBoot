package com.example.finaljava.Model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner getInput = new Scanner(System.in);
        boolean continueProgram = true;

        while (continueProgram) {
            int totalTickets = 0, ticketReleaseRate = 0, vendorCount = 0, customerCount = 0;
            int customerRetrievalRate = 0, customerTicketQuantity = 0, maximumTicketCapacity = 0;

            // Input for total tickets
            while (totalTickets <= 0) {
                try {
                    System.out.print(">> Enter total number of tickets: ");
                    totalTickets = getInput.nextInt();
                    if (totalTickets <= 0) {
                        System.out.println("Error: Total number of tickets must be a positive integer.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Please enter a valid integer.");
                    getInput.next(); // Clear invalid input
                }
            }

            // Input for ticket release rate
            while (ticketReleaseRate <= 0) {
                try {
                    System.out.print(">> Enter ticket release rate (seconds per ticket): ");
                    ticketReleaseRate = getInput.nextInt();
                    if (ticketReleaseRate <= 0) {
                        System.out.println("Error: Ticket release rate must be a positive integer.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Please enter a valid integer.");
                    getInput.next(); // Clear invalid input
                }
            }

            // Input for vendor count
            while (vendorCount <= 0) {
                try {
                    System.out.print(">> Enter number of vendors: ");
                    vendorCount = getInput.nextInt();
                    if (vendorCount <= 0) {
                        System.out.println("Error: Vendor count must be a positive integer.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Please enter a valid integer.");
                    getInput.next(); // Clear invalid input
                }
            }

            // Input for customer count
            while (customerCount <= 0) {
                try {
                    System.out.print(">> Enter number of customers: ");
                    customerCount = getInput.nextInt();
                    if (customerCount <= 0) {
                        System.out.println("Error: Customer count must be a positive integer.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Please enter a valid integer.");
                    getInput.next(); // Clear invalid input
                }
            }

            // Input for customer retrieval rate
            while (customerRetrievalRate <= 0) {
                try {
                    System.out.print(">> Enter customer retrieval rate (seconds per ticket): ");
                    customerRetrievalRate = getInput.nextInt();
                    if (customerRetrievalRate <= 0) {
                        System.out.println("Error: Customer retrieval rate must be a positive integer.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Please enter a valid integer.");
                    getInput.next(); // Clear invalid input
                }
            }

            // Input for customer ticket quantity
            while (customerTicketQuantity <= 0) {
                try {
                    System.out.print(">> Enter quantity of tickets each customer will buy: ");
                    customerTicketQuantity = getInput.nextInt();
                    if (customerTicketQuantity <= 0) {
                        System.out.println("Error: Customer ticket quantity must be a positive integer.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Please enter a valid integer.");
                    getInput.next(); // Clear invalid input
                }
            }

            // Input for maximum ticket capacity
            while (maximumTicketCapacity <= 0) {
                try {
                    System.out.print(">> Enter maximum ticket capacity of the pool: ");
                    maximumTicketCapacity = getInput.nextInt();
                    if (maximumTicketCapacity <= 0) {
                        System.out.println("Error: Maximum ticket capacity must be a positive integer.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Please enter a valid integer.");
                    getInput.next(); // Clear invalid input
                }
            }

            // Start ticket system
            try {
                TicketPool ticketPool = new TicketPool(maximumTicketCapacity);
                Thread[] vendorThreads = new Thread[vendorCount];
                Thread[] customerThreads = new Thread[customerCount];

                // Start vendor threads
                for (int i = 0; i < vendorCount; i++) {
                    Runnable vendor = new Vendor(totalTickets, ticketReleaseRate, ticketPool);
                    vendorThreads[i] = new Thread(vendor, "Vendor-" + i);
                    vendorThreads[i].start();
                }

                // Start customer threads
                for (int i = 0; i < customerCount; i++) {
                    Runnable customer = new Customer(ticketPool, customerTicketQuantity, customerRetrievalRate);
                    customerThreads[i] = new Thread(customer, "Customer-" + i);
                    customerThreads[i].start();
                }

                System.out.println("All operations are completed.");

            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }

            // Ask the user if they want to restart or exit
            System.out.print("Do you want to restart the system? (1: Restart, 2: Exit): ");
            int choice = 0;
            while (choice != 1 && choice != 2) {
                try {
                    choice = getInput.nextInt();
                    if (choice == 1) {
                        System.out.println("Restarting the system...");
                    } else if (choice == 2) {
                        System.out.println("Exiting the system...");
                        continueProgram = false;
                    } else {
                        System.out.println("Invalid choice. Please enter 1 to restart or 2 to exit.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Please enter 1 or 2.");
                    getInput.next(); // Clear invalid input
                }
            }
        }

        getInput.close();
    }
}
