package com.example.finaljava.Model;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {

    //create
    private final Queue<Ticket> tickets; // Use Ticket type in queue
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = new LinkedList<>();
    }

    // Add ticket to the pool
    public synchronized void addTicket(Ticket ticket) {
        while (tickets.size() >= maxCapacity) {
            try {
                wait(); // Wait for space to become available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupted status
                return;
            }
        }
        Logger.info("Adding ticket " + ticket);
        tickets.add(ticket); // Add the ticket to the pool
        notifyAll(); // Notify any threads waiting for a ticket
    }

    // Get a ticket from the pool
    public synchronized Ticket getTicket() {
        while (tickets.isEmpty()) {
            try {
                wait(); // Wait for a ticket to become available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupted status
                return null;
            }
        }
        Ticket ticket = tickets.poll(); // Retrieve and remove a ticket from the pool
        notifyAll(); // Notify any threads waiting to add tickets
        return ticket;
    }

    // Check if the pool has tickets
    public synchronized int getTicketSize() {
        return tickets.size();
    }
}
