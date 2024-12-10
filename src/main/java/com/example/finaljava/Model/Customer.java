package com.example.finaljava.Model;

public class Customer implements Runnable {

    private TicketPool ticketPool;
    private final int customerBuyCount;
    private final int customerReleaseRate;

    public Customer(TicketPool ticketPool, int customerBuyCount, int customerReleaseRate) {
        this.ticketPool = ticketPool;
        this.customerBuyCount = customerBuyCount;
        this.customerReleaseRate = customerReleaseRate;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < customerBuyCount; i++) {
                Thread.sleep(customerReleaseRate * 1000); // Simulate rate in seconds

                Ticket ticket = ticketPool.getTicket();
                if (ticket != null) {
                    Logger.info(Thread.currentThread().getName() +" bought a ticket. Remaining tickets in pool: " + ticketPool.getTicketSize());
                } else {
                    Logger.info(Thread.currentThread().getName() + " could not buy a ticket. Pool is empty.");
                }
            }

            // Add this message when the thread finishes all operations
            Logger.info( "Customer has finished buying tickets.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
