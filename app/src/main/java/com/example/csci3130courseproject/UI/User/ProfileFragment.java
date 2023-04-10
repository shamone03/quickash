package com.example.csci3130courseproject.UI.User;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.JobLocation;
import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.example.csci3130courseproject.Utils.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("users");
    private DatabaseReference jobRef = database.getReference("jobs");
    private UserObject targetUser;
    private String userId;

    //For when employer views employee profile considering hiring them.
    private String jobId;
    private JobPostingObject jobAppliedFor;
    private ArrayList<Object[]> jobsArray = new ArrayList<>();
    private Button editInformationButton;
    private Button jobsTakenButton;
    private Button jobsCreatedButton;
    private Button paymentHistoryButton;
    private Button jobsSavedButton;
    private Button acceptApplicantButton;
    private TextView username;
    private TextView errorText;
    private TextView userRating;
    private LinearLayout jobsList;
    private LinearLayout buttonsLayout;
    private LinearLayout acceptApplicantLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jobsList = (LinearLayout) requireView().findViewById(R.id.jobsList);
        buttonsLayout = (LinearLayout) requireView().findViewById(R.id.profileButtonLayout);
        acceptApplicantLayout = (LinearLayout) requireView().findViewById(R.id.applicantButtonLayout);
        editInformationButton = (Button)requireView().findViewById(R.id.editProfile);
        jobsTakenButton = (Button)requireView().findViewById(R.id.showJobsTaken);
        jobsCreatedButton = (Button)requireView().findViewById(R.id.showJobsCreated);
        paymentHistoryButton = (Button)requireView().findViewById(R.id.showAnalytics);
        jobsSavedButton = (Button)requireView().findViewById(R.id.showJobsSaved);
        acceptApplicantButton = (Button)requireView().findViewById(R.id.acceptApplicantButton);
        username = (TextView)requireView().findViewById(R.id.profileUsername);
        errorText = (TextView)requireView().findViewById(R.id.errorText);
        userRating = (TextView)requireView().findViewById(R.id.rating);

        super.onCreate(savedInstanceState);

        // If no UserID, assume we're viewing our own profile
        if (getArguments().getString("userId") == null) {
            userId = currentUser.getUid();
        } else {
            userId = getArguments().getString("userId");
            //The id of the job the employer is looking to hire this user for.
            jobId = getArguments().getString("jobId");
        }
        errorText.setVisibility(View.GONE);

        // Display and connect job buttons if the profile belongs to the user
        if (isOwnProfile()) {
            populateJobs(true,true, false);

            editInformationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editInformation(view);
                }
            });

            jobsTakenButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateJobs(true,true, false);
                }
            });

            jobsCreatedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateJobs(true,false, false);
                }
            });

            paymentHistoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateAnalytics();
                }
            });

            jobsSavedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateJobs(true, false, true);
                }
            });
        } else {
            userId = getArguments().getString("userId");
        }
        username.setText(currentUser.getDisplayName());
        errorText.setVisibility(View.GONE);

        userRef.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    targetUser = task.getResult().getValue(UserObject.class);
                    username.setText(targetUser.getUsername());
                    userRating.setText(String.valueOf(targetUser.getEmployeeRating()));

                    if (targetUser != null) {
                        // Display and connect job buttons if the profile belongs to the user
                        if (isOwnProfile()) {
                            acceptApplicantLayout.setVisibility(View.GONE);
                            populateJobs(true,true, false);

                            editInformationButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    editInformation(view);
                                }
                            });

                            jobsTakenButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    populateJobs(true,true, false);
                                }
                            });

                            jobsCreatedButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    populateJobs(true,false, false);
                                }
                            });

                            jobsSavedButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    populateJobs(true, false, true);
                                }
                            });
                        } else {
                            editInformationButton.setVisibility(View.GONE);
                            buttonsLayout.setVisibility(View.GONE);
                            populateAnalytics();
                            acceptApplicantButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    acceptApplicant(view);
                                }
                            });
                        }
                    }
                }
            }
        });


    }

    public void editInformation(View view) {
        Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_editUserProfileFragment);
    }

    public void acceptApplicant(View view) {
        jobRef.child(jobId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> jobTask) {
                if (!jobTask.isSuccessful()) {
                    Log.e("firebase", "Error getting data", jobTask.getException());
                }
                else {
                    jobAppliedFor = jobTask.getResult().getValue(JobPostingObject.class);
                    jobAppliedFor.applicantAccepted(userId, jobId);
                    DatabaseReference jobIDRef = jobRef.child(jobId);
                    jobIDRef.setValue(jobAppliedFor);
                }
            }
        });
        String userId = currentUser.getUid();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_self);
    }

    /**
     *
     * @return
     */
    private boolean isOwnProfile () {
        return currentUser.getUid().equals(userId);
    }

    /**
     * Resets the job list back to its default state
     */
    private void clearJobList() {
        errorText.setVisibility(View.GONE);
        jobsList.removeAllViews();
    }

    private void setError(String message) {
        errorText.setText(message);
        errorText.setVisibility(View.VISIBLE);
    }

    /**
     *
     * @param history
     * @param taken
     */
    private void populateJobs(boolean history, boolean taken, boolean saved) {
        clearJobList();
        boolean shownJobs = false;
        userRef.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    setError("There was a problem retrieving your data");
                }
                else {
                    boolean shownJobs = false;
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    UserObject profileUser = task.getResult().getValue(UserObject.class);
                    if (profileUser == null) {
                        setError("Could not find user data");
                        return;
                    } else {
                        Map<String, Boolean> jobIdList;

                        //calling getUserRating in here for now since it is stored on userObj. Can probably extract later.
                        userRating.setText(String.valueOf(profileUser.getEmployeeRating()));
                        if (taken) {
                            jobIdList = (HashMap<String, Boolean>) profileUser.getJobsTaken();
                        } else {
                            jobIdList = (HashMap<String, Boolean>) profileUser.getJobPostings();
                        }
                        if (saved) {
                            jobIdList = (HashMap<String, Boolean>) profileUser.getJobsSaved();
                        }
                        // Safe guard
                        if (jobIdList == null)
                            return;

                        // Populate jobList with jobs
                        for (Map.Entry<String, Boolean> job : jobIdList.entrySet()) {
                            shownJobs = true;
                            Log.d("ProfileTesting",job.getKey());
                            jobRef.child(job.getKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> listingTask) {
                                    if (!listingTask.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", listingTask.getException());
                                    }
                                    else {
                                        if (history) {
                                            createListingPreview(listingTask.getResult());
                                        } else {
                                            //TODO: Requires a job completed tag to be added to listings
                                        }
                                    }
                                }
                            });
                        }

                        // If there are no jobs, let the user know
                        if (shownJobs == false) {
                            if (taken) {
                                setError("You have not taken any jobs");
                            } else {
                                setError("You have not created any jobs");
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Shows the payment history of jobs that the user has completed as an employer
     */
    private void populateAnalytics() {
        clearJobList();
        userRef.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            boolean shownJobs = false;
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    setError("There was a problem retrieving your data");
                }
                else {
                    boolean shownJobs = false;
                    UserObject profileUser = task.getResult().getValue(UserObject.class);
                    if (profileUser == null) {
                        setError("Could not find user data");
                        return;
                    } else {
                        //calling getUserRating in here for now since it is stored on userObj. Can probably extract later.
                        userRating.setText(String.valueOf(profileUser.getEmployeeRating()));
                        Map<String, Boolean> jobIdList = profileUser.getJobsTaken();

                        // Safe guard
                        if (jobIdList == null)
                            return;

                        // Populate jobList with jobs
                        for (Map.Entry<String, Boolean> job : jobIdList.entrySet()) {
                            shownJobs = true;
                            Log.d("ProfileTesting",job.getKey());
                            jobRef.child(job.getKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> listingTask) {
                                    if (!listingTask.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", listingTask.getException());
                                    }
                                    else {
                                        JobPostingObject jobPosting = listingTask.getResult().getValue(JobPostingObject.class);

                                        if (jobPosting == null) {
                                            return;
                                        } else if (jobPosting.getJobComplete() == true) {
                                            createPaymentCard(jobPosting);
                                        }
                                    }
                                }
                            });
                        }

                        // If there are no jobs, let the user know
                        if (shownJobs == false) {
                            setError("You have not paid any employees");
                        }
                    }
                }
            }
        });
    }

    private double getUserRating(UserObject user) {
        return user.getEmployeeRating();
    }

    public void createListingPreview(DataSnapshot listingSnapshot) {
        // Creating Listing object and view to display data to user
        JobPostingObject jobPosting = listingSnapshot.getValue(JobPostingObject.class);

        if (jobPosting == null) {
            return;
        }

        userRef.child(jobPosting.getJobPoster()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> employerTask) {
                if (!employerTask.isSuccessful()) {
                    Log.e("firebase", "Error getting data", employerTask.getException());
                } else {
                    UserObject employerObject = employerTask.getResult().getValue(UserObject.class);
                    View listingPreview = getLayoutInflater().inflate(R.layout.prefab_listing_preview,null,false);

                    // Modifying placeholder text to match data from Listing object
                    TextView title = listingPreview.findViewById(R.id.titleLabel);
                    TextView hours = listingPreview.findViewById(R.id.hoursLabel);
                    TextView salary = listingPreview.findViewById(R.id.salaryLabel);
                    TextView employer = listingPreview.findViewById(R.id.employerLabel);
                    TextView locationName = listingPreview.findViewById(R.id.locationLabel);
                    listingPreview.findViewById(R.id.saveButton).setVisibility(View.GONE);

                    // Setting job card text
                    if (jobPosting != null) {
                        title.setText(String.format("Title: %s", jobPosting.getJobTitle()));
                        hours.setText(String.format("Hours: %s", jobPosting.getJobDuration()));
                        salary.setText(String.format("Salary: %.2f", jobPosting.getJobSalary()));
                        if (jobPosting.getJobLocation() != null) {
                            locationName.setText(String.format("Location: %s", JobLocation.getLocationName(getContext(), jobPosting.getJobLocation())));
                        }
                        if (employerObject != null) {
                            employer.setText(String.format("Employer: %s", employerObject.getUsername()));
                        } else {
                            employer.setText("Employer: NULL");
                        }
                    } else {
                        title.setText("Title: NULL");
                        hours.setText("Hours: NULL");
                        salary.setText("Salary: NULL");
                        employer.setText("Employer: NULL");

                    }
                    // Hiding apply button
                    AppCompatButton applyButton = listingPreview.findViewById(R.id.applyButton);
                    applyButton.setText("View");

                    applyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (currentUser.getUid().equals(jobPosting.getJobPoster())){
                                Bundle jobInfo = new Bundle();
                                jobInfo.putString("JobId", listingSnapshot.getKey());
                                Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_viewJobEmployer, jobInfo);
                            }else{
                                // TODO: Go to view job as employee
                            }
                        }
                    });
                    // Adding Listing object and View to ArrayList to be referenced later
                    Object[] listing = {jobPosting, listingPreview};
                    jobsArray.add(listing);
                    jobsList.addView((View)listingPreview);
                }
            }
        });
    }

    public void createPaymentCard(JobPostingObject job) {
        View paymentPreview = getLayoutInflater().inflate(R.layout.prefab_payment_preview,null,false);
        TextView title = paymentPreview.findViewById(R.id.titleLabel);
        TextView hours = paymentPreview.findViewById(R.id.hoursLabel);
        TextView salary = paymentPreview.findViewById(R.id.salaryLabel);
        TextView employees = paymentPreview.findViewById(R.id.employeesLabel);
        TextView total = paymentPreview.findViewById(R.id.paymentLabel);
        int employeesPaid = 0;

        // Setting card text
        title.setText(String.format("Title: %s", job.getJobTitle()));
        hours.setText(String.format("Hours: %s", job.getJobDuration()));
        salary.setText(String.format("Salary: %.2f", job.getJobSalary()));

        for (Map.Entry<String, Boolean> employee : job.getEmployees().entrySet()) {
            if (employee.getValue() == true) {
                employeesPaid++;
            }
        }

        employees.setText("Employees: " + employeesPaid);
        total.setText("Paid: " + (job.getJobSalary() * employeesPaid));

        jobsList.addView(paymentPreview);
    }
}
