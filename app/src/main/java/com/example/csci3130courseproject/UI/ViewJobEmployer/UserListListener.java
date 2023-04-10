package com.example.csci3130courseproject.UI.ViewJobEmployer;

import androidx.fragment.app.Fragment;

import com.example.csci3130courseproject.Utils.UserObject;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class UserListListener extends Fragment {

    public UserListener listener;

    public abstract void updateList(HashMap<String, UserObject> updatedList);

}
