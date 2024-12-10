package com.example.finaljava.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "some1") // Table name in the database
public class VendorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate primary key
    private Long id;

    private String name;          // Vendor name
    private String theaterName;   // Theater name
    private String eventName;     // Event name
    private Double ticketPrice; // Ticket price

    // Constructor with all fields
    public VendorEntity(String name, String theaterName, String eventName, Double ticketPrice) {
        this.name = name;
        this.theaterName = theaterName;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    // Default constructor
    public VendorEntity() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
