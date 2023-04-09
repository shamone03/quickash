package com.example.csci3130courseproject.Utils;

import android.location.Location;

public class JobLocation {
    private double lat;
    private double lon;
    private float accuracy;

    public JobLocation() {};

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
