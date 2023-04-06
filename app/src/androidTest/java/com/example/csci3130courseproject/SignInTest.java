package com.example.csci3130courseproject;

import android.content.Context;
import android.os.Build;

//import androidx.fragment.app.testing.FragmentScenario;
import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.test.internal.platform.content.PermissionGranter;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
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
    @Before
    public void grantPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // grant the location permissions so we won't have to deal with the dialog when signed in
            getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + getTargetContext().getPackageName() + " android.permission.ACCESS_FINE_LOCATION");
        }
    }

    @Before
    public void launchMainActivity() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("com.example.csci3130courseproject", appContext.getPackageName());
    }

    @Test
    public void signInPageVisible() {
        onView(withId(R.id.Sign_In_Email)).check(matches(withText("")));
        onView(withId(R.id.Sign_In_Password)).check(matches(withText("")));
    }

    @Test
    public void userSignedIn() throws InterruptedException {
        onView(withId(R.id.Sign_In_Email)).perform(typeText("test@email.com"));
        onView(withId(R.id.Sign_In_Password)).perform(typeText("password"));
        closeSoftKeyboard();
        onView(withId(R.id.Sign_In_Request)).perform(click());
        Thread.sleep(5000); // temporary fix, should use idlingresource or smthn
        onView(withId(R.id.searchBar)).check(matches(isDisplayed()));
    }

    @Test
    public void checkSwitchToSignUp() {
        onView(withId(R.id.GoToSignUp)).perform(click());
        onView(withId(R.id.signupEmail)).check(matches(withText("")));
    }

}