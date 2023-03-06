package com.example.csci3130courseproject.Callbacks;

import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.example.csci3130courseproject.Utils.Listing;

import java.util.HashMap;
import java.util.List;

public interface JobsCallback {
    List<JobPostingObject> onJobDetails(List<JobPostingObject> jobDetails);
}
