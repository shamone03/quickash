package com.example.csci3130courseproject;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class validStringTest extends SignUpFragment {
    @Test
    public void invalidEmail() {
        assertEquals(false, super.validString("hello world.com", 0));
    }
    public void validEmail() { assertEquals(true, super.validString("hello@world.com", 0)); }
    public void invalidPassword() { assertEquals(false, super.validString("hello", 1)); }
    public void validPassword() { assertEquals(true, super.validString("hello1234", 1)); }
}
