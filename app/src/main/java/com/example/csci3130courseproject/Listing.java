package com.example.csci3130courseproject;

import java.util.HashMap;

public class Listing extends DatabaseObject {
    /**
     * Create a new Listing object from app data
     */
    public Listing(String employerId, String title, HashMap<String, Boolean> employeeIdMap) {
        // Adding values to be replicated to Firebase
        setValue("employer", employerId);
        setValue("title", title);
        setValue("employees", employeeIdMap);
    }

    /**
     * Create a new Listing object from a Firebase record
     * @param key
     */
    public Listing(String key){
        getRecord(key);
    }
}