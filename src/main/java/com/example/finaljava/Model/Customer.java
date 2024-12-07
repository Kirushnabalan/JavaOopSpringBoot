package com.example.finaljava.Model;

import com.example.finaljava.LogsFileforThredSave.LogsSave;
public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int buyCount;
    private final int retrievalRate;

    public Customer(TicketPool ticketPool, int buyCount, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.buyCount = buyCount;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        for (int i = 0; i < buyCount; i++) {
            Ticket ticket = ticketPool.getTicket();
            LogsSave.log(Thread.currentThread().getName() + " bought " + ticket);

            try {
                Thread.sleep(retrievalRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
