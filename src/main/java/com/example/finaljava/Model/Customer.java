package com.example.finaljava.Model;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int buyCount;
    private final int releaseRate;

    public Customer(TicketPool ticketPool, int buyCount, int releaseRate) {
        this.ticketPool = ticketPool;
        this.buyCount = buyCount;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        for (int i = 0; i < buyCount; i++) {
            try {
                // Simulate buying a ticket
                Ticket ticket = ticketPool.getTicket();
                System.out.println(Thread.currentThread().getName() + " bought " + ticket);
                String event = Thread.currentThread().getName() + " bought " + ticket;
                LogsSave.log(event); // Log the event
                // Simulate delay between actions
                Thread.sleep(releaseRate*1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
