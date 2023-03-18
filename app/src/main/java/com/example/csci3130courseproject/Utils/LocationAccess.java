package com.example.csci3130courseproject.Utils;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


// NEEDED TO COMMENT THIS OUT IN ORDER TO ELIMINATE THE ACCESS_FINE_LOCATION ERROR
// import com.example.csci3130courseproject.Manifest;
import android.Manifest;

public class LocationAccess {

    String APIKey = "AIzaSyAM6UrFYCGWK9hMeKlSUWdceou5zeD2dbs";
    private GoogleMap map;


    //@Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);

        // checkSelfPermission returns: PERMISSION_GRANTED or PERMISSION_DENIED
        // depending on whether app has permission

        if(ActivityCompat.checkSelfPermission(this.Activity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            


        }

    }

    private void getLocationPermission() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();
    }



}
