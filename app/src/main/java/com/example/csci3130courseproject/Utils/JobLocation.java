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

    public Location getConvertedLocation() {
        Location ret = new Location("");
        ret.setAccuracy(accuracy);
        ret.setLongitude(lon);
        ret.setLatitude(lat);
        return ret;
    }
}
