package edu.gatech.rattracker;

import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    //writes value as user to database
    public User writeNewUser(String name, String password, Boolean isAdmin) {
        DatabaseReference myRef = database.getReference("Users");
        User user = new User(name, password, isAdmin);
        myRef.child(name).setValue(user);
        return user;
    }

    public boolean addSighting(Sighting sighting) {
        DatabaseReference myRef = database.getReference("Sightings").push();
        String key = myRef.getKey();
        sighting.setKey(key);
        myRef.setValue(sighting);
        return true;
    }

    //Returns references to a given username in Users
    public DatabaseReference authenticateListener(String username) {
        DatabaseReference myRef = database.getReference("Users").child(username);
        return myRef;
    }

    public DatabaseReference reportListener() {
        DatabaseReference myRef = database.getReference("").child("Sightings");
        return myRef;
    }

}
