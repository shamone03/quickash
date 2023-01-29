package com.example.csci3130courseproject;

import android.os.Bundle;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        EditText email = findViewById(R.id.UEmailAddress);
        EditText password = findViewById(R.id.UPassword);
        EditText rePassword = findViewById(R.id.URePassword);

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email.getText(), password.getText()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    assert user != null;
                    Toast.makeText(getApplicationContext(), "User created" + user.getEmail(), Toast.LENGTH_LONG).show();
                } else {
//                    System.out.println(task.getException());
//                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    task.getException().printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
