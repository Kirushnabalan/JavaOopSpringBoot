package com.example.finaljava.Model;

import com.example.finaljava.LogsFileforThredSave.LogsSave;

import java.math.BigDecimal;

public class Vendor implements Runnable {
    private final int ticketsToReleaseByVendor;
    private final int releaseRate;
    private final TicketPool ticketPool;
    public Vendor(int ticketsToReleaseByVendor, int releaseRate, TicketPool ticketPool) {
        this.ticketsToReleaseByVendor = ticketsToReleaseByVendor;
        this.releaseRate = releaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
            for (int i = 0; i <ticketsToReleaseByVendor; i++) {
                Ticket ticket = new Ticket(i, "Event-" + i, new BigDecimal(1000 + (i * 50)));
                ticketPool.createTicket(ticket);
                String event = Thread.currentThread().getName() + " created " + ticket;
                LogsSave.log(event);

                try {
                    // Create and release a ticket
                    // Simulate delay between actions
                    Thread.sleep(releaseRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


}
