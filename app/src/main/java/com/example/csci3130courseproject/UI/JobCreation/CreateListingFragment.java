package com.example.csci3130courseproject.UI.JobCreation;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.csci3130courseproject.Utils.JobLocation;
import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.example.csci3130courseproject.Utils.ObtainingLocation;
import com.example.csci3130courseproject.Utils.Permissions;
import com.example.csci3130courseproject.Utils.Priority;
import com.example.csci3130courseproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the creation of new job listings
 */
public class CreateListingFragment extends Fragment {
    EditText titleField, descriptionField, salaryField, durationField;
    Spinner priorityField;
    Button createPosting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_listing, container, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.w("PERMISSION", "granted");
            allowCreation();
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        createPosting = getView().findViewById(R.id.createJP_button);
        titleField = getView().findViewById(R.id.createJP_PostingTitle);
        descriptionField = getView().findViewById(R.id.createJP_JobDescription);
        salaryField = getView().findViewById(R.id.createJP_JobSalary);
        durationField = getView().findViewById(R.id.createJP_JobDurration);
        priorityField = getView().findViewById(R.id.createJP_priority);
        if (Permissions.checkFineLocationPermission(getActivity())) {
            Log.w("PERMISSION", "already there");
            allowCreation();
        } else {
            Log.w("PERMISSION", "no");
            Permissions.requestPermission(getActivity());
        }

    }

    private void allowCreation() {
        createPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Preparing listing values


                String posterID = FirebaseAuth.getInstance().getUid();

                // Create job posting object and submit to firebase

                JobPostingObject jobPostingObject = new JobPostingObject();
                jobPostingObject.setJobTitle(getJobTitle());
                jobPostingObject.setJobDescription(getJobDescription());
                jobPostingObject.setJobDuration(getJobDuration());
                jobPostingObject.setJobPoster(posterID);
                jobPostingObject.setJobSalary(getJobSalary(salaryField.getText().toString()));
                jobPostingObject.setEmployees(new HashMap<>());
                jobPostingObject.setPriority(getJobPriority());
                jobPostingObject.setJobComplete(false);
                jobPostingObject.setJobEmployeeID("");
                jobPostingObject.setEmployeeSelected(false);

                Location loc = (new ObtainingLocation(getContext())).getLocation(getContext());
                if (loc == null) {
                    Toast.makeText(getContext(), "Error getting location, check if location is on", Toast.LENGTH_LONG).show();
                    return;
                }
                jobPostingObject.setJobLocation(new JobLocation(loc));

                DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").push();
                jobRef.setValue(jobPostingObject);

                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                Map<String, Object> values = new HashMap<>();
                values.put(jobRef.getKey(), false);
                usersRef.child(posterID).child("jobPostings").updateChildren(values);

                // Navigate back to dashboard fragment:
                // TODO: Confirm job posted
                // TODO: Notify user, then switch page.
                //Navigation.findNavController(view).navigate(R.id.action_createListingFragment_to_listingSearchFragment);
                // Error note: Once Navigation is invoked, can no longer revisit page for some reason.
            }
        });
    }

    /**
     * @return String value representing the title of the job
     */
    public String getJobTitle() {
        return titleField.getText().toString();
    }

    public String getJobDescription(){ return descriptionField.getText().toString();}

    /**
     * isEmptyJobTitle() checks if Job Title field is empty
     * @return true if Job Title field is empty
     */
    public static boolean isEmptyJobTitle(String jobTitle) {
        return jobTitle.isEmpty();
    }

    /**
     * @return Integer value representing the salary of the job
     */
    public double getJobSalary(String jobSalary) {
        return Double.parseDouble(jobSalary);
    }

    /**
     * isEmptyJobSalary() checks if the Job Salary field is empty
     * @return true if field is empty
     */
    public static boolean isEmptyJobSalary(String jobSalary) {
        return jobSalary.isEmpty();
    }

    /**
     * isJobSalaryValid() checks if the Job Salary field is valid
     * @return true if Job Salary > 0
     */
    public static boolean isJobSalaryValid(int jobSalary) {
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
    public static boolean isEmptyJobDuration(String hours){
        return hours.isEmpty();
    }

    /**
     * isJobDurationValid() checks if the Job duration field is valid
     * @return true if hours > 0 and hours < 24
     */
    public static boolean isJobDurationValid(int hours){
        return ((hours>0) && (hours<24));
    }

    /**
     * @return Priority enumerator matching the spinner value
     */
    public Priority.PRIORITY getJobPriority() {
        return Priority.getPriorityFromSpinner(priorityField);
    }
}