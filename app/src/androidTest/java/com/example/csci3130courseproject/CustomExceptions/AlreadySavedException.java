package com.example.csci3130courseproject.CustomExceptions;

public class AlreadySavedException extends Exception{
    public AlreadySavedException(){
        super("This job has already been saved");
    }
}

