package edu.gatech.rattracker;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final String logTag = "LoginActivity";

        Log.d(logTag, "test");

        final EditText usernameButton = (EditText) findViewById(R.id.usernameInput);
        final EditText passwordButton = (EditText) findViewById(R.id.passwordInput);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean validData = BackendManager.validateUserPassword(usernameButton.getText().toString(), passwordButton.getText().toString(), getApplicationContext());
                final String username = usernameButton.getText().toString();
                final String password = passwordButton.getText().toString();

                if (!validData) {
                    return;
                }

                new AsyncTask<Void, String, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        try {
                            URL url = new URL(BackendManager.generateRegistrationURL(username, password));
                            try {
                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                                urlConnection.setRequestMethod("GET");
                                urlConnection.setConnectTimeout(5000);
                                urlConnection.setReadTimeout(5000);
                                urlConnection.setRequestProperty("user-agent",  "Android");
                                urlConnection.setRequestProperty("Content-Type", "application/x-zip");


                                urlConnection.connect();
                                int statusCode = urlConnection.getResponseCode();
                                if (statusCode != 200) {
                                    // bad stuff happened
                                    Log.d(logTag, "CRITICAL: Login failed, response code " + statusCode);
                                    return "";
                                } else {
                                    try {
                                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                                        String result = BackendManager.getStringFromInputStream(in);
                                        Log.d(logTag, "Verified user with ID " + result);
                                        BackendManager.setUserToken(result);
                                        BackendManager.setUsername(username);
                                        urlConnection.disconnect();
                                        return "Welcome " + username;
                                    } finally {

                                    }
                                }
                            } catch (IOException ioe) {
                                Log.d(logTag, "Bad IO: " + ioe);
                            }

                        } catch (MalformedURLException mue) {
                            Log.d(logTag, "Malformed URL: " + mue);
                        }
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        if (result != "") {
                            Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();




            }
        });

    }
}
