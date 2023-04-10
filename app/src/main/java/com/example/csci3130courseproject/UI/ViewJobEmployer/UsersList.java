package com.example.csci3130courseproject.UI.ViewJobEmployer;

import androidx.annotation.NonNull;

import com.example.csci3130courseproject.Utils.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Set;

public class UsersList implements UserListener{

    ArrayList<UserObject> users = new ArrayList<>();
    UserListListener client;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference userDatabase = database.getReference("users");

    public UsersList(){ /* Empty Constructor */ }

    public void getUsers(Set<String> userIds){
        for (String uid : userIds){
            userDatabase.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful() && task.isComplete()) {
                        users.add(task.getResult().getValue(UserObject.class));
                        updateUserList();
                    }
                }
            });
        }
    }

    @Override
    public void addListener(UserListListener listener) { client = listener; }

    @Override
    public void updateUserList() {
        client.updateList(users);
    }
}
