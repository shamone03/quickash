package com.example.csci3130courseproject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.fragment.app.Fragment;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert.*;

public class createJPTest {
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
        assertTrue(CreateListingFragment.isJobSalaryValid(0.1));
        assertTrue(CreateListingFragment.isJobSalaryValid(2000));
        assertFalse(CreateListingFragment.isJobSalaryValid(-1));
    }

}
