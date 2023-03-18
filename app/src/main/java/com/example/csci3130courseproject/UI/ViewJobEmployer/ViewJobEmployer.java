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

public class ViewJobEmployer extends Fragment {
    String jobID;
    JobPostingObject currentJob;
    ArrayList<Object[]> jobApplicants = new ArrayList<>();

    Button saveEdit, applicants;
    EditText jobDescription;
    TextView jobTitle, applicantName;

    LinearLayout applicantsContainer;

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
        // TODO: Get JobID from bundle
        // TODO: Use JobID to get JobPostingObject


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

        jobApplicants.add(new Object[]{jobApplicantPreview});

    }

    private void createApplicant(String userID){
        //TODO: Convert userID to UserObject
        //TODO: pass UserObject to createApplicantPreview to populate applicant field.
        return;
    }

    public void populateApplicantListView(@NonNull View view, Query applicants){
        createApplicantPreview(null);
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