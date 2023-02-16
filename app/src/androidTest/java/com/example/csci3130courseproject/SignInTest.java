package com.example.csci3130courseproject;

import android.content.Context;

//import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignInTest {

//    @Rule
//    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.csci3130courseproject", appContext.getPackageName());
    }

    @Test
    public void signInPageVisible() {
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.Sign_In_Email)).check(matches(withText("")));
        onView(withId(R.id.Sign_In_Password)).check(matches(withText("")));
    }

    @Test
    public void userSignedIn() throws InterruptedException {
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.Sign_In_Email)).perform(typeText("test@email.com"));
        onView(withId(R.id.Sign_In_Password)).perform(typeText("password"));
        onView(withId(R.id.Sign_In_Request)).perform(click());
        Thread.sleep(5000); // temporary fix, should use idlingresource or smthn
        onView(withId(R.id.welcome_label)).check(matches(withText("Hello")));
    }

    @Test
    public void checkSwitchToSignUp() {
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.GoToSignUp)).perform(click());
        onView(withId(R.id.signupEmail)).check(matches(withText("")));
    }

}