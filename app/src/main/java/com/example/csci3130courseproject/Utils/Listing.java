package com.example.csci3130courseproject.Utils;

import com.example.csci3130courseproject.Utils.Priority;
import com.example.csci3130courseproject.Utils.DatabaseObject;
import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class Listing extends DatabaseObject {
    /**
     * Create a new Listing object from app data
     */
    public Listing(String employerId, String title, int hours, int salary, Priority.PRIORITY priority, HashMap<String, Boolean> employeeIdMap) {
        // Adding values to be replicated to Firebase
        setValue("employer", employerId);
        setValue("title", title);
        setValue("hours", hours);
        setValue("salary", salary);
        setValue("priority", priority.toString());
        setValue("employees", employeeIdMap);
    }

    public Listing(JobPostingObject jobPosting){
        setValue("employer", jobPosting.getJobPoster());
        setValue("title", jobPosting.getJobTitle());
        setValue("hours", jobPosting.getJobDuration());
        setValue("salary", jobPosting.getJobSalary());
        setValue("priority", jobPosting.getPriority().toString());
        setValue("employees", jobPosting.getEmployees());
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


    public void addEmployee(String userId) {
        HashMap<String, Boolean> employees;

        if (getValue("employees") == null) {
            employees = new HashMap<>();
            employees.put(userId,false);
            setValue("employees",employees);
        } else {
            employees = (HashMap<String, Boolean>) getValue("employees");
            employees.put(userId,false);
        }

        updateData();
    }
}
