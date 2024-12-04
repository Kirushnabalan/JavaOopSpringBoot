package com.example.finaljava.Model;

import java.util.LinkedList;
import java.util.Queue;

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
                Thread.currentThread().interrupt(); // Preserve the interruption status.
                System.out.println("Thread interrupted during createTicket");
                return; // Exit to avoid inconsistent state.
            }
        }
        ticketQueue.add(ticket);
        notifyAll(); // Notify waiting threads that a new ticket is available.
    }

    public synchronized Ticket getTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve the interruption status.
                System.out.println("Thread interrupted during getTicket");
                return null; // Exit gracefully.
            }
        }
        Ticket ticket = ticketQueue.poll();
        notifyAll(); // Notify threads that a space is available in the queue.
        return ticket;
    }
}
