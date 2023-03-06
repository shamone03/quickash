package com.example.csci3130courseproject.UI.ViewJobEmployer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.csci3130courseproject.Callbacks.JobsCallback;
import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.Listing;
import com.example.csci3130courseproject.Utils.UserObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewJobEmployer extends Fragment {

    Button saveEdit, applicants;
    EditText jobDescription;
    TextView jobTitle, applicantName;

    // required empty constructor
    public ViewJobEmployer() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_job_employer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    private void createApplicantPreview(){

    }

    public void populateApplicantListView(@NonNull View view){

    }

    public void getJobs(String uid, JobsCallback callback) {

    }

    // returns string value representing applicant's name on profile
    public String getJobApplicantName() {
        return applicantName.getText().toString();
    }

    // returns String value representing job title
    public String getJobTitle() {
        return jobTitle.getText().toString();
    }

    public String getJobDescription() {
        return jobDescription.getText().toString();
    }

}