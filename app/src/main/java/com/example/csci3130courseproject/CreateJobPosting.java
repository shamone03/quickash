package com.example.csci3130courseproject;

public class CreateJobPosting {

    public CreateJobPosting(){ }

    public CreateJobPosting(String posterID, String jobTitle, double jobSalary, int jobDuration, Priority.PRIORITY priority){
        JobPostingObject newPosting = new JobPostingObject();
        newPosting.setJobPoster(posterID);
        newPosting.setJobTitle(jobTitle);
        newPosting.setJobSalary(jobSalary);
        newPosting.setJobDuration(jobDuration);
        newPosting.setPriority(priority);

        new Listing(newPosting);
    }
    public CreateJobPosting(JobPostingObject jobObject) {
        new Listing(jobObject);
    }

}
