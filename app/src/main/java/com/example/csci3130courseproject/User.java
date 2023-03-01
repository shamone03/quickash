package com.example.csci3130courseproject;

import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class User extends DatabaseObject{
    public User() {

    }

    public User(String username) {
        setLocalValue("username", username);
        setLocalValue("jobsPosted", new ArrayList<String>());
        setLocalValue("jobsTaken", new ArrayList<String>());
    }

    /**
     * Adds the jobId of a posting that the user has created to their jobPostings list
     * @param jobId String referencing the jobId of the job record
     */
    public void addJobPosting(String jobId) {
        List<String> list = (ArrayList) getLocalValue("jobsPosted");
        list.add(jobId);
        setLocalValue("jobsPosted",list);
    }

    /**
     * Adds the jobId of a posting that the user has taken to their jobsTaken list
     * @param jobId
     */
    public void addJobTaken(String jobId) {
        List<String> list = (ArrayList) getLocalValue("jobsTaken");
        list.add(jobId);
        setLocalValue("jobsTaken",list);
    }

    /**
     * @return The list containing all job postings created by the user
     */
    public List<String> getJobPostings() {
        return (ArrayList) getLocalValue("jobsTaken");
    }

    /**
     * @return The list containing all job postings taken by the user
     */
    public List<String> getJobsTaken() {
        return (ArrayList) getLocalValue("jobsTaken");
    }
}

