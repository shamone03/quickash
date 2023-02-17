package com.example.csci3130courseproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Space;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListingSearchFragment extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Object[]> pagedListings = new ArrayList<>();
    private LinearLayout cardPreviewList;
    private SearchView searchBar;

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
        Query query = databaseReference.orderByChild("salary");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot listingSnapshot: dataSnapshot.getChildren()) {
                    createListingPreview(listingSnapshot);
                }
                updateList();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Testing", "loadPost:onCancelled", databaseError.toException());
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
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.onActionViewExpanded();
            }
        });
    }


    public void createListingPreview(DataSnapshot listingSnapshot) {
        // Creating Listing object and view to display data to user
        Listing newListing = new Listing(listingSnapshot);
        View listingPreview = getLayoutInflater().inflate(R.layout.prefab_listing_preview,null,false);

        // Modifying placeholder text to match data from Listing object
        TextView title = listingPreview.findViewById(R.id.titleLabel);
        TextView hours = listingPreview.findViewById(R.id.hoursLabel);
        TextView salary = listingPreview.findViewById(R.id.salaryLabel);

        title.setText(String.valueOf(newListing.getValue("title")));
        hours.setText("Hours: " + String.valueOf(newListing.getValue("hours")));
        salary.setText("Salary: " + String.valueOf(newListing.getValue("salary")));

        // Adding Listing object and View to ArrayList to be referenced later
        Object[] listing = {newListing, listingPreview};
        pagedListings.add(listing);
    }

    public boolean filterTitles(String title, String query) {
        String lowerTitle = title.toLowerCase();

        if (query.equals("")) {
            return true;
        } else if (lowerTitle.contains(query.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public void updateList() {
        // Clearing previously displayed listing previews
        cardPreviewList.removeAllViews();

        for (Object[] listingReference : pagedListings) {
            Listing listing = (Listing)listingReference[0];
            String query = searchBar.getQuery().toString().toLowerCase();
            // Filtering listings based on criteria provided by the user
            if (filterTitles(String.valueOf(listing.getValue("title")), query) == false) {
                continue;
            }

            // Adding view to list layout
            cardPreviewList.addView((View)listingReference[1]);
        }
    }
}