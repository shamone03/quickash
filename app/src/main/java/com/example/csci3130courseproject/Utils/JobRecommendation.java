package com.example.csci3130courseproject.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobRecommendation {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("users");
    private DatabaseReference jobRef = database.getReference("jobs");
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private String userId;
    private UserObject userObject;
    private String jobTitle;
    private HashMap<String, Boolean> jobsTakenIds;
    private ArrayList<String[]> jobsTakenTitles;
    private ArrayList<String[]> allJobTitles;

    public JobRecommendation() {
        
    }

    public void populateJobs() {
        userId = currentUser.getUid();
        userRef.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    userObject = task.getResult().getValue(UserObject.class);
                    if (userObject == null) {
                        return;
                    } else {
                        jobsTakenIds = (HashMap<String, Boolean>) userObject.getJobsTaken();
                        if (jobsTakenIds == null) {
                            System.out.println("jobsTakenIds was null");
                            return;
                        }
                        for (Map.Entry<String, Boolean> job : jobsTakenIds.entrySet()) {
                            jobRef.child(job.getKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> listingTask) {
                                    if (!listingTask.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", listingTask.getException());
                                    }
                                    else {
                                        JobPostingObject jobPosting = listingTask.getResult().getValue(JobPostingObject.class);
                                        String jobTitle = jobPosting.getJobTitle();
                                        //split separates the first word of the string from the first
                                        //call [0] to get it
                                        jobsTakenTitles.add(jobTitle.split(" ", 2));
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
        jobRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, JobPostingObject> jobs = (HashMap<String,JobPostingObject>) snapshot.getValue();
                for(JobPostingObject job: jobs.values()) {
                    allJobTitles.add(job.getJobTitle().split(" ", 2));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public String recommendJob(ArrayList<String[]> taken, ArrayList<String[]> all) {
        String recommendation;
        for (String[] title: taken) {
            String firstWord = title[0];
            for (String[] titleCompare: all) {
                String firstWordToCompare = titleCompare[0];
                if(firstWord.equals(firstWordToCompare)){
                    recommendation = String.format("%s %s", firstWord, title[2]);
                    return recommendation;
                }
            }
        }
        recommendation = "None";
        return recommendation;
    }
}
