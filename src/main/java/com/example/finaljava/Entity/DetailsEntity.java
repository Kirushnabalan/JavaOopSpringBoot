package com.example.finaljava.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Newone")
public class DetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalTickets;
    private int ticketReleaseRate;
    private int vendorCount;
    private int customerCount;
    private int customerRetrievalRate;
    private int customerTicketQuantity;
    private int maximumTicketCapacity;

    public DetailsEntity(Long id, int totalTickets, int ticketReleaseRate, int vendorCount,
                         int customerCount, int customerRetrievalRate, int customerTicketQuantity, int maximumTicketCapacity) {
        this.id = id;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendorCount = vendorCount;
        this.customerCount = customerCount;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerTicketQuantity = customerTicketQuantity;
        this.maximumTicketCapacity = maximumTicketCapacity;
    }

    public DetailsEntity() {
    }

    @Override
    public String toString() {
        return "DetailsEntity{" +
                "id=" + id +
                ", totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", vendorCount=" + vendorCount +
                ", customerCount=" + customerCount +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", customerTicketQuantity=" + customerTicketQuantity +
                ", maximumTicketCapacity=" + maximumTicketCapacity +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getCustomerTicketQuantity() {
        return customerTicketQuantity;
    }

    public void setCustomerTicketQuantity(int customerTicketQuantity) {
        this.customerTicketQuantity = customerTicketQuantity;
    }

    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }

    public void setMaximumTicketCapacity(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
    }
}
