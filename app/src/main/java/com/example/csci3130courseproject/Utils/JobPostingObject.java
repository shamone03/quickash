package com.example.csci3130courseproject.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class JobPostingObject {
    private String jobPosterID;
    private String jobEmployeeID;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference jobRef = database.getReference("jobs");
    private HashMap<String, Boolean> employees;
    private String jobTitle;
    private String jobDescription;
    private Priority.PRIORITY priority;
    private int jobDuration;
    private double jobSalary;
    private JobLocation jobLocation;
    private boolean jobComplete;
    private boolean employeeSelected;

    public JobPostingObject(){
        this.jobComplete = false;
        this.employeeSelected = false;
        this.jobEmployeeID = "";
    }

    public JobPostingObject(String posterID, HashMap<String, Boolean> userApplied, String title, Priority.PRIORITY priority, double jobSalary,
                            int jobDuration, JobLocation location, Boolean completed){
        this.jobPosterID = posterID;
        this.employees = userApplied;
        this.jobTitle = title;
        this.priority = priority;
        this.jobSalary = jobSalary;
        this.jobDuration = jobDuration;
        this.jobLocation = location;
        this.jobComplete = false;
        this.employeeSelected = false;
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

    public void setJobDescription(String jobDescription){ this.jobDescription = jobDescription; }

    public void setEmployees(HashMap<String, Boolean> employees){ this.employees = employees; }

    public void setPriority(Priority.PRIORITY priority) {
        this.priority = priority;
    }

    public void setJobEmployeeID(String id) {
        this.jobEmployeeID = id;
    }

    public void setJobComplete(Boolean b) {
        this.jobComplete = b;
    }

    public void setEmployeeSelected(Boolean b) {
        this.employeeSelected = b;
    }

    public void addEmployee(String userid){ this.employees.put(userid, false); }

    // Get job attributes

    public boolean getJobComplete(){
        return jobComplete;
    }

    public boolean getEmployeeSelected(){
        return employeeSelected;
    }
    public String getJobEmployeeID() {
        return jobEmployeeID;
    }

    public double getJobDuration() { return jobDuration; }

    public double getJobSalary() {
        return jobSalary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDescription() { return jobDescription; }

    public Priority.PRIORITY getPriority() {
        return priority;
    }

    public String getJobPoster() {
        return jobPosterID;
    }

    public HashMap<String, Boolean> getEmployees() { return employees; }

    public JobLocation getJobLocation() {
        return(jobLocation);
    }

    public void setJobLocation(JobLocation jobLocation) {
        this.jobLocation = jobLocation;
    }

    public void applicantAccepted(String applicantId, String jobId) {
        this.jobEmployeeID = applicantId;
        this.employeeSelected = true;
    }
    public void completedJob(String jobId){
        this.jobComplete = true;
        DatabaseReference jobIDRef = jobRef.child(jobId);
        jobIDRef.setValue(this);
    }
}

