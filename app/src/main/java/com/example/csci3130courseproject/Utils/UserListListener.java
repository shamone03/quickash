package com.example.csci3130courseproject.Utils;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public abstract class UserListListener extends Fragment {

    public UserListener listener;

    public abstract void updateList(ArrayList<UserObject> updatedList);

}
