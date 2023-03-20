package com.example.csci3130courseproject.UI.User;

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

import com.example.csci3130courseproject.R;
import com.example.csci3130courseproject.Utils.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditUserProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    UserObject profileUser;
    FirebaseUser currentUser = getUser();
    private EditText emailField, passwordField, nameField, phoneNumberField, dateOfBirthField,
            locationField, preferredJobsField, creditCardField, creditCardCVVField, countryField,
            provinceField, cityField, addressField;

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

    public boolean changeUserValues(FirebaseUser currentUser){
        if(nameField.getText().toString().trim().equals("")) {
            return false;
        }
        if(passwordField.getText().toString().trim().equals("")) {
            return false;
        }
        if(phoneNumberField.getText().toString().trim().equals("")) {
            return false;
        }
        if(dateOfBirthField.getText().toString().trim().equals("")) {
            return false;
        }
        if(creditCardField.getText().toString().trim().equals("")) {
            return false;
        }
        if(creditCardCVVField.getText().toString().trim().equals("")) {
            return false;
        }
        if(countryField.getText().toString().trim().equals("")) {
            return false;
        }
        if(provinceField.getText().toString().trim().equals("")) {
            return false;
        }
        if(cityField.getText().toString().trim().equals("")) {
            return false;
        }
        if(addressField.getText().toString().trim().equals("")) {
            return false;
        }

        FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    profileUser = task.getResult().getValue(UserObject.class);
                    profileUser.updateUser(nameField.getText().toString().trim(), phoneNumberField.getText().toString().trim(),
                            dateOfBirthField.getText().toString().trim(),
                            creditCardField.getText().toString().trim(), creditCardCVVField.getText().toString().trim(),
                            countryField.getText().toString().trim(), provinceField.getText().toString().trim(),
                            cityField.getText().toString().trim(), addressField.getText().toString().trim());
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName(nameField.getText().toString().trim()
//                .updateEmail(emailField.getText().toString().trim())
//                ).build()
                }
            }
        });
        return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        //Button to submit the display name.
        Button submitProfileInformation = (Button)getView().findViewById(R.id.submitChangesButton);
        FirebaseUser user = getUser();
        FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    profileUser = task.getResult().getValue(UserObject.class);
                    emailField = (EditText)getView().findViewById(R.id.User_Email);
                    if(!user.getEmail().isEmpty()) {
                        emailField.setText(user.getEmail());
                    }
                    passwordField = (EditText)getView().findViewById(R.id.User_Password);
                    passwordField.setText("*************");
                    nameField = (EditText)getView().findViewById(R.id.Name);
                    if(!profileUser.getUsername().isEmpty()) {
                        nameField.setText(profileUser.getUsername());
                    }
                    phoneNumberField = (EditText)getView().findViewById(R.id.Phone_Num);
                    if(profileUser.getPhoneNumber() != null) {
                        phoneNumberField.setText(profileUser.getPhoneNumber());
                    }
                    dateOfBirthField = (EditText)getView().findViewById(R.id.Date_Of_Birth);
                    if(profileUser.getDateOfBirth() != null) {
                        dateOfBirthField.setText(profileUser.getDateOfBirth());
                    }
                    countryField = (EditText)getView().findViewById(R.id.Country);
                    if(profileUser.getCountry() != null) {
                        countryField.setText(profileUser.getCountry());
                    }
                    provinceField = (EditText)getView().findViewById(R.id.Province);
                    if(profileUser.getProvince() != null) {
                        provinceField.setText(profileUser.getProvince());
                    }
                    cityField = (EditText)getView().findViewById(R.id.City);
                    if(profileUser.getCity() != null) {
                        cityField.setText(profileUser.getCity());
                    }
                    addressField = (EditText)getView().findViewById(R.id.Address);
                    if(profileUser.getAddress() != null) {
                        addressField.setText(profileUser.getAddress());
                    }
                    creditCardField = (EditText)getView().findViewById(R.id.Credit_Card_Num);
                    if(profileUser.getCreditCardNumber() != null) {
                        creditCardField.setText(profileUser.getCreditCardNumber());
                    }
                    creditCardCVVField = (EditText)getView().findViewById(R.id.Ccv);
                    if(profileUser.getCreditCardCVV() != null) {
                        creditCardCVVField.setText(profileUser.getCreditCardCVV());
                    }
                }
            }
        });


        /*
            New On click listener which calls getUser() and changeUserValues.
            changeUserValues takes in the currentUser, and the nameField, and changes the user name.
            If changeUserValues returns true, then a Toast saying details changed successfully,
            Else error called, and a Toast for the same.
         */
        submitProfileInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean changeResult = changeUserValues(currentUser);
                profileUser.updateUser(nameField.getText().toString(), phoneNumberField.getText().toString(),
                        dateOfBirthField.getText().toString(), creditCardField.getText().toString(),
                        creditCardCVVField.getText().toString(), countryField.getText().toString(),
                        provinceField.getText().toString(), cityField.getText().toString(),
                        addressField.getText().toString());

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
