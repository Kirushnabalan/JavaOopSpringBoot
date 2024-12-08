package com.example.finaljava.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TicketPool {
    private final BlockingQueue<Ticket> tickets = new LinkedBlockingQueue<>(); // Use Ticket type in queue
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Add ticket to the pool
    public synchronized void addTicket(Ticket ticket) {
        while (tickets.size() >= maxCapacity) {
            try {
                System.out.println("[TicketPool]: Pool is full, waiting to add tickets...");
                wait(); // Wait for space to become available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupted status
                System.out.println("[TicketPool]: Add ticket operation interrupted.");
                return;
            }
        }
        tickets.add(ticket); // Add the ticket to the pool
        System.out.println("[TicketPool]: Created " + ticket);
        notifyAll(); // Notify any threads waiting for a ticket
    }

    // Get a ticket from the pool
    public synchronized Ticket getTicket() {
        while (tickets.isEmpty()) {
            try {
                System.out.println("[TicketPool]: Pool is empty, waiting for tickets...");
                wait(); // Wait for a ticket to become available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupted status
                System.out.println("[TicketPool]: Get ticket operation interrupted.");
                return null;
            }
        }
        Ticket ticket = tickets.poll(); // Retrieve and remove a ticket from the pool
        System.out.println("[TicketPool]: Sold " + ticket);
        notifyAll(); // Notify any threads waiting to add tickets
        return ticket;
    }

    // Check if the pool has tickets
    public boolean hasTickets() {
        return !tickets.isEmpty(); // Check if the queue has tickets
    }
    public synchronized int getSize() {
        return tickets.size();
    }
}
