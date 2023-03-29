package com.example.csci3130courseproject.Utils;

import android.location.Location;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

public class getJobNotifications extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public getJobNotifications(){}

    public void notifyAllUsers(View view, Location location){
        FirebaseDatabase getFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users").addChildEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            UserObject user = snapshot.getValue(UserObject.class);
                            mMap = google
                        }
                    }
                }
        )



    }


}
