package com.example.csci3130courseproject;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public Map<String, Object> mapValues() {
        Map<String, Object> map = new HashMap<>();

        map.put("title", title);
        map.put("description", description);
        map.put("employerId", employerId);
        map.put("duration",duration);
        map.put("salary", salary);
        map.put("urgency", urgency);

        return map;
    }
}
