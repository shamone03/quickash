package com.example.csci3130courseproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class SecurityFragment extends Fragment {

    private EditText currentemail, currentPassword, newEmail, newPassword;

    public SecurityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_security, container, false);
    }

    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
    /*
        Placeholder Security Function
     */
    public boolean changeUserSecurityValues(FirebaseUser currentUser, EditText newEmail, EditText newPassword){
        return true;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        /*
        currentemail = (EditText)getView().findViewById(R.id.currentEmailField);
        currentPassword = (EditText)getView().findViewById(R.id.currentPasswordField);
        newEmail = (EditText)getView().findViewById(R.id.newEmailField);
        newPassword = (EditText)getView().findViewById(R.id.newPasswordField);

        Button submitSecurityInformation = (Button)getView().findViewById(R.id.submitSecurityChangeButton);

        submitSecurityInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = getUser();
                boolean changeResult = changeUserSecurityValues(currentUser,newEmail, newPassword);
                if(changeResult){
                    Toast.makeText(getActivity(),"Details Changed Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

         */


    }

}