package com.example.csci3130courseproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.DrawerActions;

import org.junit.Before;
import org.junit.Test;

public class UserProfileEspressoTest {
    @Before
    public void gotoUserProfile() throws InterruptedException {
        ActivityScenario.launch(MainActivity.class);
        SignInTest sign = new SignInTest();
        sign.userSignedIn();

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.userProfileFragment)).perform(click());
    }

//    @Test
//    public void profileLoads() {
//        onView(withId(R.id.nav_view)).perform(click());
//        onView(withId(R.id.userProfileFragment)).perform(click());
//        onView(withId(R.id.email)).check(matches(withText("test@email.com")));
//
//        //Return to home page to avoid chain gang testing
//        onView(withId(R.id.listingSearchFragment)).perform(click());
//    }
//
//    @Test
//    public void editProfileLoads() {
//        onView(withId(R.id.drawer_layout)).perform(click());
//        onView(withId(R.id.userProfileFragment)).perform(click());
//        onView(withId(R.id.editProfile)).perform(click());
//        onView(withId(R.id.User_Email)).check(matches(withText("test@email.com")));
//
//        //Return to home page to avoid chain gang testing
//        onView(withId(R.id.submitChangesButton)).perform(click());
//        onView(withId(R.id.listingSearchFragment)).perform(click());
//    }

//    @Test
//    public void editProfile() {
//        //TODO: edit profile and check to see the profile page shows the change.
//    }

//    @Test
//    public void rateUser(){
//        onView(withId(R.id.drawer_layout)).perform(click());
//        onView(withId(R.id.userProfileFragment)).perform(click());
//
//        TODO: implement a test to add a rating to a user and make sure the average rating value updates.
//    }
}
