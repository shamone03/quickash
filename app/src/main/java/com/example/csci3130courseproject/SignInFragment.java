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

/**
 * Fragment that handles the sign-in process
 */
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
}