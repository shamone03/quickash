package com.example.csci3130courseproject;

import android.location.Location;

import java.io.Serializable;
import java.sql.Timestamp;

public class JobPostingObject {

    private User jobPoster;
    private String jobTitle;
    private String priority;
    private double jobDuration;
    private double jobSalary;
    private Location jobLocation;

    public JobPostingObject(){

    }

    public JobPostingObject(User poster, String title, String priority, double jobSalary,
                            double jobDuration, Location location){
        this.jobPoster = poster;
        this.jobTitle = title;
        this.priority = priority;
        this.jobSalary = jobSalary;
        this.jobDuration = jobDuration;
        this.jobLocation = location;
    }
    public double getJobDuration() { return jobDuration; }

    public double getJobSalary() {
        return jobSalary;
    }

    public Location getJobLocation() {
        return jobLocation;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getPriority() {
        return priority;
    }

    public User getJobPoster() {
        return jobPoster;
    }


}
