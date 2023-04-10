package com.example.csci3130courseproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

import android.os.SystemClock;
import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.csci3130courseproject.CustomExceptions.AlreadySavedException;
import com.example.csci3130courseproject.CustomExceptions.MismatchingText;
import com.example.csci3130courseproject.CustomExceptions.UnsavedException;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SaveJobPageEspressoTest {
    @Before
    public void setup() throws InterruptedException {
        ActivityScenario.launch(MainActivity.class);
        SignInTest.grantPermission();
        signIn("test@email.com","password");
    }

    private void signIn(String user, String password){
        onView(withId(R.id.Sign_In_Email)).perform(typeText(user));
        onView(withId(R.id.Sign_In_Password)).perform(typeText(password));
        onView(withId(R.id.Sign_In_Request)).perform(click());
        SystemClock.sleep(3000);
        onView(withId(R.id.searchBar)).check(matches(isDisplayed()));
    }

    private void navigateToSavedJobsOnProfile(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.userProfileFragment)).perform(click());
        SystemClock.sleep(3000);
        onView(withId(R.id.profileUsername)).check(matches(isDisplayed()));
        onView(withId(R.id.showJobsSaved)).perform(click());
        SystemClock.sleep(3000);
    }
    private void saveJob() throws AlreadySavedException {
        ViewInteraction job = onView(allOf(withId(R.id.saveButton), hasSibling(withText("Title: Save me! Part 2"))));
        try {
            job.check(matches(withText("SAVE")));
        } catch (AssertionError e){
            throw new AlreadySavedException();
        }
        job.check(matches(isClickable()));
        job.perform(click());
        job.check(matches(withText("UNSAVE")));
    }

    private void unsaveJobFromListing() throws UnsavedException {
        ViewInteraction job = onView(allOf(withId(R.id.saveButton), hasSibling(withText("Title: Save me! Part 2"))));
        try {
            job.perform(scrollTo());
            job.check(matches(withText("UNSAVE")));
        } catch (AssertionError e){
            throw new UnsavedException();
        }
        job.check(matches(isClickable()));
        job.perform(click());
        job.check(matches(withText("SAVE")));
    }

    private void unsaveJobFromProfile() throws MismatchingText {
        ViewInteraction job = onView(allOf(withId(R.id.saveButton), hasSibling(withText("Title: Save me! Part 2"))));
        try {
            job.perform(scrollTo());
            job.check(matches(withText("UNSAVE")));
        } catch (AssertionError e) {
            throw new MismatchingText();
        }
        job.check(matches(isClickable()));
        job.perform(click());
        job.check(matches(withText("SAVE")));
    }

    @Test
    public void saveJobIsVisibleInProfile() {
        try {
            saveJob();
        } catch (AlreadySavedException e){
            // Ignore and continue.
        }
        navigateToSavedJobsOnProfile();
        onView(allOf(withText("Title: Save me! Part 2"))).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void unsaveJobFromListingView() throws UnsavedException {
        try {
            saveJob();
            unsaveJobFromListing();
        } catch (AlreadySavedException e) {
            // Ignore and continue:
            unsaveJobFromListing();
        } catch (UnsavedException e) {
            Log.w("Exception", "Unexpected error occurred!");
            throw new RuntimeException(e);
        }

    }

    @Test
    public void unsaveJobFromProfileView() throws MismatchingText {
        try {
            saveJob();
            navigateToSavedJobsOnProfile();
        } catch (AlreadySavedException e) {
            navigateToSavedJobsOnProfile();
        }
        unsaveJobFromProfile();
    }

}