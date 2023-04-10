package com.example.csci3130courseproject.UI.HomePage;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.UI.ViewJobEmployer.IJobCallback;
import com.example.csci3130courseproject.UI.ViewJobEmployer.IUserCallback;
import com.example.csci3130courseproject.Utils.JobLocation;
import com.example.csci3130courseproject.Utils.JobPostingObject;
import com.example.csci3130courseproject.Utils.JobRecommendation;
import com.example.csci3130courseproject.Utils.ObtainingLocation;
import com.example.csci3130courseproject.Utils.Permissions;
import com.example.csci3130courseproject.Utils.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kotlinx.coroutines.Job;

/**
 * Handles the sign-up process
 */
public class ListingSearchFragment extends Fragment {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference jobsDatabaseReference;
    private DatabaseReference databaseReference;
    private DatabaseReference userRef;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference jobRef = database.getReference("jobs");
    private ArrayList<Object[]> pagedListings = new ArrayList<>();
    private LinearLayout cardPreviewList;
    private SearchView searchBar;
    private Spinner filterSpinner;
    private EditText filterInput;
    private boolean hasBeenNotified;
    private UserObject userO;
    private String jobTitle;
    private HashMap<String, Boolean> jobsTakenIds = new HashMap<>();
    private ArrayList<JobPostingObject> allJobs = new ArrayList<>();
    private ArrayList<String[]> jobsTakenTitles = new ArrayList<>();
    private ArrayList<String[]> allJobTitles = new ArrayList<>();

    public ListingSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_listing_search, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Permissions.requestPermission(getActivity());
        cardPreviewList = (LinearLayout)getView().findViewById(R.id.listingCardList);
        searchBar = (SearchView)getView().findViewById(R.id.searchBar);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        databaseReference = database.getReference("jobs");
        jobsDatabaseReference = database.getReference("jobs");
        filterSpinner = (Spinner)getView().findViewById(R.id.filterSpinner);
        filterInput = (EditText)getView().findViewById(R.id.filterInput);

