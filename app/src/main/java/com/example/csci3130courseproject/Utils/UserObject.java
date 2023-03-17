package com.example.csci3130courseproject.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class UserObject {
    /** Contains the jobId of all jobs created by the user.*/
    private HashMap<String, Boolean> jobPostings;

    /** Contains the jobId of all jobs taken by the user.*/
    private HashMap<String, Boolean> jobsTaken;

    public UserObject() { }

    public UserObject(HashMap<String, Boolean> jobPostings, HashMap<String, Boolean> jobsTaken) {
        this.jobsTaken = jobsTaken;
        this.jobPostings = jobPostings;
    }

    /**
     * Adds the jobId of a posting that the user has created to their jobPostings list
     * @param jobId String referencing the jobId of the job record
     */
    public void addJobPosting(String jobkey, Boolean jobId) {
        jobPostings.put(jobkey, jobId);
    }

    /**
     * Adds the jobId of a posting that the user has taken to their jobsTaken list
     * @param jobId
     */
    public void addJobsTaken(String jobkey, Boolean jobId) {
        jobsTaken.put(jobkey, jobId);
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
}

