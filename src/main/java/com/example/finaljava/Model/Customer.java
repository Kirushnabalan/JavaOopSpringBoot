package com.example.finaljava.Model;

public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int customerBuyCount;
    private int customerReleaseRate;

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
                    Logger.info(Thread.currentThread().getName() + " bought a ticket. Remaining tickets in pool: " );
                } else {
                    Logger.info(Thread.currentThread().getName() + " could not buy a ticket. Pool is empty.");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " was interrupted.");
        }
    }
}
