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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.csci3130courseproject.Callbacks.JobsCallback;
import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.example.csci3130courseproject.Utils.Listing;
import com.example.csci3130courseproject.Utils.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    private void createApplicantPreview(DataSnapshot applicantSnapshot){
        UserObject currentApplicant = applicantSnapshot.getValue(UserObject.class);
        ViewGroup applicantsContainer = (ViewGroup) getView().findViewById(R.id.ViewJobEmployerJobApplicantsContainer);
        View jobApplicantPreview = getLayoutInflater().inflate(R.layout.prefab_view_job_applicant_preview, applicantsContainer, true);

        // Job Posting Preview Modifiable attributes:
        ImageView profilePicture = jobApplicantPreview.findViewById(R.id.jobApplicantAvatar);
        TextView applicantName = jobApplicantPreview.findViewById(R.id.jobApplicantName);
        TextView applicantRating = jobApplicantPreview.findViewById(R.id.jopApplicantRating);
        Button applicantButton = jobApplicantPreview.findViewById(R.id.jobApplicantButton);

        // Load information:
        profilePicture.setImageURI(Uri.parse(""));
        applicantName.setText(currentApplicant.getUsername());
        applicantRating.setText(String.format("Employee Rating: %.2f",currentApplicant.getEmployeeRating()));

        applicantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to user profile & acceptance button
            }
        });

    }

    public void populateApplicantListView(@NonNull View view){

    }

    public void getJobs(String uid, JobsCallback callback) {
        FirebaseDatabase.getInstance().getReference("users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                UserObject user = task.getResult().getValue(UserObject.class);

                assert user != null;
                Toast.makeText(getContext(), user.toString(), Toast.LENGTH_SHORT).show();
//                callback.onJobDetails(user.getJobsTaken());
            }
        });
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