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

    private String recordKey;

    /** Map of key-value pairs containing all data to be sent to firebase. **/
    private HashMap<String, Object> recordValues = new HashMap<>();

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
    public DatabaseReference getDatabaseReference() {
        return databaseReferences.get(this.getClass().getSimpleName());
    }

    /**
     *
     * @return The key for the DatabaseObject
     */
    public String getRecordKey(){
        return recordKey;
    }

    /**
     * Builds the DatabaseObject from a DataSnapshot
     * @param snapshot Datasnapshot containing the record
     */
    protected void buildFromSnapshot(DataSnapshot snapshot) {
        recordKey = snapshot.getKey();

        for (DataSnapshot data: snapshot.getChildren()) {
            setValue(data.getKey(), data.getValue());
        }
    }

    /**
     * Retrieves a record related to the subclass via a key and puts values into recordValues.
     * @param key String key used by Firebase to uniquely identify the data record.
     */
    public void getRecord(String key) {
        // TODO: Prevent reference-level get requests
        DatabaseReference databaseReference = getDatabaseReference();
        databaseReference.child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("Firebase", "Error getting data", task.getException());
                }
                else {
                    buildFromSnapshot(task.getResult());
                }
            }
        });
    }

    /**
     * Sends a push request to Firebase, adding a record of the object to the realtime database.
     * @return Task describing the outcome of the push request.
     */
    public Task<Void> setRecord() {
        DatabaseReference databaseReference = getDatabaseReference();
        recordKey = databaseReference.push().getKey();

        return databaseReference.child(recordKey).setValue(recordValues);
    }

    /**
     * Sends a push request to Firebase, adding a record of the object with a custom key to the realtime database.
     * If specified key already exists, the record will be overwritten.
     * @param key Custom key you want the record to be associated with
     * @return Task describing the outcome of the push request.
     */
    public Task<Void> setRecord(String key) {
        DatabaseReference databaseReference = getDatabaseReference();
        recordKey = key;

        return databaseReference.child(recordKey).setValue(recordValues);
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

    /**
     *
     * @param key String key used locally to uniquely identify the data record.
     * @return Value associated with the key
     */
    public Object getValue(String key) {
        return recordValues.get(key);
    }

    /**
     *
     * @param key String key used locally to uniquely identify the data record.
     * @param value Value associated with the key
     */
    public void setValue(String key, Object value) {
        recordValues.put(key,value);
    }
}
