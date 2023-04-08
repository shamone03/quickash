package com.example.csci3130courseproject.UI.ViewJobEmployer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
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
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewJobEmployer extends Fragment {
    String jobId;
    JobPostingObject currentJob;
    HashMap<String, Boolean> applicants;
    Button saveEdit;
    Button completeJobButton;
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
        this.jobId = getArguments().getString("JobId");
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
        completeJobButton = (Button) getView().findViewById(R.id.ViewJobEmployerCompleteButton);
        completeJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationMessage(view);
            }
        });
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
        Log.w("GotUser", "Username: " + applicant.getUsername());
        View jobApplicantPreview = getLayoutInflater().inflate(R.layout.prefab_view_job_applicant_preview, applicantsContainer, true);

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
                String userId = applicant.getUserId();
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                bundle.putString("jobId", jobId);
                Navigation.findNavController(view).navigate(R.id.action_viewJobEmployer_to_userProfileFragment, bundle);
            }
        });

        jobApplicants.add(new Object[]{applicant, jobApplicantPreview});
    }


    private void getJob(IJobCallback callback){
        database.getReference("jobs").child(jobId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

    private void getUser(IUserCallback callback, String userID) {
        database.getReference("users").child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    Log.w("FetchUser", userID);
                    UserObject user = task.getResult().getValue(UserObject.class);
                    user.setUserId(userID);
                    if (user != null) {
                        callback.onGetUserSuccess(user);
                    } else {
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

    public void showConfirmationMessage(View view){
        AlertDialog.Builder notif = new AlertDialog.Builder(getContext());
        notif.setTitle("Complete Job and Pay Employee");
        //Add employees name here
        notif.setMessage(String.format("Are you sure you want to complete this job and pay the employee?"));
        notif.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Verify user isn't rating themselves:
                currentJob.completedJob(jobId);
                String userIdToRate = currentJob.getJobEmployeeID();
                Bundle bundle = new Bundle();
                bundle.putString("userId", userIdToRate);
                dialog.dismiss();
                Navigation.findNavController(view).navigate(R.id.action_viewJobEmployer_to_userRatingFragment, bundle);
            }
        });
        notif.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog;
        dialog = notif.create();
        dialog.show();
    }

    private void initializeActivityLauncher() {
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == -1) {
                    PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirmation != null) {
                        try {
                            // Getting the payment details
                            String paymentDetails = confirmation.toJSONObject().toString(4);
                            // on below line we are extracting json response and displaying it in a text view.
                            JSONObject payObj = new JSONObject(paymentDetails);
                            String payID = payObj.getJSONObject("response").getString("id");
                            String state = payObj.getJSONObject("response").getString("state");
                        } catch (JSONException e) {
                            // handling json exception on below line
                            Log.e("Error", "an extremely unlikely failure occurred: ", e);
                        }
                    }
                    //Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID){
                    Log.d("PayPal","Launcher Result Invalid");
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Log.d("PayPal", "Launcher Result Cancelled");
                }
            }
        });
    }
}