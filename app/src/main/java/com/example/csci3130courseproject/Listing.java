package com.example.csci3130courseproject;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class Listing extends DatabaseObject {
    /**
     * Create a new Listing object from app data
     */
    public Listing(String employerId, String title, int hours, int salary, HashMap<String, Boolean> employeeIdMap) {
        // Adding values to be replicated to Firebase
        setValue("employer", employerId);
        setValue("title", title);
        setValue("hours", hours);
        setValue("salary", salary);
        setValue("employees", employeeIdMap);
    }

    public Listing(DataSnapshot snapshot) {
        buildFromSnapshot(snapshot);
    }

    /**
     * Create a new Listing object from a Firebase record
     * @param key
     */
    public Listing(String key){
        getRecord(key);
    }
}
