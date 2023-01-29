package com.example.csci3130courseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailField = (EditText)findViewById(R.id.Sign_In_Email);
        passwordField = (EditText)findViewById(R.id.Sign_In_Password);
    }

    // Checks  when the button is clicked, returning true if both are valid
    public void Sign_In_Request(View view) {
        String emailAddress = emailField.getText().toString();
        String password = passwordField.getText().toString();

        // Local validation to determine if address and password is worth sending to server
        if (emailAddress.contains("@") == false) {
            // Email address is not valid
            Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.length() < 6) {
            // Password can't exist - sign up would not allow it
            Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Send off to firebase for validation

        // TODO: Respond to firebase confirmation code
    }

    // Link to sign-up page
    public void SignInToSignUp(View view) {

    }
}