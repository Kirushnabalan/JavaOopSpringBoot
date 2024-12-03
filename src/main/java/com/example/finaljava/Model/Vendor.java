package com.example.finaljava.Model;

import java.math.BigDecimal;

public class Vendor implements Runnable {
    private final int ticketsToRelease;
    private final int releaseRate;
    private final TicketPool ticketPool;

    public Vendor(int ticketsToRelease, int releaseRate, TicketPool ticketPool) {
        this.ticketsToRelease = ticketsToRelease;
        this.releaseRate = releaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        for (int i = 0; i < ticketsToRelease; i++) {
            try {
                // Create and release a ticket
                Ticket ticket = new Ticket(i, "Event-" + i, new BigDecimal(1000 + (i * 50)));
                ticketPool.createTicket(ticket);
                System.out.println(Thread.currentThread().getName() + " created " + ticket);
                String event = Thread.currentThread().getName() + " created " + ticket;
                LogsSave.log(event);
                // Simulate delay between actions
                Thread.sleep(releaseRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
