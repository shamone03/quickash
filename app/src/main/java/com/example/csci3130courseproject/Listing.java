package com.example.csci3130courseproject;

public class Listing extends DatabaseObject {
    private int listingId, employerId, duration, urgency, salary;
    // Find appropriate data types for date and location
    private String title, description;

    public Listing(int listingId, int employerId, int duration, int urgency, int salary,
                   String title, String description) {
        this.listingId = listingId;
        this.employerId = employerId;
        this.duration = duration;
        this.urgency = urgency;
        this.salary = salary;
        this.title = title;
        this.description = description;
    }

    public int getListingId() {
        return listingId;
    }

    public int getEmployerId() {
        return employerId;
    }

    public int getDuration() {
        return duration;
    }

    public int getUrgency() {
        return urgency;
    }

    public int getSalary() {
        return salary;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
