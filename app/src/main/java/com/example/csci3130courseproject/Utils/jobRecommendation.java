package com.example.csci3130courseproject.Utils;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class jobRecommendation {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("users");
    private DatabaseReference jobRef = database.getReference("jobs");
    private DatabaseReference takenJobRef = database.getReference("jobsTaken");
    private DatabaseReference takenJobTitleRef = database.getReference("jobTitle");
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private String userID;
    private String jobTitle;
    private String[] jobKeyword;

    // go through jobsSaved and add keyword to array
    public String[] populateJobKeyword() {

        String jobTitle_jobRef;
        // get userID
        userID = currentUser.getUid();
        jobTitle = userRef.child(userID).child(String.valueOf(takenJobRef)).getKey();
        jobTitle_jobRef = jobRef.child(userID).child(String.valueOf(takenJobTitleRef)).getKey();



    }
}
