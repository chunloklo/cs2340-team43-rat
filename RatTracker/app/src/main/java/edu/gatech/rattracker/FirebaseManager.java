package edu.gatech.rattracker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Firebase Manager
 * Created by Chunlok Lo on 10/1/2017.
 */

public class FirebaseManager {
    //Interfaces with Firebase. Provide Firebase references and uploads data

    //singleton design
    private final static FirebaseManager firebaseManager = new FirebaseManager();

    public static FirebaseManager getInstance() {
        return firebaseManager;
    }

    private final FirebaseDatabase database;

    private FirebaseManager(){
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
        try{
            DatabaseReference myRef = database.getReference("Sightings").push();
            String key = myRef.getKey();
            sighting.setKey(key);
            myRef.setValue(sighting);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    //Returns references to a given username in Users
    public DatabaseReference authenticateListener(String username) {
        return database.getReference("Users").child(username);
    }

    public DatabaseReference reportListener() {
        return database.getReference("").child("Sightings");
    }

}
