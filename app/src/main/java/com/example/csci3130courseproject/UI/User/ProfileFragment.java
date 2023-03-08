package com.example.csci3130courseproject.UI.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.Listing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    TextView emailField, passwordField, nameField, phoneNumberField, dateOfBirthField, locationField,
    preferredJobsField, creditCardField, creditCardCVVField, ratingField;

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailField = (TextView) requireView().findViewById(R.id.email);
        passwordField = (TextView) requireView().findViewById(R.id.password);
        nameField = (TextView) requireView().findViewById(R.id.name);
        phoneNumberField = (TextView) requireView().findViewById(R.id.phoneNumber);
        dateOfBirthField = (TextView) requireView().findViewById(R.id.dateOfBirth);
        locationField = (TextView) requireView().findViewById(R.id.location);
        preferredJobsField = (TextView) requireView().findViewById(R.id.preferredJobs);
        creditCardField = (TextView) requireView().findViewById(R.id.creditCardNumber);
        creditCardCVVField = (TextView) requireView().findViewById(R.id.CCV);
        ratingField = (TextView) requireView().findViewById(R.id.rating);

        Button editInformationButton = (Button)requireView().findViewById(R.id.editProfile);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference(Listing.class.getSimpleName());

        //Populate fields with user data from firebase
        getUserInfo();

        editInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInformation(view);
            }
        });
    }

    public void editInformation(View view) {
        Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_editUserProfileFragment);
    }

    public void getUserInfo() {
        Intent intent = getActivity().getIntent();

        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        nameField.setText(name);
        emailField.setText(email);
        passwordField.setText(password);

    }
}
