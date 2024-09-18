package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bill {
    private int billId;
    private String description;
    private int numOfBands;
    private String bookerName;
    private LocalDate dateTime;
    private int cost;
    private String flyer;
    private int venueId;

    // Constructors
    public Bill() {}

    public Bill(int billId, String description, int numOfBands, String bookerName,
                LocalDate dateTime, int cost, String flyer, int venueId) {
        this.billId = billId;
        this.description = description;
        this.numOfBands = numOfBands;
        this.bookerName = bookerName;
        this.dateTime = dateTime;
        this.cost = cost;
        this.flyer = flyer;
        this.venueId = venueId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumOfBands() {
        return numOfBands;
    }

    public void setNumOfBands(int numOfBands) {
        this.numOfBands = numOfBands;
    }

    public String getBookerName() {
        return bookerName;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getFlyer() {
        return flyer;
    }

    public void setFlyer(String flyer) {
        this.flyer = flyer;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }
}
