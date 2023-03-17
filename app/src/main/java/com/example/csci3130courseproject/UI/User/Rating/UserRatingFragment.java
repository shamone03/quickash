package com.example.csci3130courseproject.UI.User.Rating;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.csci3130courseproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserRatingFragment extends Fragment {
    private RatingBar ratingBar;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_rating, container, false);
    }

    public void rate(View v){
        TextView message = (TextView)getView().findViewById(R.id.message);
        message.setText("You Rated :" +String.valueOf(ratingBar.getRating()));
    }
}