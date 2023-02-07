package com.example.csci3130courseproject;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.tasks.Task;
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
    private static HashMap<String, DatabaseReference> databaseReferences
            = new HashMap<String, DatabaseReference>();

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
     * Allows for field name declarations & enforces data rules through implicit Map rules
     * @return Map containing all values to be sent to firebase
     */
    public abstract Map<String, Object> mapValues();

    /**
     * Sends a push request to Firebase, adding the object's data to the realtime database.
     * @return Task describing the outcome of the push request.
     */
    public Task<Void> push() {
        DatabaseReference databaseReference = databaseReferences.get(this.getClass().getSimpleName());

        return databaseReference.push().setValue(this.mapValues());
    }
}
