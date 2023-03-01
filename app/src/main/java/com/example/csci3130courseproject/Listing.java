package com.example.csci3130courseproject;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class Listing extends DatabaseObject {
    /**
     * Create a new Listing object from app data
     */
    public Listing(String employerId, String title, int hours, int salary, Priority.PRIORITY priority) {
        // Adding values to be replicated to Firebase
        setLocalValue("employer", employerId);
        setLocalValue("title", title);
        setLocalValue("hours", hours);
        setLocalValue("salary", salary);
        setLocalValue("priority", priority.toString());
        setLocalValue("employees", new HashMap<String, Boolean>());
    }

    /**
     * Create a new Listing object from a DataSnapshot
     * @param snapshot DataSnapshot holding the record and its child data objects
     */
    public Listing(DataSnapshot snapshot) {
        build(snapshot);
    }

    /**
     * Create a new Listing object from a Firebase record key
     * @param key String key referencing the record in Firebase
     */
    public Listing(String key){
        getRecord(key);
    }

    public Listing() {

    };


    /**
     *
     * @param userId ID of the user to add to the employees table
     */
    public void addEmployee(String userId) {
        HashMap<String, Boolean> employees;

        if (getLocalValue("employees") == null) {
            employees = new HashMap<>();
            employees.put(userId,false);
            setLocalValue("employees",employees);
        } else {
            employees = (HashMap<String, Boolean>) getLocalValue("employees");
            employees.put(userId,false);
        }

        updateRecord();
    }
}
