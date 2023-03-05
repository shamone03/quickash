package com.example.csci3130courseproject.Callbacks;

import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.example.csci3130courseproject.Utils.Listing;

import java.util.HashMap;

public interface JobsCallback {
    JobPostingObject onJobDetails(JobPostingObject jobDetails);
}
