package com.example.csci3130courseproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

    // Methods:
    private double toDouble(String text) throws NumberFormatException{
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    public String getJobTitle(){
        EditText jobTitleTextField = (EditText) getView().findViewById(R.id.createJP_PostingTitle);
        return jobTitleTextField.getText().toString();
    }

    public double getJobSalary(){
        EditText jobSalaryTextField = (EditText) getView().findViewById(R.id.createJP_JobSalary);
        Double salary = toDouble(jobSalaryTextField.getText().toString());
        return salary;
    }

}