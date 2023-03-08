package com.example.csci3130courseproject.UI.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csci3130courseproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditUserProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private EditText emailField, passwordField, nameField, phoneNumberField, dateOfBirthField,
            locationField, preferredJobsField, creditCardField,
            creditCardCVVField, phoneField, countryField, provinceField, cityField, addressField, creditCardNumberField, ccvField;

    public EditUserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        return inflater.inflate(R.layout.fragment_edit_user_profile, container, false);
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

//        UpdateRequest request = new UpdateRequest(uid)
//                .setEmail("user@example.com")
//                .setPhoneNumber("+11234567890")
//                .setEmailVerified(true)
//                .setPassword("newPassword")
//                .setDisplayName("Jane Doe")
//                .setPhotoUrl("http://www.example.com/12345678/photo.png")
//                .setDisabled(true);
//
//        UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
//        System.out.println("Successfully updated user: " + userRecord.getUid())

        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        //Button to submit the display name.
        Button submitProfileInformation = (Button)getView().findViewById(R.id.submitChangesButton);

        emailField = (EditText)getView().findViewById(R.id.User_Email);
        passwordField = (EditText)getView().findViewById(R.id.User_Password);
        nameField = (EditText)getView().findViewById(R.id.Name);
        phoneField = (EditText)getView().findViewById(R.id.Phone_Num);
        dateOfBirthField = (EditText)getView().findViewById(R.id.Date_Of_Birth);
        countryField = (EditText)getView().findViewById(R.id.Country);
        provinceField = (EditText)getView().findViewById(R.id.Province);
        cityField = (EditText)getView().findViewById(R.id.City);
        addressField = (EditText)getView().findViewById(R.id.Address);
        creditCardNumberField = (EditText)getView().findViewById(R.id.Credit_Card_Num);
        ccvField = (EditText)getView().findViewById(R.id.Ccv);
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
                //Change this to update all user fields. Check in with Alex about
                //database integration/firebase
                boolean changeResult = changeUserValues(currentUser,nameField);

                //Toast for the result.
                if(changeResult){

                    Toast.makeText(getActivity(),"Details Changes Successfully", Toast.LENGTH_SHORT).show();
                    editUserProfileToUserProfile(view);
                }else{
                    Toast.makeText(getActivity(),"Error Occurred. Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void editUserProfileToUserProfile(View view){
        Navigation.findNavController(view).navigate(R.id.action_editUserProfile_to_userProfileFragment);
    }
}
