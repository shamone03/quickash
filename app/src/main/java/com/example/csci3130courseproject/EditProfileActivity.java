package com.example.csci3130courseproject;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class EditProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info);

        mAuth = FirebaseAuth.getInstance();
        emailField = (EditText)findViewById(R.id.Sign_In_Email);
        passwordField = (EditText)findViewById(R.id.Sign_In_Password);
    }
}
