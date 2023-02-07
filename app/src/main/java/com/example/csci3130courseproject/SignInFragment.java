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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    private EditText emailField, passwordField;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        emailField = (EditText)getView().findViewById(R.id.Sign_In_Email);
        passwordField = (EditText)getView().findViewById(R.id.Sign_In_Password);

        Button signInButton = (Button)getView().findViewById(R.id.Sign_In_Request);
        Button signUpButton = (Button)getView().findViewById(R.id.GoToSignUp);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sign_In_Request(view);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInToSignUp(view);
            }
        });
    }

    public void Sign_In_Request(View view) {
        String emailAddress = emailField.getText().toString();
        String password = passwordField.getText().toString();

        // Local validation to determine if address and password is worth sending to server
        if (emailAddress.contains("@") == false || emailAddress.contains(".") == false) {
            // Email address is not valid
            Log.i("SignIn", "Failed email validation");
            return;
        } else if (password.length() < 6 || password.length() > 24) {
            // Password can't exist as sign up would not allow it
            Log.i("SignIn", "Failed password validation");
            return;
        }

        // Attempt Firebase sign-in
        mAuth.signInWithEmailAndPassword(emailAddress, password)
            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("Firebase", "signInWithEmail:success");
                    Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_dashboardFragment);
                } else {
                    Log.w("Firebase", "signInWithEmail:failure", task.getException());
                }
            }
        });
    }

    public void SignInToSignUp(View view) {
        Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment);
    }
}