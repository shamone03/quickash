package com.example.csci3130courseproject;

public interface CreateJob {

    default JobPostingObject createJob(){
        return new JobPostingObject();
    }

    default boolean acceptableJobTitle(String jobTitle){
        return jobTitle.isEmpty();
    }

    default boolean acceptableJobSalary(double jobSalary){
        return jobSalary > 0;
    }

    default boolean acceptableJobDuration(double jobDuration){
        boolean jobDurationNotNeg = jobDuration > 0;
        boolean jobDurationNotMax = jobDuration < 24.01;
        return jobDurationNotNeg && jobDurationNotMax;
    }

    default boolean acceptablePriority(String jobPriority){
        return !(jobPriority.contains("priority"));
    }



}
