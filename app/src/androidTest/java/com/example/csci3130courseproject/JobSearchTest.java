package com.example.csci3130courseproject;

import android.content.Context;
import android.view.KeyEvent;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.core.app.ActivityScenario;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JobSearchTest {

    @Before
    public void launchMainActivity() throws InterruptedException {
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.Sign_In_Email)).perform(typeText("test@email.com"));
        onView(withId(R.id.Sign_In_Password)).perform(typeText("password"));
        onView(withId(R.id.Sign_In_Request)).perform(click());
        Thread.sleep(5000); // wait to sign in
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.csci3130courseproject", appContext.getPackageName());
    }

    @Test
    public void checkIfSearchPageVisible() {
        onView(withId(R.id.searchPage)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfSearching_isValid() {
        onView(withId(R.id.searchBar)).perform(typeText("dog"));
        onView(withId(R.id.searchBar)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.searchPage)).check(matches(isDisplayed()));
    }

    /*
    @Test
    public void checkIfSearched_matchesCard() throws InterruptedException {
        onView(withId(R.id.searchBar)).perform(typeText("dog"));
        onView(withId(R.id.searchBar)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        Thread.sleep(4000);
        //onView(withId(R.id.searchBar)).check(matches(withText(String.valueOf(withId(R.id.titleLabel)))));
        onView(withParent(withId(R.id.listingCardList))).check(matches(isDisplayed()));
    }
    */
}