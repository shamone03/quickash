package com.example.csci3130courseproject.CustomExceptions;

public class UnsavedException extends Exception {
    public UnsavedException(){
        super("This job has not been saved");
    }
}
