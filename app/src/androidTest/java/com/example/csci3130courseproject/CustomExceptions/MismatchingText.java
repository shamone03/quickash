package com.example.csci3130courseproject.CustomExceptions;

public class MismatchingText extends AssertionError{
    public MismatchingText(){super("The texted to be matched did not match the text presented");}
}
