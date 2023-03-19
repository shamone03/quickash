package com.example.csci3130courseproject.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ObtainingLocation extends AppCompatActivity implements LocationListener {

    private boolean GPSstatus = false;
    Location location;
    double latitude;
    double longitude;
    private static final int MIN_DISTANCE_FOR_UPDATES = 500;
    private static final int MIN_TIME_FOR_UPDATES = 10000;
    private Context mContext;

    public ObtainingLocation(Context context){
        this.mContext = context;
        getLocation();
    }

    //Gets the context, checks if gps is enabled, if enabled, then uses the location and stores it.

    @SuppressLint("MissingPermission")
    public Location getLocation(){
        LocationManager mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        GPSstatus = checkIfGPSEnabled();
        if(!GPSstatus) {
            GPSstatus = turnOnGPS();
        }

        // check again if user enabled GPS
        if(GPSstatus){
            //location = null;
            if(location == null){
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_FOR_UPDATES,
                        MIN_DISTANCE_FOR_UPDATES,this);
                if(mLocationManager!=null){
                    location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    return location;
                }
            }
        }else{
            // if permission is denied, show a message
            Toast.makeText(this,"Location Permission Denied: Please enable location from Settings",Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    public boolean checkIfGPSEnabled(){
        LocationManager mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //Provides a dialog box to ask user to enable gps.
    //2 buttons, one to go to settings, one to cancel.
    //Either way, check if gps enabled
    //And the result of the check is returned.
    public boolean turnOnGPS(){
        AlertDialog.Builder newAlertDialog = new AlertDialog.Builder(mContext);
        newAlertDialog.setMessage("Do you want to enable GPS?");
        newAlertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                );
                mContext.startActivity(intent);
            }
        });

        return checkIfGPSEnabled();
    }

    //Longitude
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    //Latitude
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }


    //Called when location is changed.
    @Override
    public void onLocationChanged(@NonNull Location newLocation) {
        this.location = newLocation;
        this.latitude = newLocation.getLatitude();
        this.longitude = newLocation.getLongitude();
    }

    //Need to implement this.
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        this.location = mLocationManager.getCurrentLocation();
        getLocation();


    }
}
