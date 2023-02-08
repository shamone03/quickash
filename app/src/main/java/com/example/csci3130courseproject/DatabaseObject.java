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

    protected String recordKey;

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
     * @return Map of key-value pairs containing all data to be sent to firebase.
     */
    public abstract Map<String, Object> mapValues();

    /**
     * @return The DatabaseReference object associated with the subclass.
     */
    public DatabaseReference getDatabaseReference() {
        return databaseReferences.get(this.getClass().getSimpleName());
    }

    public String getRecordKey(){
        return recordKey;
    }

    /**
     * Retrieves a record related to the subclass via a key
     * @param key String key used by Firebase to uniquely identify the data record.
     * @return Task describing the outcome of the get request.
     */
    public Task<DataSnapshot> getRecord(String key) {
        DatabaseReference databaseReference = getDatabaseReference();
        Task<DataSnapshot> record = databaseReference.child(key).get();

        // If no firebase record associated with key, object is not fully initialized
        if (record.getResult().exists() == true) {
            recordKey = key;
        } else {
            Log.w("Firebase", "getRecord: Record with key " + key + " could not be found in firebase.");
        }

        return record;
    }

    /**
     * Sends a push request to Firebase, adding a record of the object to the realtime database.
     * @return Task describing the outcome of the push request.
     */
    public Task<Void> pushRecord() {
        DatabaseReference databaseReference = getDatabaseReference();
        recordKey = databaseReference.push().getKey();

        return databaseReference.push().setValue(this.mapValues());
    }

    /**
     * Updates the data of an existing record in Firebase's realtime database.
     * @param data Key-value pair map to be sent to Firebase.
     * @return Task describing the outcome of the update request.
     */
    public Task<Void> updateData(Map<String, Object> data) {
        if (recordKey == null) {
            throw new NullPointerException("Object has not been linked to a Firebase record");
        }

        DatabaseReference databaseReference = getDatabaseReference();

        return databaseReference.child(recordKey).updateChildren(data);
    }

    /**
     * Deletes the Firebase record associated with this object, and nulls the recordKey
     * @return Task describing the outcome of the delete request
     */
    public Task<Void> deleteRecord() {
        if (recordKey == null) {
            throw new NullPointerException("Object has not been linked to a Firebase record");
        }

        DatabaseReference databaseReference = getDatabaseReference();
        Task<Void> task = databaseReference.child(recordKey).removeValue();
        recordKey = null;

        return task;
    }
}
