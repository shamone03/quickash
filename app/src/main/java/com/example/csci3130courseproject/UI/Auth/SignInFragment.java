package com.example.csci3130courseproject.UI.Auth;

import android.content.Intent;
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
import com.example.csci3130courseproject.Callbacks.UserCallback;
import com.example.csci3130courseproject.UI.User.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Fragment that handles the sign-in process
 */

//TODO: Trying to sign in without any input crashes app.
public class SignInFragment extends Fragment {
    private EditText emailField, passwordField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailField = (EditText)getView().findViewById(R.id.Sign_In_Email);
        passwordField = (EditText)getView().findViewById(R.id.Sign_In_Password);

        Button signInButton = (Button)getView().findViewById(R.id.Sign_In_Request);
        Button signUpButton = (Button)getView().findViewById(R.id.GoToSignUp);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser(emailField.getText().toString().trim(), passwordField.getText().toString().trim(), new UserCallback() {
                    @Override
                    public void isSuccessful(boolean successful) {
                        if (successful) {
                            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_listingSearchFragment);
                        } else {
                            Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInToSignUp(view);
            }
        });
    }

    /**
     * Takes information given by the user via the interactable elements of the UI, and attempts
     * to sign them into Firebase.
     * @param email Email address of the user
     * @param password Password of the user
     * @param callback
     */
    public void signInUser(String email, String password, UserCallback callback) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                callback.isSuccessful(task.isSuccessful());
            }
        });
    }

    /**
     * Sends the user to the SignUpFragment UI
     * @param view View that the button resides in
     */
    private void SignInToSignUp(View view) {
        Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment);
    }

    public void checkUser(){
        String userEmail = emailField.getText().toString().trim();
        String userPassword = passwordField.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    emailField.setError(null);
                    String passwordFromDB = snapshot.child(userEmail).child("password").getValue(String.class);

                    if(passwordFromDB.equals(userPassword)){
                        emailField.setError(null);

                        //Using Intents to pass user data to profile page
                        String nameFromDB = snapshot.child(userEmail).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userEmail).child("email").getValue(String.class);

                        Intent intent = new Intent(getView().getContext(), ProfileFragment.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    } else {
                        passwordField.setError("Invalid Credentials");
                        passwordField.requestFocus();
                    }
                } else {
                    emailField.setError("Invalid Credentials");
                    emailField.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}