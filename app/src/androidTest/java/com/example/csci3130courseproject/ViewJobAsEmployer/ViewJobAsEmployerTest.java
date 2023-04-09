package com.example.csci3130courseproject.ViewJobAsEmployer;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.os.SystemClock;

import androidx.navigation.NavController;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.UserObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(AndroidJUnit4.class)
public class ViewJobAsEmployerTest extends ViewMyJobPostingsTest {
    static NavController navController;

    @Mock
    UserObject newApplicant;

    @Before
    public void setup() throws InterruptedException {
        super.gotoUserProfile();
        super.pressShowMyJobsButton();
        // TODO: Click View
    }

    public void gotoViewJobAsEmployer(){
        onView(allOf(withId(R.id.applyButton), hasSibling(withText("Title: Walk dog")))).perform(click());
        SystemClock.sleep(1500);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.csci3130courseproject", appContext.getPackageName());
    }
    /*
    @Test
    public void saveButton_isVisible() {
        gotoViewJobAsEmployer();
        onView(withId(R.id.ViewJobEmployerSaveButton)).check(matches(isDisplayed()));
    }

    @Test
    public void jobTitle_isVisible() {
        gotoViewJobAsEmployer();
        onView(withId(R.id.ViewJobEmployerJobTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void jobDescription_isVisible() {
        gotoViewJobAsEmployer();
        onView(withId(R.id.ViewJobEmployerJobDescription)).check(matches(isDisplayed()));
    }

    @Test
    public void jobApplicants_isVisible() {
        gotoViewJobAsEmployer();
        onView(withId(R.id.ViewJobEmployerJobApplicantsContainer)).check(matches(isDisplayed()));
    }

    @Test
    public void applicantPreviewIsVisible() {
        // As an employer I wish to see my job's applicants
        gotoViewJobAsEmployer();
        onView(anyOf(withId(R.id.jobApplicant), isDisplayed()));
    }
     */
}
