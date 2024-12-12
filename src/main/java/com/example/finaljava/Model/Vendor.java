package com.example.finaljava.Model;


public class Vendor implements Runnable {

    private final int ticketsPerVendor;
    private final int vendorReleaseRate;
    private final TicketPool ticketPool;
    private final String theaterName;
    private final String eventName;
    private final Double ticketPrice;


    public String getTheaterName() {
        return theaterName;
    }

    public String getEventName() {
        return eventName;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }
// Autowire the TicketActionLogRepository

    // Constructor without the repository injection
    public Vendor(int ticketsPerVendor, int vendorReleaseRate, TicketPool ticketPool,
                  String theaterName, String eventName, Double ticketPrice) {
        this.ticketsPerVendor = ticketsPerVendor;
        this.vendorReleaseRate = vendorReleaseRate;
        this.ticketPool = ticketPool;
        this.theaterName = theaterName;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= ticketsPerVendor; i++) {
                // Simulate ticket release delay
                Thread.sleep(vendorReleaseRate * 1000);

                // Create a new ticket using configuration details
                Ticket ticket = new Ticket(
                        i,
                        eventName,
                        theaterName,
                        ticketPrice // Ticket price
                );

                // Add the ticket to the pool
                ticketPool.addTicket(ticket);

                // Save the log entry to the database
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        } catch (Exception e) {
            Logger.error("Error in ticket release: " + e.getMessage());
        }
    }
}
