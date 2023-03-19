package com.example.csci3130courseproject.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Permissions {

    public static boolean checkFineLocationPermission(Activity activity){
        int result = ContextCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION);
        return (result == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean checkCoarseLocationPermission(Activity activity){
        int result = ContextCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_COARSE_LOCATION);
        return (result == PackageManager.PERMISSION_GRANTED);
    }

    //
    public static void requestPermission(Activity activity){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
    }

    public static void permissionCheck(Activity activity){
        if(checkFineLocationPermission(activity)){
            //When permission has been received already.
            return;
            //?
        }else{
            requestPermission(activity);
        }
    }
}
