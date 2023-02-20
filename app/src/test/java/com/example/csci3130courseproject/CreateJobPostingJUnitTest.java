package com.example.csci3130courseproject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.fragment.app.Fragment;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateJobPostingJUnitTest {
    static Fragment jobPostingFragment;

    @BeforeClass
    public static void setUp(){ jobPostingFragment = new CreateListingFragment(); }

    @AfterClass
    public static void garbageCollect(){ System.gc(); }

    @Test
    public void checkIfJobTitleIsEmpty() {
        assertTrue(CreateListingFragment.isEmptyJobTitle(""));
        assertFalse(CreateListingFragment.isEmptyJobTitle("abcd"));
        assertFalse(CreateListingFragment.isEmptyJobTitle("123456"));
        assertFalse(CreateListingFragment.isEmptyJobTitle("ab2^&3cd"));
    }

    @Test
    public void checkIfJobSalaryIsEmpty(){
        assertTrue(CreateListingFragment.isEmptyJobSalary(""));
        assertFalse(CreateListingFragment.isEmptyJobSalary("50000"));
        assertFalse(CreateListingFragment.isEmptyJobSalary("5.0"));
        assertFalse(CreateListingFragment.isEmptyJobSalary("78.998"));
    }

    @Test
    public void checkIfJobSalaryIsValid(){
        assertFalse(CreateListingFragment.isJobSalaryValid(-1));
        assertFalse(CreateListingFragment.isJobSalaryValid(-100));
        assertTrue(CreateListingFragment.isJobSalaryValid(2000));
        assertTrue(CreateListingFragment.isJobSalaryValid(3000));
    }

    @Test
    public void checkIfJobDescriptionIsEmpty(){
        assertTrue(CreateListingFragment.isEmptyJobDuration(""));
        assertFalse(CreateListingFragment.isEmptyJobDuration("This job is about xyz abc"));
        assertFalse(CreateListingFragment.isEmptyJobDuration("Rate of pay is $24 hourly "));
    }

    @Test
    public void checkIfJobDescriptionIsValid(){
        assertFalse(CreateListingFragment.isJobDurationValid(-1));
        assertFalse(CreateListingFragment.isJobDurationValid(100));
        assertTrue(CreateListingFragment.isJobDurationValid(20));
        assertTrue(CreateListingFragment.isJobDurationValid(3));
    }

}
