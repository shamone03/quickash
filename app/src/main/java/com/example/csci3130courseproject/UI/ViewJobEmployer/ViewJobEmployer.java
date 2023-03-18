package com.example.csci3130courseproject.UI.ViewJobEmployer;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.example.csci3130courseproject.Utils.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewJobEmployer extends Fragment {
    String jobID;
    JobPostingObject currentJob;
    HashMap<String, Boolean> applicants;

    Button saveEdit;
    EditText jobDescription;
    TextView jobTitle, applicantName;
    LinearLayout applicantsContainer;
    // Contains reference to applicantButton [0] and user [1]
    ArrayList<Object[]> jobApplicants = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference userReference;

    // required empty constructor
    public ViewJobEmployer() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.jobID = getArguments().getString("JobID");
        return inflater.inflate(R.layout.fragment_view_job_employer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = FirebaseDatabase.getInstance();

        applicantsContainer = (LinearLayout) getView().findViewById(R.id.ViewJobEmployerJobApplicantsContainer);
        jobTitle = (TextView) getView().findViewById(R.id.ViewJobEmployerJobTitle);
        jobDescription = (EditText) getView().findViewById(R.id.ViewJobEmployerJobDescription);
        saveEdit = (Button) getView().findViewById(R.id.ViewJobEmployerSaveButton);
        saveEdit.setEnabled(false);
        getJob(new IJobCallback() {
            @Override
            public void onGetJobSuccess(JobPostingObject job) {
                currentJob = job;
                applicants = job.getEmployees();
                // Setup View Job As Employer Page:
                jobTitle.setText(job.getJobTitle());
                jobDescription.setText(job.getJobDescription());
                saveEdit.setEnabled(true);

                populateApplicantListView();
            }
        });


        // TODO: OrderByRating


    }

    private void createApplicantPreview(UserObject applicant){
        // currentApplicant = applicantSnapshot.getValue(UserObject.class);
        View jobApplicantPreview = getLayoutInflater().inflate(R.layout.prefab_view_job_applicant_preview, applicantsContainer, true);

        // Job Posting Preview Modifiable attributes:
        ImageView profilePicture = jobApplicantPreview.findViewById(R.id.jobApplicantAvatar);
        TextView applicantName = jobApplicantPreview.findViewById(R.id.jobApplicantName);
        TextView applicantRating = jobApplicantPreview.findViewById(R.id.jopApplicantRating);
        Button applicantButton = jobApplicantPreview.findViewById(R.id.jobApplicantButton);

        // Load information:
        if (applicant == null) {
            profilePicture.setImageURI(Uri.parse(""));
            applicantName.setText("John Doe");
            applicantRating.setText(String.format("Employee Rating: %.2f", 2.5));
        }else{
            profilePicture.setImageURI(Uri.parse(""));
            applicantName.setText(applicant.getUsername());
            applicantRating.setText(String.format("Employee Rating: %.2f", applicant.getEmployeeRating()));
        }

        applicantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to user profile & acceptance button
            }
        });

        jobApplicants.add(new Object[]{applicant, jobApplicantPreview});

    }


    private void getJob(IJobCallback callback){
        database.getReference("jobs").child(jobID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    JobPostingObject job = task.getResult().getValue(JobPostingObject.class);
                    Log.w("GotJob", job.getJobTitle());
                    callback.onGetJobSuccess(job);
                }
            }
        });
    }


    private void getUser(IUserCallback callback, String userID){
        database.getReference("users").child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    Log.w("FetchUser", userID);
                    UserObject user = task.getResult().getValue(UserObject.class);
                    if(user != null) {
                        Log.w("GotUser", "Username: " + String.valueOf(user.getEmployerRating()));
                        callback.onGetUserSuccess(user);
                    }else {
                        Log.w("UserError",  "User is null");
                    }
                }
            }
        });
    }

    public void populateApplicantListView(){
        if (applicants == null){
            Log.w("Applicants", "Failed to initialize (no applicants or error occurred)");
            return;
        }
        for (String userid : applicants.keySet()){
            getUser(new IUserCallback() {
                @Override
                public void onGetUserSuccess(UserObject user) {
                    createApplicantPreview(user);
                }
            }, userid);
        }
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