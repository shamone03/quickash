package com.example.csci3130courseproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        TextView emailField = (TextView) getView().findViewById(R.id.email);
        TextView passwordField = (TextView) getView().findViewById(R.id.password);
        TextView nameField = (TextView) getView().findViewById(R.id.name);
        TextView phoneNumberField = (TextView) getView().findViewById(R.id.phoneNumber);
        TextView dateOfBirthField = (TextView) getView().findViewById(R.id.dateOfBirth);
        TextView locationField = (TextView) getView().findViewById(R.id.location);
        TextView preferredJobsField = (TextView) getView().findViewById(R.id.preferredJobs);
        TextView creditCardField = (TextView) getView().findViewById(R.id.creditCardNumber);
        TextView creditCardCVVField = (TextView) getView().findViewById(R.id.CCV);
        phoneNumberField = (TextView)getView().findViewById(R.id.phoneNumber);

        Button editInformationButton = (Button)getView().findViewById(R.id.editProfile);

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
}
