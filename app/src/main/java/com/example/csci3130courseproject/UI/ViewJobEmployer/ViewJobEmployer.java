package com.example.csci3130courseproject.UI.ViewJobEmployer;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.csci3130courseproject.Callbacks.JobsCallback;
import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.example.csci3130courseproject.Utils.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewJobEmployer extends Fragment {
    JobPostingObject currentJob;

    ArrayList<Object[]> jobApplicants = new ArrayList<>();

    Button saveEdit, applicants;
    EditText jobDescription;
    TextView jobTitle, applicantName;

    LinearLayout applicantsContainer;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

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
        database = FirebaseDatabase.getInstance();
        // TODO: Modify child reference to be that of the specific job selected.
        databaseReference = database.getReference("Listing").child("-NOf8LrJF1mzs_9lWcau").child("employees");

        applicantsContainer = (LinearLayout) getView().findViewById(R.id.ViewJobEmployerJobApplicantsContainer);

        // TODO: OrderByRating
        Query applicants = databaseReference.orderByKey();
        populateApplicantListView(getView(), applicants);

        getJobs(FirebaseAuth.getInstance().getUid(), new JobsCallback() {
            @Override
            public List<JobPostingObject> onJobDetails(List<JobPostingObject> jobDetails) {
                return null;
            }
        });

    }

    private void createApplicantPreview(DataSnapshot applicantSnapshot){
        // currentApplicant = applicantSnapshot.getValue(UserObject.class);
        View jobApplicantPreview = getLayoutInflater().inflate(R.layout.prefab_view_job_applicant_preview, applicantsContainer, true);

        // Job Posting Preview Modifiable attributes:
        ImageView profilePicture = jobApplicantPreview.findViewById(R.id.jobApplicantAvatar);
        TextView applicantName = jobApplicantPreview.findViewById(R.id.jobApplicantName);
        TextView applicantRating = jobApplicantPreview.findViewById(R.id.jopApplicantRating);
        Button applicantButton = jobApplicantPreview.findViewById(R.id.jobApplicantButton);

        // Load information:
        profilePicture.setImageURI(Uri.parse(""));
        applicantName.setText("John Doe");
        applicantRating.setText(String.format("Employee Rating: %.2f",2.5));

        applicantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to user profile & acceptance button
            }
        });

        jobApplicants.add(new Object[]{jobApplicantPreview});

    }

    private void createApplicant(DatabaseReference userReference){
        Task<DataSnapshot> userTask = userReference.get();
        userTask.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                createApplicantPreview(task.getResult());
            }
        });
    }

    public void populateApplicantListView(@NonNull View view, Query applicants){
        createApplicantPreview(null);
    }

    public void getJobs(String uid, JobsCallback callback) {
        FirebaseDatabase.getInstance().getReference("users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                UserObject user = task.getResult().getValue(UserObject.class);

                assert user != null;
                Toast.makeText(getContext(), user.toString(), Toast.LENGTH_SHORT).show();
                callback.onJobDetails(user.getJobsTaken());
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