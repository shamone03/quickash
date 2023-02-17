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

    /**
     * Create a new Listing object from a DataSnapshot
     * @param snapshot DataSnapshot holding the record and its child data objects
     */
    public Listing(DataSnapshot snapshot) {
        buildFromSnapshot(snapshot);
    }

    /**
     * Create a new Listing object from a Firebase record key
     * @param key String key referencing the record in Firebase
     */
    public Listing(String key){
        getRecord(key);
    }
}
