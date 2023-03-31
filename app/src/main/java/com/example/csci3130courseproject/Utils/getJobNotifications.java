package com.example.csci3130courseproject.Utils;

import static androidx.test.InstrumentationRegistry.getContext;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.core.Context;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

public class getJobNotifications extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final String BASE_URL = "https://fcm.googleapis.com";
    public static final String SERVER_KEY = "BF5zbJia7kbjFfEACZahrmZCWbFhny3zOrLV5mM-nntibtx0R175ZVvT_M4bv1M7kdlHivNeAIYn89I9iy133YY";

    public getJobNotifications() {
    }
    // send email when new job in the area (same city?) is posted
    public void notifyAllUsers(Context context, Location location) {
        FirebaseDatabase getFirebaseDatabase = (FirebaseDatabase) FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // retrieve users' locations.
                            UserObject user = snapshot.getValue(UserObject.class);
                            Location currLocation = (new ObtainingLocation(getApplicationContext())).getLocation(getApplicationContext());

                            // compare with job posting location
                            Location job = (Location) dataSnapshot.child("jobLocation").child("convertedLocation").getValue();
                            JobLocation jobLocation = new JobLocation(job);
                            //Double jobLongitude = (Double) dataSnapshot.child("jobLocation").child("convertedLocation").child("longitude").getValue();
                            //jobLocation.setLongitude(jobLongitude);

                            float distance = currLocation.distanceTo(jobLocation.getConvertedLocation());
                            // if within 30km of user location, send notification
                            if (distance < 30) {
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "CSCI3130 Course Project");
                                builder.setContentTitle("New Job Posting");
                                builder.setContentText("There is a new job posting in your area!");
                                builder.setAutoCancel(true);

                                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                managerCompat.notify(1, builder.build());
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );


    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }


}
