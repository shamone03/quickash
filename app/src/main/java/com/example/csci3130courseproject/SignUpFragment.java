package com.example.csci3130courseproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentActivity activity;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button signUpButton = getView().findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailView = getView().findViewById(R.id.signupEmail);
                String email = emailView.getText().toString().trim();
                EditText passwordView = getView().findViewById(R.id.editTextTextPassword);
                String password = passwordView.getText().toString().trim();
                EditText rePasswordView = getView().findViewById(R.id.editTextTextPassword2);
                String rePassword = rePasswordView.getText().toString().trim();
                if (!validateEmail(email)) return;
                if (!validatePasswords(password, rePassword)) return;
                signUpUser(emailView.getText().toString(), passwordView.getText().toString(), new UserCallback() {
                    @Override
                    public void isSuccessful(boolean successful) {
                        if (successful) {
                            Toast.makeText(getActivity(), "Sign Up Successful", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment);
                        }
                    }
                });
            }
        });
    }

    public void signUpUser(String email, String password, UserCallback callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference userRef = database.getReference("users");
                    User newUser = new User(new ArrayList<>(), new ArrayList<>());
                    String uid = task.getResult().getUser().getUid();
                    userRef.child(uid).setValue(newUser);
                } else {
                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                    if (errorCode.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                        Toast.makeText(getActivity(), "This email address is already in use", Toast.LENGTH_SHORT).show();
                    } else if (errorCode.equals("ERROR_INVALID_EMAIL")) {
                        Toast.makeText(getActivity(), "Invalid email", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("SIGNUP", ((FirebaseAuthException) task.getException()).getErrorCode());
                        Toast.makeText(getActivity(), "Sign Up Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
                callback.isSuccessful(task.isSuccessful());
            }
        });
    }

    private boolean validateEmail(String email) {
        if (!Pattern.compile("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+").matcher(email).find()) {
            Toast.makeText(getActivity(), "Invalid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePasswords(String password, String rePassword) {
        if (!password.equals(rePassword)) {
            Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(getActivity(), "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() > 24) {
            Toast.makeText(getActivity(), "Password should be at most 24 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}