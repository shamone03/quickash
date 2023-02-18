package com.example.csci3130courseproject;

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

}
