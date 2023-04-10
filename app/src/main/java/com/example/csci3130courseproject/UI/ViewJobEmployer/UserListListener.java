package com.example.csci3130courseproject.UI.ViewJobEmployer;

import androidx.fragment.app.Fragment;

import com.example.csci3130courseproject.Utils.UserObject;

import java.util.ArrayList;

public abstract class UserListListener extends Fragment {

    public UserListener listener;

    public abstract void updateList(ArrayList<UserObject> updatedList);

}
