package com.example.csci3130courseproject.UI.User.Rating;

import android.content.DialogInterface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRatingFragment extends Fragment {

    private String userBeingRatedID;
    private RatingBar ratingBar;
    private UserObject userObject;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("users");

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    public UserRatingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userBeingRatedID = getArguments().getString("userID");

        FirebaseDatabase.getInstance().getReference("users").child(userBeingRatedID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    userObject = task.getResult().getValue(UserObject.class);
                    return;
                }
            }
        });
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        ratingBar = (RatingBar)getView().findViewById(R.id.ratingBar);
        LayerDrawable stars=(LayerDrawable)ratingBar.getProgressDrawable();
        Button submitRating = (Button)getView().findViewById(R.id.submitRating);
        submitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double rating = Double.valueOf(ratingBar.getRating());
                showConfirmationMessage(rating);
                TextView message = (TextView) getView().findViewById(R.id.message);
                message.setText("You Rated :" + String.valueOf(ratingBar.getRating()));
            }
            // Inflate the layout for this fragment
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user_rating,container,false);
    }

    public void showConfirmationMessage(double rating){
        AlertDialog.Builder notif = new AlertDialog.Builder(getContext());
        notif.setTitle("Rating Confirmation");
        notif.setMessage(String.format("Are you sure? Your rating is %.1f/5 stars", rating));
        notif.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Verify user isn't rating themselves:
                if (userBeingRatedID == currentUser.getUid()) {
                    return;
                }
                userObject.rateUser(rating);
                saveUserRating(rating);
                dialog.dismiss();
            }
        });
        notif.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog;
        dialog = notif.create();
        dialog.show();
    }
    private void saveUserRating(double rating){
        userRef.child(userBeingRatedID).child("rating").setValue(rating);
    }

}