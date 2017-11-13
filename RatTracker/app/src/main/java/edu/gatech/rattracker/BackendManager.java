package edu.gatech.rattracker;

import android.content.Context;
import android.widget.Toast;


//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;

/**
 * BackendManager
 * Created by matthewkaufer on 9/24/17.
 */

class BackendManager {
    private final static String logTag = "BackendManager";
    private final static int passLength = 6;


//    public static String encodeURIComponent(String s) {
//        // this code block borrowed from https://stackoverflow.com/questions/14321873/java-url-encoding-urlencoder-vs-uri
//        String result;
//
//        try {
//            result = URLEncoder.encode(s, "UTF-8")
//                    .replaceAll("\\+", "%20")
//                    .replaceAll("\\%21", "!")
//                    .replaceAll("\\%27", "'")
//                    .replaceAll("\\%28", "(")
//                    .replaceAll("\\%29", ")")
//                    .replaceAll("\\%7E", "~");
//        } catch (UnsupportedEncodingException e) {
//            result = s;
//        }
//
//        return result;
//    }

    public static boolean validateUserPassword(String username, String password, Context context) {
        if (username.trim().length() == 0) {
            if (context != null) {
                Toast.makeText(context, "Please enter an alphanumeric username", Toast.LENGTH_SHORT).show();
            }

            return false;
        }

        if (password.length() < passLength) {
            if (context != null) {
                Toast.makeText(context, "You need a password longer than " + passLength + " characters", Toast.LENGTH_SHORT).show();
            }

            return false;
        }

        return true;
    }
//
//    public static void logOut() {
//        User.clearUser();
//    }
}
