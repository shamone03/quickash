package com.example.csci3130courseproject;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.csci3130courseproject.UI.HomePage.ListingSearchFragment;
import com.example.csci3130courseproject.UI.User.ProfileFragment;
import com.example.csci3130courseproject.Utils.JobLocation;
import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.example.csci3130courseproject.Utils.Priority;

import java.util.HashMap;


public class UserProfileJUnitTest {
    private String userId = "UserId123";
    private HashMap<String, Boolean> acceptedMap = new HashMap<>();
    private HashMap<String, Boolean> rejectedMap = new HashMap<>();

    private JobPostingObject completeAcceptedJob;
    private JobPostingObject incompleteAcceptedJob;
    private JobPostingObject completeRejectedJob;


    @Before
    public void setup() throws InterruptedException {
        acceptedMap.put(userId, true);
        rejectedMap.put(userId, false);

        completeAcceptedJob = new JobPostingObject("PosterID", acceptedMap, "",
                Priority.PRIORITY.HIGH, 10, 5, new JobLocation(), true);
        incompleteAcceptedJob = new JobPostingObject("PosterID",acceptedMap, "",
                Priority.PRIORITY.HIGH, 10, 5, new JobLocation(), false);
        completeRejectedJob = new JobPostingObject("PosterID", rejectedMap, "",
                Priority.PRIORITY.HIGH, 10, 5, new JobLocation(), true);
    }

    //Test test
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkPaidParticipant() {
        assertTrue(ProfileFragment.checkPaid(completeJob, userId));
    }

    @Test
    public void checkUnpaidParticipant() {
        assertFalse(ProfileFragment.checkPaid(incompleteJob, userId));
    }

    @Test
    public void checkPaidNonparticipant() {
        assertFalse(ProfileFragment.checkPaid(incompleteJob, userId));
    }

    public void CatchNullObjects() {
        assertFalse(ProfileFragment.checkPaid(null, userId));
    }
}
