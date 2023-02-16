package com.example.csci3130courseproject;

import android.location.Location;

import java.io.Serializable;
import java.sql.Timestamp;

public class JobPostingObject {

    private User jobPoster;
    private String jobTitle;
    private int priority;
    private Timestamp jobExpiry;
    private Location jobLocation;

    public JobPostingObject(){

    }

    public JobPostingObject(User poster, String title, int priority, Timestamp expires, Location location){
        this.jobPoster = poster;
        this.jobTitle = title;
        this.priority = priority;
        this.jobExpiry = expires;
        this.jobLocation = location;
    }

}
