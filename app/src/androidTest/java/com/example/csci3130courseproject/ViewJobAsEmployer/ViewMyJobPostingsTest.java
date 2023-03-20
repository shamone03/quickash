package com.example.csci3130courseproject.ViewJobAsEmployer;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.DrawerActions;

import com.example.csci3130courseproject.MainActivity;
import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.SignInTest;
import com.example.csci3130courseproject.UserProfileEspressoTest;

import org.junit.Before;
import org.junit.Test;

public class ViewMyJobPostingsTest extends UserProfileEspressoTest {

    public void pressShowMyJobsButton(){
        onView(withId(R.id.showJobsCreated)).perform(click());
    }

    // Show My Job Postings Button is visible
    @Test
    public void viewMyJobPostingsButtonVisible(){
        onView(withId(R.id.showJobsCreated)).check(matches(isDisplayed()));
    }

    // Multiple Job Postings are visible
    @Test
    public void viewMyJobPostingsVisible(){
        pressShowMyJobsButton();
        SystemClock.sleep(1500);

        onView(allOf(withId(R.id.titleLabel), isDisplayed()));
        onView(allOf(withId(R.id.hoursLabel), isDisplayed()));
        onView(allOf(withId(R.id.employerLabel), isDisplayed()));
        onView(allOf(withId(R.id.salaryLabel), isDisplayed()));
    }

    // Making Sure all Jobs are meaningful and not null
    @Test
    public void noNullJobPostings(){
        pressShowMyJobsButton();

        onView(allOf(withId(R.id.titleLabel), not(withText("Title: NULL"))));
        onView(allOf(withId(R.id.hoursLabel), not(withText("Hours: NULL"))));
        onView(allOf(withId(R.id.salaryLabel), not(withText("Salary: NULL"))));
        onView(allOf(withId(R.id.employerLabel), not(withText("Employer ID: NULL"))));

    }
}
