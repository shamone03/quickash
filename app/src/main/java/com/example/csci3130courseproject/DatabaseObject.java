package com.example.csci3130courseproject;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Superclass of objects that interact with the realtime database.
 * Handles database communication overhead.
 */
public abstract class DatabaseObject {
    private static FirebaseDatabase database;
    private static HashMap<String, DatabaseReference> databaseReferences = new HashMap<>();

    /**
     * Called implicitly before the construction of a subclass object.
     * Connects to Firebase's realtime database and creates a DatabaseReference for each subclass.
     */
    public DatabaseObject() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
        }

        // Gets className of the object extending this class
        String className = this.getClass().getSimpleName();

        // Adds a new reference for every new unique subclass
        if (databaseReferences.get(className) == null) {
            databaseReferences.put(className, database.getReference(className));
        }
    }

    /**
     * Maps all values to be replicated to firebase during a push().
     * Allows for field name declarations & enforces data rules through implicit Map rules.
     * @return Map containing all values to be sent to firebase.
     */
    public abstract Map<String, Object> mapValues();

    /**
     * @return The DatabaseReference object associated with the subclass.
     */
    public DatabaseReference getDatabaseReference() {
        return databaseReferences.get(this.getClass().getSimpleName());
    }

    /**
     * Retrieves a record related to the subclass via a key
     * @param key String key used by Firebase to uniquely identify the data record.
     * @return Task describing the outcome of the get request.
     */
    public Task<DataSnapshot> getData(String key) {
        DatabaseReference databaseReference = getDatabaseReference();
        return databaseReference.child(key).get();
    }

    /**
     * Sends a push request to Firebase, adding a record of the object to the realtime database.
     * @return Task describing the outcome of the push request.
     */
    public Task<Void> pushData() {
        DatabaseReference databaseReference = getDatabaseReference();
        return databaseReference.push().setValue(this.mapValues());
    }

    /**
     * Updates the data of an existing record in Firebase's realtime database.
     * @param key String key used by Firebase to uniquely identify the data record.
     * @param data Key-value pair map to be sent to Firebase.
     * @return Task describing the outcome of the update request.
     */
    public Task<Void> updateData(String key, Map<String, Object> data) {
        DatabaseReference databaseReference = getDatabaseReference();
        return databaseReference.child(key).updateChildren(data);
    }
}
