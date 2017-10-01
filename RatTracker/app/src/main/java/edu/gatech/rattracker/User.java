package edu.gatech.rattracker;

/**
 * Created by Chunlok Lo on 10/1/2017.
 */

public class User {
    String name;
    String password;
    Boolean isAdmin;

    public User(String name, String password, Boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User() {
        name = "";
        password = "";
        isAdmin = false;
    }

    public static User currentUser = new User();
    public static User getCurrentUser() {
        return currentUser;
    }
}