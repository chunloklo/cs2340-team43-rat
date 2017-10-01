package edu.gatech.rattracker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Chunlok Lo on 10/1/2017.
 */

public class FirebaseManager {

    private FirebaseDatabase database;

    public FirebaseManager(){
        database = FirebaseDatabase.getInstance();

    }

    public void writeNewUser(String name, String password, Boolean isAdmin) {
        DatabaseReference myRef = database.getReference("Users");
        myRef.setValue("Hello, World!");
    }

    private static FirebaseManager instance = null;

    public static FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

}
