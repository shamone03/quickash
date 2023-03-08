package com.example.csci3130courseproject.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class UserObject {
    /** Contains the jobId of all jobs created by the user.*/
    private List<String> jobPostings;

    /** Contains the jobId of all jobs taken by the user.*/
    private List<String> jobsTaken;

    public UserObject() { }

    public UserObject(ArrayList<String> jobPostings, ArrayList<String> jobsTaken) {
        this.jobsTaken = jobsTaken;
        this.jobPostings = jobPostings;
    }

    /**
     * Adds the jobId of a posting that the user has created to their jobPostings list
     * @param jobId String referencing the jobId of the job record
     */
    public void addJobPosting(String jobId) {
        jobPostings.add(jobId);
    }

    /**
     * Adds the jobId of a posting that the user has taken to their jobsTaken list
     * @param jobId
     */
    public void addJobsTaken(String jobId) {
        jobsTaken.add(jobId);
    }

    /**
     * @return The list containing all job postings created by the user
     */
    public List<String> getJobPostings() {
        return jobPostings;
    }

    /**
     * @return The list containing all job postings taken by the user
     */
    public List<String> getJobsTaken() {
        return jobsTaken;
    }
}

