package edu.gatech.rattracker;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by matthewkaufer on 9/24/17.
 */

public class BackendManager {
    public final static String backendURL = "http://10.0.2.2:3000/";
    public final static String loginURL = backendURL + "api/login/";
    public final static String registerURL = backendURL + "api/register/";
    private final static String logTag = "BackendManager";
    private final static int passLength = 6;

    private static String userToken, username = null;

    public static void setUserToken(String newToken) {
        userToken = newToken;
    }
    public static String getUserToken() {
        return userToken;
    }

    public static void setUsername(String newUsername) {
        username = newUsername;
    }
    public static String getUsername() {
        return username;
    }

    public static String generateLoginURL(String user, String pass) {
        return BackendManager.loginURL + encodeURIComponent(user) + "/" + encodeURIComponent(pass) + "/";
    }

    public static String generateRegistrationURL(String user, String pass) {
        return BackendManager.registerURL + encodeURIComponent(user) + "/" + encodeURIComponent(pass) + "/";
    }

    public static String encodeURIComponent(String s) {
        // this code block borrowed from https://stackoverflow.com/questions/14321873/java-url-encoding-urlencoder-vs-uri
        String result;

        try {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

    public static String getStringFromInputStream(InputStream is) {
        // code borrowed from https://www.mkyong.com/java/how-to-convert-inputstream-to-string-in-java/
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public static boolean validateUserPassword(String username, String password, Context context) {
        if (username.trim().length() == 0) {
            Toast.makeText(context, "Please enter an alphanumeric username", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < passLength) {
            Toast.makeText(context, "You need a password longer than " + passLength + " characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static void logOut() {
        setUserToken(null);
        setUsername(null);
    }
}
