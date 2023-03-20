package com.example.csci3130courseproject.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

/**
 *
 */
public class UserObject {
    private String username;
    private String phoneNumber;
    private String dateOfBirth;
    private HashMap<String, Boolean> preferredJobsField;
    private String creditCardNumber;
    private String creditCardCVV;
    private String country;
    private String province;
    private String city;
    private String address;


    /** Contains the jobId of all jobs created by the user.*/
    private HashMap<String, Boolean> jobPostings;

    /** Contains the jobId of all jobs taken by the user.*/
    private HashMap<String, Boolean> jobsTaken;

    private double employerRating;

    private double employeeRating;

    /** adding user rating to userObject, as it won't seem to let me add it right on firebase **/
    private double employerRatingScore;
    private double employeeRatingScore;
    private int numberOfEmployeeRatings;
    private int numberOfEmployerRatings;

    public UserObject() { }

    public UserObject(String username) {
        this.username = username;
    }

    public UserObject(String username, HashMap<String, Boolean> jobPostings, HashMap<String, Boolean> jobsTaken){
        this.username = username;
        this.jobsTaken = jobsTaken;
        this.jobPostings = jobPostings;
        //Start users with 0 rating
        this.numberOfEmployerRatings = 0;
        this.numberOfEmployeeRatings = 0;
        this.employeeRating = 0;
        this.employerRating = 0;
    }

    public void updateUser(String username, String phoneNumber, String dateOfBirth,
                           String creditCardNumber, String creditCardCVV,
                           String country, String province, String city, String address) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.creditCardNumber = creditCardNumber;
        this.creditCardCVV = creditCardCVV;
        this.country = country;
        this.province = province;
        this.city = city;
        this.address = address;
    }

    /**
     * Adds the jobId of a posting that the user has taken to their jobsTaken list
     * @param jobId
     */
    public void addJobsTaken(String jobkey, Boolean jobId) {
        jobsTaken.put(jobkey, jobId);
    }

    @Override
    public String toString() {
        return "UserObject{" +
                "jobPostings=" + jobPostings +
                ", jobsTaken=" + jobsTaken +
                '}';
    }

    /**
     * @return The list containing all job postings created by the user
     */
    public HashMap<String, Boolean> getJobPostings() {
        return jobPostings;
    }

    /**
     * @return The list containing all job postings taken by the user
     */
    public HashMap<String, Boolean> getJobsTaken() {
        return jobsTaken;
    }


    public double getEmployeeRating() {
        return employeeRating;
    }

    public double getEmployerRating() {
        return employerRating;
    }

    public String getUsername() {
        return username;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public HashMap<String, Boolean> getPreferredJobsField() {
        return preferredJobsField;
    }

    public String getCreditCardCVV() {
        return creditCardCVV;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void rateUser(Double dob) {
        //TODO: Add conditional to choose employer or employee
        rateEmployee(dob);
    }

    public void rateEmployer(Double rating) {
        numberOfEmployerRatings += 1;
        employerRatingScore += rating;
        employeeRating = employerRatingScore/numberOfEmployerRatings;
    }

    public void rateEmployee(Double rating) {
        numberOfEmployeeRatings += 1;
        employeeRatingScore += rating;
        employerRating = employeeRatingScore/numberOfEmployeeRatings;
    }
}

