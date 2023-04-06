package com.example.csci3130courseproject;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class SaveJobPageEspressoTest {
    @Before
    public void navigateToSavePage() throws InterruptedException {
        ActivityScenario.launch(MainActivity.class);
        UserProfileEspressoTest.gotoUserProfile();
//        onView(withId(R.id.viewSavedJobs)).perform(click());
    }

    @Test
    public void userProfilePageVisible() {
//        onView(withId(R.id.savedJob)).check(matches(isDisplayed()));
    }
}
