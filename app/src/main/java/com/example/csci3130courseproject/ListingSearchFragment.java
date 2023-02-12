package com.example.csci3130courseproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private ArrayList<Listing> pagedListings = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listing_search, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Listing.class.getSimpleName());
        Query query = databaseReference.orderByChild("salary");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot listingSnapshot: dataSnapshot.getChildren()) {
                    Listing listing = new Listing(listingSnapshot);
                    pagedListings.add(listing);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Testing", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}