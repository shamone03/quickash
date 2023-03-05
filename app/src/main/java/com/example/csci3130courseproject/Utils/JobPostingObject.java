package com.example.csci3130courseproject.Utils;

import android.location.Location;

import java.util.HashMap;

public class JobPostingObject {

    private String jobPosterID;
    private HashMap<String, Boolean> userApplied;
    private String jobTitle;
    private Priority.PRIORITY priority;
    private int jobDuration;
    private double jobSalary;
    private Location jobLocation;

    public JobPostingObject(){}

    public JobPostingObject(String posterID, HashMap<String, Boolean> userApplied, String title, Priority.PRIORITY priority, double jobSalary,
                            int jobDuration, Location location){
        this.jobPosterID = posterID;
        this.userApplied = userApplied;
        this.jobTitle = title;
        this.priority = priority;
        this.jobSalary = jobSalary;
        this.jobDuration = jobDuration;
        this.jobLocation = location;
    }
    // Set job attributes
    public void setJobDuration(int jobDuration) {
        this.jobDuration = jobDuration;
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

    public void setPriority(Priority.PRIORITY priority) {
        this.priority = priority;
    }

    // Get job attributes

    public double getJobDuration() { return jobDuration; }

    public double getJobSalary() {
        return jobSalary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Priority.PRIORITY getPriority() {
        return priority;
    }

    public String getJobPoster() {
        return jobPosterID;
    }

    public HashMap<String, Boolean> getUserApplied() { return userApplied; }


}