        if(!hasBeenNotified) {
            setUserObject(new IUserCallback() {
                @Override
                public void onGetUserSuccess(UserObject user) {
                    String jobRecommended = populateJobs();
                    String message = String.format("%s", jobRecommended);
                    if(!message.equals("None")){
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                    setHasBeenNotified(true);
                }
            });
        }


        // TODO: Replace hardcoded query with a spinner read
        Query query = jobsDatabaseReference.orderByChild("salary");

        // Retrieves all Listing records from firebase and
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clearing old Listings to make way for new Listings
                pagedListings.clear();

                for (DataSnapshot listingSnapshot: dataSnapshot.getChildren()) {
                    createListingPreview(listingSnapshot);
                    allJobs.add(listingSnapshot.getValue(JobPostingObject.class));
                }

                updateList();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Firebase", "loadPost:onCancelled", databaseError.toException());
            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateList();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchBar.getQuery().toString().equals("")) {
                    updateList();
                }
                return false;
            }
        });

        filterInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateList();
            }
        });
    }

    /**
     * Converts DataSnapshots into Listing objects, creating an associated
     * @param listingSnapshot DataSnapshot containing all Listing records currently in firebase
     */
    public void createListingPreview(DataSnapshot listingSnapshot) {
        // Creating Listing object and view to display data to user
        JobPostingObject jobPosting = listingSnapshot.getValue(JobPostingObject.class);

        if (jobPosting.getJobPoster() == null) {
            return;
        }


        userRef.child(jobPosting.getJobPoster()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> employerTask) {
                if (!employerTask.isSuccessful()) {
                    Log.e("firebase", "Error getting data", employerTask.getException());
                } else {
                    UserObject employerObject = employerTask.getResult().getValue(UserObject.class);

                    if (employerObject == null) {
                        return;
                    }

                    View listingPreview = getLayoutInflater().inflate(R.layout.prefab_listing_preview,null,false);

                    // Modifying placeholder text to match data from Listing object
                    TextView title = listingPreview.findViewById(R.id.titleLabel);
                    TextView hours = listingPreview.findViewById(R.id.hoursLabel);
                    TextView salary = listingPreview.findViewById(R.id.salaryLabel);
                    TextView employer = listingPreview.findViewById(R.id.employerLabel);
                    TextView locationName = listingPreview.findViewById(R.id.locationLabel);

                    if (jobPosting != null) {
                        title.setText(String.format("Title: %s", jobPosting.getJobTitle()));
                        hours.setText(String.format("Hours: %s", jobPosting.getJobDuration()));
                        salary.setText(String.format("Salary: %.2f", jobPosting.getJobSalary()));
                        employer.setText(String.format("Employer ID: %s", employerObject.getUsername()));
                        if (jobPosting.getJobLocation() != null) {
                            locationName.setText(String.format("Location: %s", JobLocation.getLocationName(getContext(), jobPosting.getJobLocation())));
                        }
                    } else {
                        title.setText("Title: NULL");
                        hours.setText("Hours: NULL");
                        salary.setText("Salary: NULL");
                        employer.setText("Employer: NULL");
                        Log.d("LOCATION", jobPosting.getJobTitle() + " is null");
                    }

                    // Connecting button event listener to apply the user to a job listing
                    AppCompatButton applyButton = listingPreview.findViewById(R.id.applyButton);
                    AppCompatButton saveButton = listingPreview.findViewById(R.id.saveButton);


                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
                    DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(listingSnapshot.getKey());

                    userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            UserObject currentUser = task.getResult().getValue(UserObject.class);
                            if (currentUser != null) {
                                if (currentUser.getJobsTaken() != null) {
                                    currentUser.getJobsTaken().containsKey(listingSnapshot.getKey());
                                    applyButton.setText("Apply");
                                    applyButton.setBackground(getResources().getDrawable(R.drawable.background_rounded_button_inactive));
                                }
                                if (currentUser.getJobsSaved() != null) {
                                    if (currentUser.getJobsSaved().containsKey(listingSnapshot.getKey())) {
                                        saveButton.setText("Unsave");
                                        saveButton.setBackground(getResources().getDrawable(R.drawable.background_rounded_button_inactive));
                                    }
                                }

                            }
                        }
                    });
                    applyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (applyButton.getText().toString().equals("Apply")) {
                                applyButton.setText("Applied");
                                applyButton.setBackground(getResources().getDrawable(R.drawable.background_rounded_button_inactive));


                                // adds the job id to current user
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
                                DatabaseReference jobsTakenRef = userRef.child(user.getUid()).child("jobsTaken");
                                Map<String, Object> takenJob = new HashMap<>();
                                takenJob.put(listingSnapshot.getKey(), false);
                                jobsTakenRef.updateChildren(takenJob);
                                // add user to jobObject
                                DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(listingSnapshot.getKey());
                                Map<String, Object> val = new HashMap<>();
                                val.put(user.getUid(), false);
                                jobRef.child("employees").updateChildren(val);

                            }
                        }
                    });

                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (saveButton.getText().toString().equals("Save")) {
                                saveButton.setText("Unsave");
                                saveButton.setBackground(getResources().getDrawable(R.drawable.background_rounded_button_inactive));

                                DatabaseReference jobsSavedRef = userRef.child("jobsSaved");
                                Map<String, Object> savedJob = new HashMap<>();
                                savedJob.put(listingSnapshot.getKey(), false);
                                jobsSavedRef.updateChildren(savedJob);

                            }
                        }
                    });

                    // Adding Listing object and View to ArrayList to be referenced later
                    Object[] listing = {jobPosting, listingPreview};
                    pagedListings.add(listing);
                    updateList();
                }
            }
        });
    }

    /**
     * @return String representation of the selected drop-down filter
     */
    private String getFilter() {
        return(filterSpinner.getSelectedItem().toString());
    }

    private void setHasBeenNotified(Boolean b) {
        this.hasBeenNotified = b;
    }

    /**
     * Determines if the job posting was posted by the current user
     * @param employerID String representation of query used to filter
     * @return
     */
    public boolean filterMyPositings(String employerID){
        String userid = user.getUid();
        return (employerID.equals(userid.toLowerCase()));
    }

    /**
     * Compares the search bar query with a title to determine if the title should be included
     * @param title Title of the job listing
     * @param query String representing the query used to filter the titles of Listing objects
     * @return Boolean true if the title passes the query
     */
    public static boolean filterTitle(String title, String query) {
        String sanitizedTitle = title.toLowerCase();
        String sanitizedQuery = query.trim().toLowerCase();

        return (sanitizedQuery.equals("") || sanitizedTitle.contains(sanitizedQuery));
    }

    /**
     * @param salary Salary that the job listing is offering
     * @param lowerBounds Lowest value that will pass
     * @return Boolean true if the salary is above the lower bounds
     */
    public static boolean filterSalary(double salary, double lowerBounds) {
        return (lowerBounds < 0 || salary >= lowerBounds);
    }

    public boolean filterLocation(Location jobLocation, double distanceLimit) {
        if (jobLocation == null) {
            // The job has no location because it is a remote listing, and so cannot be filtered
            return true;
        } else {
            Location userLocation = (new ObtainingLocation(getContext())).getLocation(getContext());
            return (userLocation.distanceTo(jobLocation) < distanceLimit);
        }
    }

    /**
     * Calculates the distances between two positions in 3d space
     * @param x1 X coordinate for the first position
     * @param y1 y coordinate for the first position
     * @param x2 x coordinate for the second position
     * @param y2 y coordinate for the second position
     * @return Straight-line distance between the two positions
     */
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.hypot(Math.abs(y2 - y1), Math.abs(x2 - x1));
    }

    /**
     * Updates the list of job Listings by deleting old UI cards and adding new ones based on
     * the filter and sort criteria.
     */
    public void updateList() {
        // Clearing previously displayed listing previews
        cardPreviewList.removeAllViews();

        for (Object[] listingReference : pagedListings) {
            JobPostingObject listing = (JobPostingObject) listingReference[0];
            String query = searchBar.getQuery().toString().toLowerCase();

            // Removing entries where the employer is the user
            if (listing.getJobPoster() == null || listing.getJobPoster().equals(user.getUid()) == true) {
                continue;
            }

            // Filtering listings based on criteria provided by the user
            if (filterTitle(String.valueOf(listing.getJobTitle()), query) == false) {
                continue;
            } else { // Filtering based on current filter settings
                if (getFilter().equals("Pay rate")) {
                    try {
                        if (filterSalary(listing.getJobSalary(),
                                Double.parseDouble(filterInput.getText().toString())) == false) {
                            continue;
                        }
                    } catch(NumberFormatException e) {
                        // Do nothing. An invalid number is treated the same as a negative/empty
                    }
                } else if (getFilter().equals("Distance")) {
                    try {
                        Location jobLocation = new Location("");
                        jobLocation.setAccuracy(listing.getJobLocation().getAccuracy());
                        jobLocation.setLongitude(listing.getJobLocation().getLon());
                        jobLocation.setLatitude(listing.getJobLocation().getLat());
                        if (filterLocation(jobLocation,
                                Double.parseDouble(filterInput.getText().toString())) == false) {
                            continue;
                        }
                    } catch(NumberFormatException e) {
                        // Do nothing. An invalid number is treated the same as a negative/empty
                    }
                }
            }

            // Adding view to list layout
            cardPreviewList.addView((View)listingReference[1]);
        }
    }

    public void setUserObject(IUserCallback callback) {
        String Uid = user.getUid();
        database.getReference("users").child(Uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    Log.w("FetchUser", Uid);
                    userO = task.getResult().getValue(UserObject.class);
                    userO.setUserId(Uid);
                    if (user != null) {
                        callback.onGetUserSuccess(userO);
                    } else {
                        Log.w("UserError",  "User is null");
                    }
                }
            }
        });
    }

    public String populateJobs() {
        if (userO == null) {
            System.out.println("User object is null");
            return "None";
        } else {
            for(JobPostingObject job: allJobs) {
                allJobTitles.add(job.getJobTitle().split(" ", 2));
                if(job.getJobEmployeeID().equals(user.getUid())){
                    jobsTakenTitles.add(job.getJobTitle().split(" ", 2));
                }
            }
            String message = recommendJob();
            return message;
        }
    }

    public String recommendJob() {
        String recommendation;
        if(jobsTakenTitles == null || allJobTitles == null) {
            System.out.println("Arrays are empty");
            return "";
        }
        for (String[] title: jobsTakenTitles) {
            String firstWord = title[0];
            for (String[] titleCompare: allJobTitles) {
                String firstWordToCompare = titleCompare[0];
                if(firstWord.equals(firstWordToCompare)){
                    if(title.length > 1) {
                        recommendation = String.format("%s %s is similar to other jobs you have done and is available to apply to!", firstWord, title[1]);
                        return recommendation;
                    } else {
                        recommendation = String.format("%s is similar to other jobs you have done and is available to apply to!", firstWord);
                        return recommendation;
                    }

                }
            }
        }
        recommendation = "None";
        return recommendation;
    }

    public void setUserObject(UserObject u) {
        this.userO = u;
    }

    private void getJob(IJobCallback callback, String jobId){
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

    private String getLocation(JobLocation location) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLat(), location.getLon(), 1);
            return addresses.get(0).getCountryName() + " " + addresses.get(0).getLocality() + " " + addresses.get(0).getPostalCode();
        } catch (Exception e) {
            //Toast.makeText(getContext(), "Error getting location name", Toast.LENGTH_SHORT).show();
        }
        return "Location unavailable";
    }
}