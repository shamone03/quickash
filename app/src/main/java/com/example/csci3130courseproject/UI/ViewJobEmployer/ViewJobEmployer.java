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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.example.csci3130courseproject.Utils.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewJobEmployer extends UserListListener {

    UsersList userListGetter = new UsersList();
    String jobID;
    JobPostingObject currentJob;
    HashMap<String, Boolean> applicants;

    Button saveEdit;
    EditText jobDescription;
    TextView jobTitle, applicantName;
    LinearLayout applicantsContainer;
    // Contains reference to applicantButton [0] and user [1]
    ArrayList<Object[]> jobApplicants = new ArrayList<>();
    ArrayList<UserObject> userApplied = new ArrayList<>();

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
        userListGetter.addListener(this);
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
                userListGetter.getUsers(applicants.keySet());
                populateApplicantListView();
            }
        });


        // TODO: OrderByRating


    }

    private void createApplicantPreview(UserObject applicant){
        // currentApplicant = applicantSnapshot.getValue(UserObject.class);
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View jobApplicantPreview = inflater.inflate(R.layout.prefab_view_job_applicant_preview, applicantsContainer, false);
        applicantsContainer.addView(jobApplicantPreview);
        Object[] newPreview = {applicant, jobApplicantPreview};
        Log.w("GotUser", "Username: " + applicant.getUsername());
        Log.w("JobApplicantPreview", "Applicants Button: " + jobApplicantPreview.toString());
        jobApplicants.add(newPreview);
    }

    private void loadApplicantInfo(UserObject applicant, View jobApplicantPreview){
        // Job Posting Preview Modifiable attributes:
        ImageView profilePicture = jobApplicantPreview.findViewById(R.id.jobApplicantAvatar);
        TextView applicantName = jobApplicantPreview.findViewById(R.id.jobApplicantName);
        TextView applicantRating = jobApplicantPreview.findViewById(R.id.jopApplicantRating);
        Button applicantButton = jobApplicantPreview.findViewById(R.id.jobApplicantButton);


        profilePicture.setImageURI(Uri.parse(""));
        applicantName.setText(applicant.getUsername());
        applicantRating.setText(String.format("Employee Rating: %.2f", applicant.getEmployeeRating()));


        applicantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Move to user profile & acceptance button
            }
        });

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

    private void clearApplicantsListView(){
        applicantsContainer.removeAllViews();
        jobApplicants.clear();
    }

    public void populateApplicantListView(){
        if (applicants == null){
            Log.w("Applicants", "Failed to initialize (no applicants or error occurred)");
            return;
        }
        clearApplicantsListView();
        for (UserObject user : userApplied){
            createApplicantPreview(user);
        }
        for (Object[] e : jobApplicants){
            loadApplicantInfo((UserObject) e[0], (View) e[1]);
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

    @Override
    public void updateList(ArrayList<UserObject> updatedList) {
        userApplied = updatedList;
        populateApplicantListView();
    }
}