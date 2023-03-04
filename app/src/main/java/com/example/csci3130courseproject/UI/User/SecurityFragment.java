package com.example.csci3130courseproject.UI.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csci3130courseproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class SecurityFragment extends Fragment {

    private EditText currentEmail, currentPassword, newEmail, newPassword;

    public SecurityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_security, container, false);
    }

    /*
        This function gets the current user using FirebaseAuth, and outputs the user as a FirebaseUser
        object.
     */
    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    /*
        Re-authenticates the user based on the provided email and password.
        More functionality to be added, currently only works to check the current email.
     */

    public boolean re_authenticateUser(FirebaseUser currentUser, EditText currentEmail, EditText currentPassword){
        String currentUserEmail = currentUser.getEmail();
        if(currentEmail.getText().toString().trim().equals(currentUserEmail)){
            return true;
        }
        return false;
    }

    /*
        This function takes in the current and new email and password and updates their values.
        If any updates are made, it returns true else it returns false.
        This boolean is used to create toasts for the respective cases.
     */
    public boolean changeUserSecurityValues(FirebaseUser currentUser, EditText currentEmail,EditText currentPassword, EditText newEmail, EditText newPassword){

        //Variable to keep track of the number of changes.
        int changes = 0;
        if(!re_authenticateUser(currentUser,currentEmail,currentPassword)){
            return false;
        }

        if(newEmail.getText().toString().trim() != null && !newEmail.getText().toString().trim().equals("")){
            currentUser.updateEmail(newEmail.getText().toString().trim());
            changes++;
        }

        if(newPassword.getText().toString().trim() != null && !newPassword.getText().toString().trim().equals("")){
            currentUser.updateEmail(newPassword.getText().toString().trim());
            changes++;
        }

        if(changes>0){
            return true;
        }else{
            return false;
        }
    }

    /*
        This view takes in a view and a bundle saved Instance state.
        Multiple EditText objects are used for current and new emails and passwords.
        A button object to submit the new security information.

        The listener is used to call the function change security values, which returns true if
        any value is changed, else returns false.

        A toast for each.
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        currentEmail = (EditText)getView().findViewById(R.id.currentEmailField);
        currentPassword = (EditText)getView().findViewById(R.id.currentPasswordField);
        newEmail = (EditText)getView().findViewById(R.id.newEmailField);
        newPassword = (EditText)getView().findViewById(R.id.newPasswordField);

        Button submitSecurityInformation = (Button)getView().findViewById(R.id.submitSecurityChangeButton);

        submitSecurityInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = getUser();
                boolean changeResult = changeUserSecurityValues(currentUser,currentEmail, currentPassword, newEmail, newPassword);
                if(changeResult){
                    Toast.makeText(getActivity(),"Security Details Changes Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Current Email or Password Incorrect. Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}