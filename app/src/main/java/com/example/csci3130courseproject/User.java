package com.example.csci3130courseproject;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class User {
    private List<String> jobPostings;
    private List<String> jobsTaken;

    public User() { }

    public User(ArrayList<String> jobPostings, ArrayList<String> jobsTaken) {
        this.jobsTaken = jobsTaken;
        this.jobPostings = jobPostings;
    }

    public void addJobPosting(String jobId) {
        jobPostings.add(jobId);
    }

    public void addJobsTaken(String jobId) {
        jobsTaken.add(jobId);
    }

    public List<String> getJobPostings() {
        return jobPostings;
    }

    public List<String> getJobsTaken() {
        return jobsTaken;
    }
}

