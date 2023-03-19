package com.example.csci3130courseproject.Utils;

import android.Manifest;
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

    public ObtainingLocation(Context context){
        getLocation(context);
    }

    public Location getLocation(Context context){
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        GPSstatus = checkIfGPSEnabled(context);
        if(!GPSstatus) {
            GPSstatus = turnOnGPS(context);
        }

        if(GPSstatus){
            location = null;
            if(location == null){
//                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,);
            }
        }else{
            //Code if GPS is not enabled. Based on entered address.
        }

        return null;
    }

    public boolean checkIfGPSEnabled(Context context){
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean turnOnGPS(Context context){
        AlertDialog.Builder newAlertDialog = new AlertDialog.Builder(context);
        newAlertDialog.setMessage("Do you want to enable GPS?");
        newAlertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                );
                context.startActivity(intent);
            }
        });

        return checkIfGPSEnabled(context);
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    }
}
