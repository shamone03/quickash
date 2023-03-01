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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
        TextView emailField = (TextView) requireView().findViewById(R.id.email);
        TextView passwordField = (TextView) requireView().findViewById(R.id.password);
        TextView nameField = (TextView) requireView().findViewById(R.id.name);
        TextView phoneNumberField = (TextView) requireView().findViewById(R.id.phoneNumber);
        TextView dateOfBirthField = (TextView) requireView().findViewById(R.id.dateOfBirth);
        TextView locationField = (TextView) requireView().findViewById(R.id.location);
        TextView preferredJobsField = (TextView) requireView().findViewById(R.id.preferredJobs);
        TextView creditCardField = (TextView) requireView().findViewById(R.id.creditCardNumber);
        TextView creditCardCVVField = (TextView) requireView().findViewById(R.id.CCV);

        Button editInformationButton = (Button)requireView().findViewById(R.id.editProfile);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference(Listing.class.getSimpleName());

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
