package com.example.csci3130courseproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private EditText emailField, passwordField, nameField, phoneNumberField, dateOfBirthField,
            locationField, preferredJobsField, creditCardField,
            creditCardCVVField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        emailField = (EditText)getView().findViewById(R.id.email);
        passwordField = (EditText)getView().findViewById(R.id.password);
        nameField = (EditText)getView().findViewById(R.id.name);
        phoneNumberField = (EditText)getView().findViewById(R.id.phoneNumber);
        dateOfBirthField = (EditText)getView().findViewById(R.id.dateOfBirth);
        locationField = (EditText)getView().findViewById(R.id.location);
        preferredJobsField = (EditText)getView().findViewById(R.id.preferredJobs);
        creditCardField = (EditText)getView().findViewById(R.id.creditCardNumber);
        creditCardCVVField = (EditText)getView().findViewById(R.id.CVV);
        phoneNumberField = (EditText)getView().findViewById(R.id.phoneNumber);

        Button editInformationButton = (Button)getView().findViewById(R.id.editInformation);
        Button confirmButton = (Button)getView().findViewById(R.id.confirmEditInformation);

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
