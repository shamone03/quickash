package com.example.csci3130courseproject;

import static org.junit.Assert.*;
import com.google.firebase.auth.FirebaseAuth;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.Test;
import org.mockito.Mock;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@ExtendWith(MockitoExtension.class)
public class SignInJUnitTest {
    @Mock
    FirebaseAuth firebaseAuth;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void checkIfSignedIn() {
        assertNotNull(firebaseAuth);
    }

}