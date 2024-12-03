package com.example.finaljava.Model;

import java.math.BigDecimal;

public class Ticket {
    private int ticketId;
    private String eventName;
    private BigDecimal eventPrice;

    public Ticket(int ticketId, String eventName, BigDecimal eventPrice) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.eventPrice = eventPrice;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public BigDecimal getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(BigDecimal eventPrice) {
        this.eventPrice = eventPrice;
    }

    @Override
    public String toString() {
        return "Ticket{ID=" + ticketId + ", Event='" + eventName + "', Rate=" + eventPrice + "}";
    }

}
