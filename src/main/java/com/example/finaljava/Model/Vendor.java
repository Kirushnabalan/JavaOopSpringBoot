package com.example.finaljava.Model;

import java.math.BigDecimal;

public class Vendor implements Runnable {
    private int ticketsPerVendor;
    private int vendorReleaseRate;
    private TicketPool ticketPool;

    public Vendor(int ticketsPerVendor, int vendorReleaseRate, TicketPool ticketPool) {
        this.ticketsPerVendor = ticketsPerVendor;
        this.vendorReleaseRate = vendorReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < ticketsPerVendor; i++) {
                // Simulating ticket release by vendor
                Thread.sleep(vendorReleaseRate * 1000); // Simulate rate in seconds

                ticketPool.addTicket(new Ticket(i,"varadaa",new BigDecimal(1000))); // Add a ticket to the pool
                System.out.println(Thread.currentThread().getName() + " released a ticket. Total tickets in pool: ");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " was interrupted.");
        }
    }
}
