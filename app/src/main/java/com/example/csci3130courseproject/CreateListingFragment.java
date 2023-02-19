package com.example.csci3130courseproject;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Handles the creation of new job listings
 */
public class CreateListingFragment extends Fragment {
    EditText titleField, salaryField, durationField;
    Spinner priorityField;
    Button createPosting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_listing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        createPosting = getView().findViewById(R.id.createJP_button);
        titleField = getView().findViewById(R.id.createJP_PostingTitle);
        salaryField = getView().findViewById(R.id.createJP_JobSalary);
        durationField = getView().findViewById(R.id.createJP_JobDurration);
        priorityField = getView().findViewById(R.id.createJP_priority);

        createPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Preparing listing values
                String posterID = FirebaseAuth.getInstance().getUid();
                HashMap<String, Boolean> employees = new HashMap<>();

                // Create job posting object and send to firebase
                Listing newListing = new Listing(posterID, getJobTitle(), getJobDuration(),
                        getJobSalary(), getJobPriority(), employees);
                newListing.setRecord();

                // Navigate back to dashboard fragment:

//                Navigation.findNavController(view).navigate(R.id.action_createListingFragment_to_listingSearchFragment);
            }
        });
    }

    /**
     * @return String value representing the title of the job
     */
    public String getJobTitle() {
        return titleField.getText().toString();
    }

    /**
     * @return Integer value representing the salary of the job
     */
    public int getJobSalary() {
        return Integer.valueOf(salaryField.getText().toString());
    }

    /**
     * @return Integer value representing the duration of the job
     */
    public int getJobDuration() {
        return Integer.valueOf(durationField.getText().toString());
    }

    /**
     * @return Priority enumerator matching the spinner value
     */
    public Priority.PRIORITY getJobPriority() {
        return Priority.getPriorityFromSpinner(priorityField);
    }
}