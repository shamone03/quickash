package com.example.csci3130courseproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;

import org.junit.Before;
import org.junit.Test;

public class UserProfileEspressoTest {
    @Before
    public void launchMainActivity() throws InterruptedException {
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.Sign_In_Email)).perform(typeText("test@email.com"));
        onView(withId(R.id.Sign_In_Password)).perform(typeText("password"));
        onView(withId(R.id.Sign_In_Request)).perform(click());
        Thread.sleep(5000); // wait to sign in
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
}
