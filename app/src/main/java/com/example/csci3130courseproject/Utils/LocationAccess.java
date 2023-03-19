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

public class LocationAccess extends AppCompatActivity {

    String APIKey = "AIzaSyCXMdClKMOSRbxYIHx3tW-cvYxzicmKzGs";
    private GoogleMap map;
    private static final String TAG = "MainActivity";
    int LOCATION_REQUEST_CODE = 101;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationManager locationManager;
    Double latitude, longitude;
    String city="", country="";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if((!Permissions.checkFineLocationPermission(this)) &&
            !Permissions.checkCoarseLocationPermission(this)) {
            Permissions.requestPermission(this);
        }
    }


//    public void onMapReady(@NonNull GoogleMap googleMap, Activity activity) {
//        map = googleMap;
//        map.setOnMyLocationButtonClickListener(GoogleMap.OnMyLocationButtonClickListener({}));
//        map.setOnMyLocationClickListener(this);
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
//    }

    //@Override
    // along with onRequestPermissionsResult, this method requests for permission
    protected void checkPermissions(Activity activity) {
        // Check if permissions are granted
        if(ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
        // else, request location permission
        else {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,}, LOCATION_REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults, Activity activity){
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    // gets the actual location by longitude, latitude, and city

    private void getLocation(Activity activity) {
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null) {
                                Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                                List<Address> address = null;
                                try {
                                    latitude = address.get(0).getLatitude();
                                    longitude = address.get(0).getLongitude();
                                    city = address.get(0).getLocality();
                                    country = address.get(0).getCountryName();
                                    address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
        } else {
            askPermission(activity);
        }
    }


    // ask user for permission
    private void askPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(activity.checkSelfPermission(LocationAccess.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && activity.checkSelfPermission(activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            }else{

            }
        }
//       if(ActivityCompat.requestPermissions(activity,new String[] {
//               Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE)){
//
//       }
    }


}
