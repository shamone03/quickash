package com.example.csci3130courseproject;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.atomic.AtomicBoolean;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.sign_up);
    }
    //    call this function with the email and password to create a new user
    public boolean signUpUser(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
//        atomic  allows modification of values from inside lambda functions and other threads
        AtomicBoolean result = new AtomicBoolean(false);
        mAuth.createUserWithEmailAndPassword("aryah@gmail.com", "password").addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                assert user != null;
                Toast.makeText(getApplicationContext(), "User created" + user.getEmail(), Toast.LENGTH_LONG).show();
                result.set(true);
            } else {
//                    System.out.println(task.getException());
//                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                task.getException().printStackTrace();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                result.set(false);
            }
        });
        return result.get();
    }
}
