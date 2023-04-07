package com.example.csci3130courseproject;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Any;

@RunWith(AndroidJUnit4.class)
public class SaveJobPageEspressoTest {
    @Before
    public void setup() throws InterruptedException {
        ActivityScenario.launch(MainActivity.class);
        SignInTest.grantPermission();
    }

    private void signIn(String user, String password){
        onView(withId(R.id.Sign_In_Email)).perform(typeText(user));
        onView(withId(R.id.Sign_In_Password)).perform(typeText(password));
        onView(withId(R.id.Sign_In_Request)).perform(click());
        SystemClock.sleep(3000);
        onView(withId(R.id.searchBar)).check(matches(isDisplayed()));
    }
    @Test
    public void saveJob() {
        signIn("test@email.com", "password");
        // TODO: Save a job
    }
}
