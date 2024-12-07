package com.example.finaljava.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "New")
public class DetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ticketCapacity;
    private int vendorCount;
    private int ticketsPerVendor;
    private int vendorReleaseRate;
    private int customerCount;
    private int customerReleaseRate;
    private int customerBuyCount;

    public DetailsEntity(Long id, int ticketCapacity,
                         int vendorCount, int ticketsPerVendor, int vendorReleaseRate, int customerCount, int customerReleaseRate, int customerBuyCount) {
        this.id = id;
        this.ticketCapacity = ticketCapacity;
        this.vendorCount = vendorCount;
        this.ticketsPerVendor = ticketsPerVendor;
        this.vendorReleaseRate = vendorReleaseRate;
        this.customerCount = customerCount;
        this.customerReleaseRate = customerReleaseRate;
        this.customerBuyCount = customerBuyCount;
    }

    public DetailsEntity() {
    }

    @Override
    public String toString() {
        return "DetailsEntity{" +
                "id=" + id +
                ", ticketCapacity=" + ticketCapacity +
                ", vendorCount=" + vendorCount +
                ", ticketsPerVendor=" + ticketsPerVendor +
                ", vendorReleaseRate=" + vendorReleaseRate +
                ", customerCount=" + customerCount +
                ", customerReleaseRate=" + customerReleaseRate +
                ", customerBuyCount=" + customerBuyCount +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTicketCapacity() {
        return ticketCapacity;
    }

    public void setTicketCapacity(int ticketCapacity) {
        this.ticketCapacity = ticketCapacity;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }

    public int getTicketsPerVendor() {
        return ticketsPerVendor;
    }

    public void setTicketsPerVendor(int ticketsPerVendor) {
        this.ticketsPerVendor = ticketsPerVendor;
    }

    public int getVendorReleaseRate() {
        return vendorReleaseRate;
    }

    public void setVendorReleaseRate(int vendorReleaseRate) {
        this.vendorReleaseRate = vendorReleaseRate;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getCustomerReleaseRate() {
        return customerReleaseRate;
    }

    public void setCustomerReleaseRate(int customerReleaseRate) {
        this.customerReleaseRate = customerReleaseRate;
    }

    public int getCustomerBuyCount() {
        return customerBuyCount;
    }

    public void setCustomerBuyCount(int customerBuyCount) {
        this.customerBuyCount = customerBuyCount;
    }
}
