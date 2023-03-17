package com.example.csci3130courseproject.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class UserObject {
    private String username;
    /** Contains the jobId of all jobs created by the user.*/
    private Map<String, String> jobPostings;

    /** Contains the jobId of all jobs taken by the user.*/
    private Map<String, String> jobsTaken;

    private double employerRating = 0;

    private double employeeRating = 0;

    public UserObject() { }

    public UserObject(HashMap<String, String> jobPostings, HashMap<String, String> jobsTaken) {
        this.jobsTaken = jobsTaken;
        this.jobPostings = jobPostings;
    }

    public UserObject(String username, HashMap<String, String> jobPostings, HashMap<String, String> jobsTaken, double employerRating, double employeeRating){
        this.username = username;
        this.jobsTaken = jobsTaken;
        this.jobPostings = jobPostings;
        this.employerRating = employerRating;
        this.employeeRating = employeeRating;
    }

    @Override
    public String toString() {
        return "UserObject{" +
                "jobPostings=" + jobPostings +
                ", jobsTaken=" + jobsTaken +
                '}';
    }

    /**
     * @return The list containing all job postings created by the user
     */
    public Map<String, String> getJobPostings() {
        return jobPostings;
    }

    /**
     * @return The list containing all job postings taken by the user
     */
    public Map<String, String> getJobsTaken() {
        return jobsTaken;
    }

//    public List<JobPostingObject> getJobsDetails(HashMap<String, String> jobs) {
//
//    }

    public double getEmployeeRating() {
        return employeeRating;
    }

    public double getEmployerRating() {
        return employerRating;
    }

    public String getUsername() {
        return username;
    }
}

