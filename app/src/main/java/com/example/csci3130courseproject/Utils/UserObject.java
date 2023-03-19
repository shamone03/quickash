package com.example.csci3130courseproject.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class UserObject {
    private String username;
    /** Contains the jobId of all jobs created by the user.*/
    private HashMap<String, Boolean> jobPostings;

    /** Contains the jobId of all jobs taken by the user.*/
    private HashMap<String, Boolean> jobsTaken;

    private double employerRating;

    private double employeeRating;

    /** adding user rating to userObject, as it won't seem to let me add it right on firebase **/
    private double employerRatingScore;
    private double employeeRatingScore;
    private int numberOfEmployeeRatings;
    private int numberOfEmployerRatings;

    public UserObject() { }

    public UserObject(String username, HashMap<String, Boolean> jobPostings, HashMap<String, Boolean> jobsTaken){
        this.username = username;
        this.jobsTaken = jobsTaken;
        this.jobPostings = jobPostings;
        //Start users with 0 rating
        this.employerRating = 0;
        this.employeeRating = 0;
        this.numberOfEmployerRatings = 0;
        this.numberOfEmployeeRatings = 0;
        this.employeeRating = 0;
        this.employerRating = 0;
    }

    /**
     * Adds the jobId of a posting that the user has taken to their jobsTaken list
     * @param jobId
     */
    public void addJobsTaken(String jobkey, Boolean jobId) {
        jobsTaken.put(jobkey, jobId);
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
    public HashMap<String, Boolean> getJobPostings() {
        return jobPostings;
    }

    /**
     * @return The list containing all job postings taken by the user
     */
    public HashMap<String, Boolean> getJobsTaken() {
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

    public double getUserRating(){
        return userRatingScore/numberOfRatings;
    }

    public void rateUser(Double rating) {
        numberOfRatings += 1;
        userRatingScore += rating;
    }

}

