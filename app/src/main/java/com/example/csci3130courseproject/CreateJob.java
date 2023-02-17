package com.example.csci3130courseproject;

public interface CreateJob {

    default JobPostingObject createJob(){
        return new JobPostingObject();
    }

    default boolean jobTitleNotEmpty(String jobTitle){
        return jobTitle.isEmpty();
    }

}
