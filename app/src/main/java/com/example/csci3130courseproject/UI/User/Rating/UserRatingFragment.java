package com.example.csci3130courseproject.UI.User.Rating;

import android.app.usage.NetworkStats;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRatingFragment extends Fragment {
    private RatingBar ratingBar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("users");
    private FirebaseUser currentUser = mAuth.getCurrentUser();
//    private FirebaseUser userToBeRated = mAuth.getUserByEmail(email);
// TODO: Figure out how to get the user to be rated. Should be attached to completed job somehow.


    public UserRatingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ratingBar = (RatingBar)getView().findViewById(R.id.ratingBar);
        LayerDrawable stars=(LayerDrawable)ratingBar.getProgressDrawable();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button submitRating = (Button)getView().findViewById(R.id.submitRating);

        submitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double rating = Double.valueOf(ratingBar.getRating());
                //TODO: Right now this just lets the user rate themselves. Will have to figure out how to get the other user to rate.
                UserObject profileUser = userRef.child(currentUser.getUid()).get().getResult().getValue(UserObject.class);
                profileUser.rateUser(rating);
                TextView message = (TextView) getView().findViewById(R.id.message);
                message.setText("You Rated :" + String.valueOf(ratingBar.getRating()));
            }
            // Inflate the layout for this fragment
        });
        return inflater.inflate(R.layout.fragment_user_rating,container,false);
    }

    public void showConfirmationMessage(double rating){
        AlertDialog.Builder notif = new AlertDialog.Builder(getContext());
        notif.setTitle("Rating Confirmation");
        notif.setMessage("Are you sure? Your rating is " + rating + "/5 stars");
        notif.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: Add rating to DB
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
}