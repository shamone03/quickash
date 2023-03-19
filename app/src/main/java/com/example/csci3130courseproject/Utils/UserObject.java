package com.example.csci3130courseproject.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class UserObject {
    private String username;
    /** Contains the jobId of all jobs created by the user.*/
    private Map<String, Boolean> jobPostings;

    /** Contains the jobId of all jobs taken by the user.*/
    private Map<String, Boolean> jobsTaken;

    private double employerRating = 0;

    private double employeeRating = 0;

    public UserObject() {  }

    public UserObject(String username) { this.username = username; }

    public UserObject(HashMap<String, Boolean> jobPostings, HashMap<String, Boolean> jobsTaken) {
        this.jobsTaken = jobsTaken;
        this.jobPostings = jobPostings;
    }

    public UserObject(String username, HashMap<String, Boolean> jobPostings, HashMap<String, Boolean> jobsTaken, double employerRating, double employeeRating){
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
    public Map<String, Boolean> getJobPostings() {
        return jobPostings;
    }

    /**
     * @return The list containing all job postings taken by the user
     */
    public Map<String, Boolean> getJobsTaken() {
        return jobsTaken;
    }

//    public List<JobPostingObject> getJobsDetails(JobsCallback callback) {
//        ArrayList<JobPostingObject> ret = new ArrayList<>();
//        FirebaseDatabase.getInstance().getReference("jobs");
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

