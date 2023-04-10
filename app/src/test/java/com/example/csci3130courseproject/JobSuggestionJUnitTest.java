package com.example.csci3130courseproject;

import org.junit.Test;

import static org.junit.Assert.*;
import com.example.csci3130courseproject.UI.User.IUser;
import com.example.csci3130courseproject.UI.User.Rating.IRate;
import com.example.csci3130courseproject.Utils.UserObject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class JobSuggestionJUnitTest {
    public UserObject employeeUser;
    public UserObject employerUser;

    @Mock
    public static IUser user;

    @Test
    public void testSuggestRelevantJob(){
        //TODO: Mock a job being created.
        //TODO: Mock User with preferred jobs similar to the one being posted.
        //TODO: On job creation send notification to employee
        //TODO: Reset Users to avoid chain gang anti-pattern testing
    }

    @Test
    public void testDoNotSuggestIrrelevantJob(){
        //TODO: Mock a job being created.
        //TODO: Mock User with preferred jobs different to the one being posted.
        //TODO: On job creation do not send notification to employee.
        //TODO: Reset Users to avoid chain gang anti-pattern testing.
    }

}
