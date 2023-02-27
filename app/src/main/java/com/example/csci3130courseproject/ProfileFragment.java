package com.example.csci3130courseproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private TextView emailField, passwordField, nameField, phoneNumberField, dateOfBirthField,
            locationField, preferredJobsField, creditCardField,
            creditCardCVVField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        emailField = (TextView)getView().findViewById(R.id.email);
        passwordField = (TextView)getView().findViewById(R.id.password);
        nameField = (TextView)getView().findViewById(R.id.name);
        phoneNumberField = (TextView)getView().findViewById(R.id.phoneNumber);
        dateOfBirthField = (TextView)getView().findViewById(R.id.dateOfBirth);
        locationField = (TextView)getView().findViewById(R.id.location);
        preferredJobsField = (TextView)getView().findViewById(R.id.preferredJobs);
        creditCardField = (TextView)getView().findViewById(R.id.creditCardNumber);
        creditCardCVVField = (TextView)getView().findViewById(R.id.CCV);
        phoneNumberField = (TextView)getView().findViewById(R.id.phoneNumber);

        Button editInformationButton = (Button)getView().findViewById(R.id.editProfile);

        editInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditInformation(view);
            }
        });

//        confirmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SignInToSignUp(view);
//            }
//        });
    }
}
