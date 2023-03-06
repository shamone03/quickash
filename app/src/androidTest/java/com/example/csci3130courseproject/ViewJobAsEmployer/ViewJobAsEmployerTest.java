package com.example.csci3130courseproject.ViewJobAsEmployer;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.csci3130courseproject.MainActivity;
import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.UI.HomePage.ListingSearchFragment;
import com.example.csci3130courseproject.Utils.UserObject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class ViewJobAsEmployerTest {
    static NavController navController;

    @Mock
    UserObject newApplicant;

    @Before
    public void launchMainActivity() throws InterruptedException {
        MockitoAnnotations.openMocks(this);
        ActivityScenario.launch(MainActivity.class);
        onView(ViewMatchers.withId(R.id.Sign_In_Email)).perform(typeText("test@email.com"));
        onView(withId(R.id.Sign_In_Password)).perform(typeText("password"));
        closeSoftKeyboard();
        onView(withId(R.id.Sign_In_Request)).perform(click());
        Thread.sleep(5000); // wait to sign in

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.viewJobEmployer)).perform(click());
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.csci3130courseproject", appContext.getPackageName());
    }
    @Test
    public void checkIfPageVisible() {

    }

    @Test
    public void button_isVisible() {
        onView(withId(R.id.ViewJobEmployerSaveButton)).check(matches(isDisplayed()));
    }

    @Test
    public void jobTitle_isVisible() {
        onView(withId(R.id.ViewJobEmployerJobTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void jobDescription_isVisible() {
        onView(withId(R.id.ViewJobEmployerJobDescription)).check(matches(isDisplayed()));
    }

    @Test
    public void jobApplicants_isVisible() {
        onView(withId(R.id.ViewJobEmployerJobApplicantsContainer)).check(matches(isDisplayed()));
    }

    @Test
    public void applicantPreviewIsVisible() {
        // As an employer I wish to see my job's applicants
        onView(withId(R.id.jobApplicant)).check(matches(isDisplayed()));
    }

}
