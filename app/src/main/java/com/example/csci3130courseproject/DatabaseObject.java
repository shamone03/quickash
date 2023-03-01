package com.example.csci3130courseproject;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.compose.runtime.snapshots.Snapshot;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Superclass of objects that interact with the realtime database.
 * Handles database communication overhead.
 */
public abstract class DatabaseObject {
    /** Reference to the Group 8 Firebase realtime database.*/
    private static FirebaseDatabase database;
    /** Static hashmap holding all DatabaseReferences for the subclasses implementing DatabaseObject.*/
    private static HashMap<String, DatabaseReference> databaseReferences = new HashMap<>();
    /** Key of the database record as referenced in Firebase.*/
    private String recordKey = "";
    /** Boolean tracking the build status of the object to determine the validity of function calls.*/
    private Boolean built = false;
    /** Map containing all data to be sent to Firebase.*/
    private HashMap<String, Object> localValues = new HashMap<>();
    /** Map containing the record values that are currently stored in Firebase.*/
    private HashMap<String, Object> firebaseValues = new HashMap<>();
    /** Map containing the delta between localValues and firebaseValues.*/
    private HashMap<String, Object> valueDelta = new HashMap<>();

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
     * @return The DatabaseReference object associated with the subclass.
     */
    public DatabaseReference getReference() {
        return databaseReferences.get(this.getClass().getSimpleName());
    }

    /**
     * @return The key for the DatabaseObject
     */
    public String getKey(){
        return recordKey;
    }

    /**
     * Builds the DatabaseObject from a DataSnapshot,
     * @param snapshot DataSnapshot containing the record
     */
    public void build(DataSnapshot snapshot) {
        if (recordKey.equals("")) {
            recordKey = snapshot.getKey();

            // Adding values from snapshot to the firebaseValues map
            for (DataSnapshot data: snapshot.getChildren()) {
                firebaseValues.put(data.getKey(), data.getValue());
            }

            syncLocalValues();
        }

        // Setting up value update listener to keep firebaseValues up to date
        getReference().child(recordKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseValues.clear();

                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    firebaseValues.put(data.getKey(), data.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Firebase", "Read failed: Code " + databaseError.getCode() +
                        "\nFirebase values may not be up to date.");
            }
        });

        built = true;
    }

    /**
     * Retrieves a record related to the subclass via a key and puts values into recordValues.
     * @param key String key used by Firebase to uniquely identify the data record.
     */
    public Task<DataSnapshot> getRecord(String key) {
        // TODO: Prevent reference-level get requests
        DatabaseReference databaseReference = getReference();
        return databaseReference.child(key).get();
    }

    /**
     * Sends a setValue request to Firebase, adding a record of the object to the realtime database.
     * @return Task describing the outcome of the push request.
     */
    public Task<Void> setRecord() {
        return setRecord(getReference().push().getKey());
    }

    /**
     * Sends a setValue request to Firebase, adding a record of the object with a custom key to the
     * realtime database.
     * If specified key already exists, the record will be overwritten.
     * @param key Custom key you want the record to be associated with
     * @return Task describing the outcome of the push request.
     */
    public Task<Void> setRecord(String key) {
        DatabaseReference databaseReference = getReference();
        recordKey = key;

        return databaseReference.child(key).setValue(localValues);
    }

    /**
     * Updates the data of an existing record in Firebase's realtime database.
     * @return Task describing the outcome of the update request.
     */
    public Task<Void> updateRecord() {
        if (recordKey == null) {
            throw new NullPointerException("Object has not been linked to a Firebase record");
        }

        DatabaseReference databaseReference = getReference();

        HashMap<String, Object> temporaryMap = new HashMap<>();
        temporaryMap.putAll(valueDelta);
        syncLocalValues();

        return databaseReference.child(recordKey).updateChildren(temporaryMap);
    }

    /**
     * Deletes the Firebase record associated with this object, and nulls the recordKey
     * @return Task describing the outcome of the delete request
     */
    public Task<Void> deleteRecord() {
        if (recordKey == null) {
            throw new NullPointerException("Object has not been linked to a Firebase record");
        }

        Task<Void> task = getReference().child(recordKey).removeValue();
        recordKey = null;

        return task;
    }

    /**
     * Gets a value from the locally stored record.
     * @param key String key used locally to uniquely identify the data record.
     * @return Value associated with the key
     */
    public Object getLocalValue(String key) {
        return localValues.get(key);
    }

    public Object getFirebaseValue(String key) {
        return localValues.get(key);
    }

    /**
     * Sets a value in the locally stored record.
     * @param key String key used locally to uniquely identify the data record.
     * @param value Value associated with the key
     */
    public void setLocalValue(String key, Object value) {
        localValues.put(key,value);
        valueDelta.put(key,value);
    }

    /**
     * Resets all changes made to the local record and keeps it up to date with the Firebase
     * record.
     */
    public void syncLocalValues() {
        localValues.clear();
        valueDelta.clear();
        localValues.putAll(firebaseValues);
    }

    /**
     * Returns a string representation of the maps relevant to this object.
     * @return
     */
    public String toString() {
        return "Reference: " + getReference() +
                "\n\nKey: " + getKey() +
                "\n\nLocal values:\n" + localValues.toString() +
                "\n\nFirebase values:\n" + firebaseValues.toString() +
                "\n\nDelta:\n" + valueDelta.toString();
    }
}
