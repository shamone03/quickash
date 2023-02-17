package com.example.csci3130courseproject;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Handles the creation of new job listings
 */
public class CreateListingFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_listing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        Button createPosting = (Button) getView().findViewById(R.id.createJP_button);

        createPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String posterID = FirebaseAuth.getInstance().getUid();
                String jobTitle = getJobTitle();
                int jobSalary = getJobSalary();
                int jobDuration = getJobDuration();
                Priority.PRIORITY priorityLevel = Priority.getPriorityFromSpinner(getJobPriorityField());
                HashMap<String, Boolean> employees = new HashMap<>();

                // Create job posting object and send to firebase
                Listing newListing = new Listing(posterID, jobTitle, jobDuration, jobSalary, priorityLevel, employees);
                newListing.setRecord();

                // Navigate back to dashboard fragment:
                Navigation.findNavController(view).navigate(R.id.action_createListingFragment_to_dashboardFragment);
            }
        });
    }

    public EditText getJobTitleField(){
        return (EditText) getView().findViewById(R.id.createJP_PostingTitle);
    }

    public String getJobTitle(){
        return getJobTitleField().getText().toString();
    }

    public EditText getJobSalaryField(){
        return (EditText) getView().findViewById(R.id.createJP_JobSalary);
    }

    public int getJobSalary(){
        return Integer.valueOf(getJobSalaryField().getText().toString());
    }

    public EditText getJobDurationField(){
        return (EditText) getView().findViewById(R.id.createJP_JobDurration);
    }

    public int getJobDuration(){
        return Integer.valueOf(getJobDurationField().getText().toString());
    }

    public Spinner getJobPriorityField() { return (Spinner) getView().findViewById(R.id.createJP_priority); }
}