package com.example.finaljava.Model;

public class Ticket {

    private int ticketId;
    private String eventName;
    private String theaterName;
    private Double eventPrice;

    public Ticket(int ticketId, String eventName, String theaterName, Double eventPrice) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.theaterName = theaterName;
        this.eventPrice = eventPrice;
    }

    public Ticket() {

    }

    @Override
    public String toString() {
        return "Ticket{ID=" + ticketId + ","+"TheaterName = "+ theaterName+","+"Event=" + eventName + ", Rate=" + eventPrice + "}"  ;
    }

}
