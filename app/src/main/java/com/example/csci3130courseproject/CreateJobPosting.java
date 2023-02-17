package com.example.csci3130courseproject;

public class CreateJobPosting {

    public CreateJobPosting(){ }

    // Helper Methods:
    private double toDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            System.err.println("Double conversion Error: " + e);
            return -1;
        }
    }

    private int toInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e){
            System.err.println("Integer conversion Error: " + e);
            return -1;
        }
    }

    // Main methods:

    public CreateJobPosting(String posterID, String jobTitle, String jobSalary, String jobDuration, Priority.PRIORITY priority){
        JobPostingObject newPosting = new JobPostingObject();
        newPosting.setJobPoster(posterID);
        newPosting.setJobTitle(jobTitle);
        newPosting.setJobSalary(toDouble(jobSalary));
        newPosting.setJobDuration(toInt(jobDuration));
        newPosting.setPriority(priority);

        Listing newPost = new Listing(newPosting);
        newPost.setRecord();
    }
    public CreateJobPosting(JobPostingObject jobObject) {
        new Listing(jobObject);
    }

}
