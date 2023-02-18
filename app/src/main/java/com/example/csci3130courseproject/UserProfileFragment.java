package com.example.csci3130courseproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserProfileFragment extends Fragment {

    private EditText nameField;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    /*
        This function gets the current user using FirebaseAuth, and outputs the user as a FirebaseUser
        object.
     */
    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    /*
        This function checks the validity of the entered name string i.e. its not null or empty.
        Then based on that either updates the profile and returns true, or returns false.
        This boolean is used to create toasts for the same.
     */
    public boolean changeUserValues(FirebaseUser currentUser, EditText nameField){
        if(nameField.getText().toString().trim() != null && !nameField.getText().toString().trim().equals("")) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(nameField.getText().toString().trim()).build();
            currentUser.updateProfile(profileUpdates);
            return true;
        }

        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        nameField = (EditText)getView().findViewById(R.id.existingUserName);

        /*
            Buttons
         */
        //Navigation to go to the security fragment
        Button securityNavigation = (Button)getView().findViewById(R.id.securityNavigationButton);

        //Button to submit the display name.
        Button submitProfileInformation = (Button)getView().findViewById(R.id.submitProfileButton);

        /*
            New On click listener which calls getUser() and changeUserValues.
            changeUserValues takes in the currentUser, and the nameField, and changes the user name.
            If changeUserValues returns true, then a Toast saying details changed successfully,
            Else error called, and a Toast for the same.
         */
        submitProfileInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = getUser();
                boolean changeResult = changeUserValues(currentUser,nameField);

                //Toast for the result.
                if(changeResult){

                    Toast.makeText(getActivity(),"Details Changes Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Error Occurred. Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        securityNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userProfileToSecurity(view);
            }
        });


    }
    /*
        Action to navigate to the security features.
        This action occurs when the button "Security" is pressed.
        The onClickListener and function are implemented above.
     */
    public void userProfileToSecurity(View view){
        Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_securityFragment);
    }
}