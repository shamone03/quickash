package com.example.csci3130courseproject.UI.HomePage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.Listing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Handles the sign-up process
 */
public class ListingSearchFragment extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<Object[]> pagedListings = new ArrayList<>();
    private LinearLayout cardPreviewList;
    private SearchView searchBar;
    private Spinner filterSpinner;
    private EditText filterInput;

    public ListingSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listing_search, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        cardPreviewList = (LinearLayout)getView().findViewById(R.id.listingCardList);
        searchBar = (SearchView)getView().findViewById(R.id.searchBar);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Listing.class.getSimpleName());
        filterSpinner = (Spinner)getView().findViewById(R.id.filterSpinner);
        filterInput = (EditText)getView().findViewById(R.id.filterInput);

        // TODO: Replace hardcoded query with a spinner read
        Query query = databaseReference.orderByChild("salary");

        // Retrieves all Listing records from firebase and
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clearing old Listings to make way for new Listings
                pagedListings.clear();

                for (DataSnapshot listingSnapshot: dataSnapshot.getChildren()) {
                    createListingPreview(listingSnapshot);
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
    }

    /**
     * Converts DataSnapshots into Listing objects, creating an associated
     * @param listingSnapshot DataSnapshot containing all Listing records currently in firebase
     */
    public void createListingPreview(DataSnapshot listingSnapshot) {
        // Creating Listing object and view to display data to user
        Listing newListing = new Listing(listingSnapshot);
        View listingPreview = getLayoutInflater().inflate(R.layout.prefab_listing_preview,null,false);

        // Modifying placeholder text to match data from Listing object
        TextView title = listingPreview.findViewById(R.id.titleLabel);
        TextView hours = listingPreview.findViewById(R.id.hoursLabel);
        TextView salary = listingPreview.findViewById(R.id.salaryLabel);
        TextView employer = listingPreview.findViewById(R.id.employerLabel);

        title.setText(String.valueOf(newListing.getValue("title")));
        hours.setText("Hours: " + String.valueOf(newListing.getValue("hours")));
        salary.setText("Salary: " + String.valueOf(newListing.getValue("salary")));
        employer.setText("Employer: " + String.valueOf(newListing.getValue("employer")));

        // Connecting button event listener to apply the user to a job listing
        AppCompatButton applyButton = listingPreview.findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (applyButton.getText().toString().equals("Apply")) {
                    applyButton.setText("Applied");
                    applyButton.setBackground(getResources().getDrawable(R.drawable.background_rounded_button_inactive));
                    newListing.addEmployee(user.getUid());
                }
            }
        });

        // Adding Listing object and View to ArrayList to be referenced later
        Object[] listing = {newListing, listingPreview};
        pagedListings.add(listing);
    }

    /**
     * @return String representation of the selected drop-down filter
     */
    private String getFilter() {
        return(filterSpinner.getSelectedItem().toString());
    }

    /**
     * Determines if the job posting was posted by the current user
     * @param String representation of query used to filter
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
    public static boolean filterSalary(int salary, int lowerBounds) {
        return (lowerBounds < 0 || salary >= lowerBounds);
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
            Listing listing = (Listing)listingReference[0];
            String query = searchBar.getQuery().toString().toLowerCase();

            // Filtering listings based on criteria provided by the user
            if (filterTitle(String.valueOf(listing.getValue("title")), query) == false) {
                continue;
            } else { // Filtering based on current filter settings
                if (getFilter().equals("Pay rate")) {
                    try {
                        if (filterSalary(Integer.parseInt(listing.getValue("salary").toString()),
                                Integer.parseInt(filterInput.getText().toString())) == false) {
                            continue;
                        }
                    } catch(NumberFormatException e) {
                        // Do nothing. an invalid number is the same as a negative and so wont filter
                    }
                } else if (getFilter().equals("Distance")) {
                    // Waiting on Kayleen's location implementation
                }else if (getFilter().equals("My Postings")){
                    if (filterMyPositings(listing.getValue("employer").toString()) == false){
                        // Add modified cardPreview
                        continue;
                    }
                }
            }

            // Adding view to list layout
            cardPreviewList.addView((View)listingReference[1]);
        }
    }
}