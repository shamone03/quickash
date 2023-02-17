package com.example.csci3130courseproject;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateListingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateListingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateListingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateListingFragment newInstance(String param1, String param2) {
        CreateListingFragment fragment = new CreateListingFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_listing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        Button createPosting = (Button) getView().findViewById(R.id.createJP_button);
    }

    // Methods:
    private double toDouble(String text) throws NumberFormatException{
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    public EditText getJobTitleField(){
        return (EditText) getView().findViewById(R.id.createJP_PostingTitle);
    }

    public EditText getJobSalaryField(){
        return (EditText) getView().findViewById(R.id.createJP_JobSalary);
    }

    public double getJobSalary(){
        return toDouble(getJobSalaryField().getText().toString());
    }

    public EditText getJobDurationField(){
        return (EditText) getView().findViewById(R.id.createJP_JobDurration);
    }

    public Spinner getJobPriorityField() { return (Spinner) getView().findViewById(R.id.createJP_priority); }

    public String getJobPriority(Spinner priorityList){
        return priorityList.getSelectedItem().toString();
    }

    // Click method:
    public void onButtonClick(){
        String posterID = FirebaseAuth.getInstance().getUid();
        EditText jobTitle = getJobTitleField();
        EditText jobSalaryField = getJobSalaryField();
        EditText jobDurationField = getJobDurationField();
        String priorityLevel = getJobPriority(getJobPriorityField());


    }

}