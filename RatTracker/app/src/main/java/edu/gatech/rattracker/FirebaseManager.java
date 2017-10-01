package edu.gatech.rattracker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Chunlok Lo on 10/1/2017.
 */

public class FirebaseManager {
    //Interfaces with Firebase. Provide Firebase references and uploads data

    //singleton design
    private static FirebaseManager firebaseManager = new FirebaseManager();

    public static FirebaseManager getInstance() {
        return firebaseManager;
    }

    private FirebaseDatabase database;

    public FirebaseManager(){
        database = FirebaseDatabase.getInstance();

    }

    //writes value as uesr to database
    public User writeNewUser(String name, String password, Boolean isAdmin) {
        DatabaseReference myRef = database.getReference("Users");
        User user = new User(name, password, isAdmin);
        myRef.child(name).setValue(user);
        return user;
    }

    //Returns references to a given username in Users
    public DatabaseReference authenticateListener(String username) {
        DatabaseReference myRef = database.getReference("Users").child(username);
        return myRef;
    }

}
