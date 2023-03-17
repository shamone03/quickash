package com.example.csci3130courseproject.Utils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class UserJobs extends DatabaseObject{

    public UserJobs (FirebaseUser currentUser){
        ArrayList<String> currentJobs = new ArrayList<>();
        ArrayList<String> previousJobs = new ArrayList<>();
        ArrayList<String> currentCreatedJobs = new ArrayList<>();
        ArrayList<String> previousCreatedJobs = new ArrayList<>();


        setValue("User",currentUser.getUid());
        setValue("currentJobs",currentJobs);
        setValue("previousJobs",previousJobs);
        setValue("currentCreatedJobs",currentCreatedJobs);
        setValue("previousCreatedJobs",previousCreatedJobs);
    }

    public UserJobs (FirebaseUser currentUser, String status){

    }

    public UserJobs (DataSnapshot currentSnapshot) {
        buildFromSnapshot(currentSnapshot);
    }

    public void addCurrentCreatedJobs(String jobID) {
        ArrayList<String> currentCreatedJobs;

        if (getValue("currentCreatedJobs") == null) {
            currentCreatedJobs = new ArrayList<>();
            currentCreatedJobs.add(jobID);
            setValue("currentCreatedJobs", currentCreatedJobs);
        } else {
            currentCreatedJobs = (ArrayList<String>) getValue("currentCreatedJobs");
            currentCreatedJobs.add(jobID);
        }

        updateData();
    }

    public void addPreviousCreatedJobs(String jobID){
        ArrayList<String> currentCreatedJobs;
        ArrayList<String> previousCreatedJob;

        if(getValue("previousCreatedJobs") == null){
            previousCreatedJob = new ArrayList<>();
        }else{
            previousCreatedJob = (ArrayList<String>) getValue("previousCreatedJobs");
        }

        if(getValue("currentCreatedJobs") == null){

        }else{
            currentCreatedJobs = (ArrayList<String>) getValue("currentCreatedJobs");
            if(currentCreatedJobs.contains(jobID)){
                currentCreatedJobs.remove(jobID);
                previousCreatedJob.add(jobID);
            }
        }

        updateData();
    }


    public void addCurrentJob(String jobID){
        ArrayList<String> currentJobs;

        if(getValue("currentJobs") == null){
            currentJobs = new ArrayList<>();
            currentJobs.add(jobID);
            setValue("currentJobs",currentJobs);
        }else{
            currentJobs = (ArrayList<String>) getValue("currentJobs");
            currentJobs.add(jobID);
        }

        updateData();

    }

    public void changeJobToPrevious(UserJobs currentUserJobs, String jobID){
        ArrayList<String> currentJobs;
        ArrayList<String> previousJobs;

        if(getValue("previousJobs") == null){
            previousJobs = new ArrayList<>();
        }else{
            previousJobs = (ArrayList<String>) getValue("previousJobs");
        }

        if(getValue("currentJobs") == null){

        }else{
            currentJobs = (ArrayList<String>) getValue("currentJobs");
            if(currentJobs.contains(jobID)){
                currentJobs.remove(jobID);
                previousJobs.add(jobID);
            }
        }

        updateData();

    }


}
