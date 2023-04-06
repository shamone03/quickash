package com.example.csci3130courseproject;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import org.junit.Before;
import org.junit.Test;


public class LocationRequestEspressoTest {

    private boolean signIn(String user, String password) throws InterruptedException{
        onView(withId(R.id.Sign_In_Email)).perform(typeText(user));
        onView(withId(R.id.Sign_In_Password)).perform(typeText(password));
        wait(1500);
        onView(withId(R.id.listingSearchFragment)).check(matches(isDisplayed()));
        return true;
    }

    @Before
    public void Login(){
        boolean loginSuccess = false;
        try {
            loginSuccess = signIn("test@email.com", "password");
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

}
