package com.example.csci3130courseproject;

import android.location.Location;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;

public class JobPostingObject {

    private String jobPosterID;
    private HashMap<String, Boolean> userApplied;
    private String jobTitle;
    private String priority;
    private double jobDuration;
    private double jobSalary;
    private Location jobLocation;

    public JobPostingObject(){}

    public JobPostingObject(String posterID, HashMap<String, Boolean> userApplied, String title, String priority, double jobSalary,
                            double jobDuration, Location location){
        this.jobPosterID = posterID;
        this.userApplied = userApplied;
        this.jobTitle = title;
        this.priority = priority;
        this.jobSalary = jobSalary;
        this.jobDuration = jobDuration;
        this.jobLocation = location;
    }
    // Set job attributes
    public void setJobDuration(double jobDuration) {
        this.jobDuration = jobDuration;
    }

    public void setJobLocation(Location jobLocation) {
        this.jobLocation = jobLocation;
    }

    public void setJobPoster(String jobPosterID) {
        this.jobPosterID = jobPosterID;
    }

    public void setJobSalary(double jobSalary) {
        this.jobSalary = jobSalary;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    // Get job attributes

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

    public String getJobPoster() {
        return jobPosterID;
    }


}
