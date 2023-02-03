package com.example.csci3130courseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText emailField, passwordField;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        emailField = (EditText)findViewById(R.id.Sign_In_Email);
        passwordField = (EditText)findViewById(R.id.Sign_In_Password);
    }

    // Checks fields when the button is clicked and attempts sign-in
    public void Sign_In_Request(View view) {
        String emailAddress = emailField.getText().toString();
        String password = passwordField.getText().toString();

        // Local validation to determine if address and password is worth sending to server
        if (emailAddress.contains("@") == false || emailAddress.contains(".") == false) {
            // Email address is not valid
            Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.length() < 6 || password.length() > 24) {
            // Password can't exist as sign up would not allow it
            Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Attempt Firebase sign-in
        mAuth.signInWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Firebase", "signInWithEmail:success");
                    // TODO: Transition to dashboard
                } else {
                    Log.w("Firebase", "signInWithEmail:failure", task.getException());
                }
            }
        });
    }

    // TODO: Transition to sign-up page
    public void SignInToSignUp(View view) {

    }
}