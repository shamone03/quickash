package com.example.csci3130courseproject.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

public class Permissions {

    public static boolean checkFineLocationPermission(Activity activity){
        int result = ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION);
        return (result == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean checkCoarseLocationPermission(Activity activity){
        int result = ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_COARSE_LOCATION);
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
