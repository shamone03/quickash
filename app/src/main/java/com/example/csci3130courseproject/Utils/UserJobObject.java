package com.example.csci3130courseproject.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserJobObject {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference jobsReference = database.getReference("Listing");
    private DatabaseReference userReference = database.getReference("users");

    private JobPostingObject currentJob;
    private HashMap<String, UserObject> applicants;

    public UserJobObject(String currentJobID){}




}
