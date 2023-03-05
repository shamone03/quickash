package com.example.csci3130courseproject.UI.JobCreation;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.example.csci3130courseproject.Utils.Listing;
import com.example.csci3130courseproject.Utils.Priority;
import com.example.csci3130courseproject.R;
import com.google.firebase.auth.FirebaseAuth;
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
//                Listing newListing = new Listing(posterID, getJobTitle(), getJobDuration(),
//                        getJobSalary(), getJobPriority(), employees);
//                newListing.setRecord();

                JobPostingObject jobPostingObject = new JobPostingObject(posterID, new HashMap<String, Boolean>(), getJobTitle(), getJobPriority(),
                        getJobSalary(), getJobDuration(), new Location(""));

                FirebaseDatabase.getInstance().getReference("JobPostingObject").push().setValue(jobPostingObject);



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
     * isEmptyJobTitle() checks if Job Title field is empty
     * @return true if Job Title field is empty
     */
    protected static boolean isEmptyJobTitle(String jobTitle) {
        return jobTitle.isEmpty();
    }

    /**
     * @return Integer value representing the salary of the job
     */
    public int getJobSalary() {
        return Integer.valueOf(salaryField.getText().toString());
    }

    /**
     * isEmptyJobSalary() checks if the Job Salary field is empty
     * @return true if field is empty
     */
    protected static boolean isEmptyJobSalary(String jobSalary) {
        return jobSalary.isEmpty();
    }

    /**
     * isJobSalaryValid() checks if the Job Salary field is valid
     * @return true if Job Salary > 0
     */
    protected static boolean isJobSalaryValid(int jobSalary) {
        return jobSalary>0;
    }

    /**
     * @return Integer value representing the duration of the job
     */
    public int getJobDuration() {
        return Integer.valueOf(durationField.getText().toString());
    }

    /**
     * isEmptyJobDuration() checks if the Job Description field is empty
     * @return true if field is empty
     */
    protected static boolean isEmptyJobDuration(String hours){
        return hours.isEmpty();
    }

    /**
     * isJobDurationValid() checks if the Job duration field is valid
     * @return true if hours > 0 and hours < 24
     */
    protected static boolean isJobDurationValid(int hours){
        return ((hours>0) && (hours<24));
    }

    /**
     * @return Priority enumerator matching the spinner value
     */
    public Priority.PRIORITY getJobPriority() {
        return Priority.getPriorityFromSpinner(priorityField);
    }
}