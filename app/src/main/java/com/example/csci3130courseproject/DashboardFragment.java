package com.example.csci3130courseproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DashboardFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        Button goToUserProfile = (Button)getView().findViewById(R.id.userProfileButton);

        goToUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboardToUserProfile(view);
            }
        });


    }

    public void dashboardToUserProfile(View view){
        Navigation.findNavController(view).navigate(R.id.action_listingSearchFragment_to_userProfileFragment);
    }
}