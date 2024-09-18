package org.example.model;

public class Band {
    private int bandId;
    private String name;
    private String hometown;
    private String state;
    private String country;
    private String bandcampLink;
    private String contact;

    // Constructors

    public Band() {}

    public Band(int id, String name, String hometown, String state,
                String country, String bandcampLink, String contact) {
        this.bandId = id;
        this.name = name;
        this.hometown = hometown;
        this.state = state;
        this.country = country;
        this.bandcampLink = bandcampLink;
        this.contact = contact;
    }

    // Getters and Setters

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int id) {
        this.bandId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
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

    public String getBandcampLink() {
        return bandcampLink;
    }

    public void setBandcampLink(String bandcampLink) {
        this.bandcampLink = bandcampLink;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
