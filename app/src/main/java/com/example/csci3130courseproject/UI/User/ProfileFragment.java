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
    private ArrayList<Object[]> jobsArray = new ArrayList<>();
    private Button editInformationButton;
    private Button jobsTakenButton;
    private Button jobsCreatedButton;
    private Button analyticsButton;

    private Button ratingButton;
    private TextView username;
    private TextView emailAddress;
    private TextView errorText;
    private TextView userRating;
    private LinearLayout jobsList;
    private LinearLayout buttonsLayout;

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
        editInformationButton = (Button)requireView().findViewById(R.id.editProfile);
        jobsTakenButton = (Button)requireView().findViewById(R.id.showJobsTaken);
        jobsCreatedButton = (Button)requireView().findViewById(R.id.showJobsCreated);
        analyticsButton = (Button)requireView().findViewById(R.id.showAnalytics);
        username = (TextView)requireView().findViewById(R.id.profileUsername);
        emailAddress = (TextView)requireView().findViewById(R.id.profileEmail);
        errorText = (TextView)requireView().findViewById(R.id.errorText);
        userRating = (TextView)requireView().findViewById(R.id.rating);
        ratingButton = requireView().findViewById(R.id.ratingButton);


        // If no UserID, assume we're viewing our own profile
        if (getArguments() == null) {
            userId = currentUser.getUid();
        }
        username.setText(currentUser.getDisplayName());
        emailAddress.setText(currentUser.getEmail());
        errorText.setVisibility(View.GONE);

        // Display and connect job buttons if the profile belongs to the user
        if (isOwnProfile()) {
            populateJobs(true,true);

            editInformationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editInformation(view);
                }
            });

            jobsTakenButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateJobs(true,true);
                }
            });

            jobsCreatedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateJobs(true,false);
                }
            });

            analyticsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateAnalytics();
                }
            });

            ratingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_userRatingFragment);
                }
            });
        } else {
            userId = getArguments().getString("UserID");
        }

        userRef.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    targetUser = task.getResult().getValue(UserObject.class);

                    if (targetUser != null) {
                        // Display and connect job buttons if the profile belongs to the user
                        if (isOwnProfile()) {
                            populateJobs(true,true);

                            editInformationButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    editInformation(view);
                                }
                            });

                            jobsTakenButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    populateJobs(true,true);
                                }
                            });

                            jobsCreatedButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    populateJobs(true,false);
                                }
                            });

                            analyticsButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    populateAnalytics();
                                }
                            });
                        } else {
                            editInformationButton.setVisibility(View.GONE);
                            buttonsLayout.setVisibility(View.GONE);
                            populateAnalytics();
                        }
                    }
                }
            }
        });
    }

    public void editInformation(View view) {
        Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_editUserProfileFragment);
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
    private void populateJobs(boolean history, boolean taken) {
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

    // TODO: Set up analytics page
    private void populateAnalytics() {
        setError("Analytics TBD in iteration 3");
    }

    private double getUserRating(UserObject user) {
        return user.getEmployeeRating();
    }

    public void createListingPreview(DataSnapshot listingSnapshot) {
        // Creating Listing object and view to display data to user
        JobPostingObject jobPosting = listingSnapshot.getValue(JobPostingObject.class);

        if (jobPosting == null) {
            Log.e("Firebase", "Job posting object is null.\n Related snapshot:\n"
                    + listingSnapshot.toString());
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
                                jobInfo.putString("JobID", listingSnapshot.getKey());
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
}