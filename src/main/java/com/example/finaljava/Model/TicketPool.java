package com.example.finaljava.Model;


import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a ticket pool for managing tickets with a fixed capacity.
 * Supports thread-safe ticket creation and retrieval.
 */
public class TicketPool {
    private final Queue<Ticket> ticketQueue;
    private final int capacity;

    public TicketPool(int capacity) {
        this.capacity = capacity;
        this.ticketQueue = new LinkedList<>();
    }

    public synchronized void createTicket(Ticket ticket) {
        while (ticketQueue.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        Logger.info("Creating new ticket");
        ticketQueue.add(ticket);
        notifyAll();
    }

    public synchronized Ticket getTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Ticket ticket = ticketQueue.poll();
        Logger.info("Returning ticket");
        notifyAll();
        return ticket;
    }


}
