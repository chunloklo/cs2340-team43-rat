package edu.gatech.rattracker;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Chunlok Lo on 10/1/2017.
 */

public class FirebaseManager {

    private static FirebaseManager firebaseManager = new FirebaseManager();

    public static FirebaseManager getInstance() {
        return firebaseManager;
    }

    private FirebaseDatabase database;

    public FirebaseManager(){
        database = FirebaseDatabase.getInstance();

    }

    public void writeNewUser(String name, String password, Boolean isAdmin) {
        DatabaseReference myRef = database.getReference("Users");
        User user = new User(name, password, isAdmin);
        myRef.child(name).setValue(user);
    }

    public DatabaseReference authenticateListener(String username) {
        DatabaseReference myRef = database.getReference("Users").child(username);
        return myRef;
    }

}
