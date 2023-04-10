package com.example.csci3130courseproject.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class JobLocation {
    private double lat;
    private double lon;
    private float accuracy;

    public JobLocation() {};

    public static String getLocationName(Context context, JobLocation location) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLat(), location.getLon(), 1);
            return addresses.get(0).getCountryName() + " " + addresses.get(0).getLocality() + " " + addresses.get(0).getPostalCode();
        } catch (Exception e) {
            //Toast.makeText(context, "Error getting location name", Toast.LENGTH_SHORT).show();
        }
        return "Location unavailable";
    }

    public JobLocation(Location location) {
        this.lat = location.getLatitude();
        this.lon = location.getLongitude();
        this.accuracy = location.getAccuracy();
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
}
