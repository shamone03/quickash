package com.example.csci3130courseproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.csci3130courseproject.UI.User.IUser;
import com.example.csci3130courseproject.UI.User.Rating.IRate;
import com.example.csci3130courseproject.Utils.UserObject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserRatingTest {

    public UserObject currentUser;

    @Mock
    static IRate rating;
    static IUser user;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.openMocks(IRate.class);
    }

    @Test
    public void testRateUser() throws Exception {
        rating = Mockito.mock(IRate.class);
        Mockito.when(rating.getUserRating(user)).thenReturn(5.0);
        assertTrue(rating.getUserRating(user) == 5.0);
        Mockito.when(rating.updateUserRating(user, 3)).thenReturn(4.0);
        Mockito.verify(rating, Mockito.atLeastOnce()).getUserRating(user);
    }

}
