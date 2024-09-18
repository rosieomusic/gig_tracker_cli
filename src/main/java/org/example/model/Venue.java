package org.example.model;

public class Venue {
    private int venueId;
    private String name;
    private String city;
    private String state;
    private String country;
    private String contact;
    private String email_instagram;

    // Constructors

    public Venue() {}

    public Venue(int venueId, String name, String city, String state,
                 String country, String contact, String email_instagram) {
        this.venueId = venueId;
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.contact = contact;
        this.email_instagram = email_instagram;
    }

    // Getters and Setters


    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail_instagram() {
        return email_instagram;
    }

    public void setEmail_instagram(String email_instagram) {
        this.email_instagram = email_instagram;
    }
}
