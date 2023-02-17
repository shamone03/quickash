package com.example.csci3130courseproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseList {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    Class classReference;
    private int pageStart = 0;
    private int pageWidth;

    /**
     *
     * @param classReference The DatabaseObject subclass to use as the DatabaseReference
     * @param pageWidth The maximum amount of records to be retrieved per page
     */
    public DatabaseList(Class classReference, int pageWidth) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(classReference.getSimpleName());
        this.pageWidth = pageWidth;
        this.classReference = classReference;
    }

    public int getPages() {
        return 0;
    }

    public void getPage() {

    }

    public void nextPage() {
        pageStart += pageWidth;
    }

    public void previousPage() {
        pageStart = Math.max(0,pageStart - pageWidth);
    }

    public void goToPage(int pageNumber) {

    }
}
