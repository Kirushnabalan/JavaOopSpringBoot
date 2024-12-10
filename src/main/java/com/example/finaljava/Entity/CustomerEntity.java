package com.example.finaljava.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Customer") // Table name in the database
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate primary key
    private Long id;

    private String name;          // Customer name
    private int totalTickets;     // Total tickets the customer wants to retrieve
    private int ticketRetrievalRate; // Rate at which tickets are retrieved

    // Constructor with all fields
    public CustomerEntity(String name, int totalTickets, int ticketRetrievalRate) {
        this.name = name;
        this.totalTickets = totalTickets;
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    // Default constructor
    public CustomerEntity() {
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

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketRetrievalRate() {
        return ticketRetrievalRate;
    }

    public void setTicketRetrievalRate(int ticketRetrievalRate) {
        this.ticketRetrievalRate = ticketRetrievalRate;
    }
}
