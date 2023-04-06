package com.example.csci3130courseproject;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class SaveJobPageEspressoTest extends UserProfileEspressoTest{

    private void signIn() throws InterruptedException{
        onView(withId(R.id.Sign_In_Email)).perform(typeText("save@make.com"));
        onView(withId(R.id.Sign_In_Password)).perform(typeText("password"));
        wait(1200);
        onView(withId(R.id.listingSearchFragment)).check(matches(isDisplayed()));
    }

    @Before
    public void setup(){
        try{
            signIn();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
