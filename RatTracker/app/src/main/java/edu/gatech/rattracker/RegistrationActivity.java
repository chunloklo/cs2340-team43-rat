package edu.gatech.rattracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final String logTag = "RegistrationActivity";

        final EditText usernameButton = (EditText) findViewById(R.id.usernameInput);
        final EditText passwordButton = (EditText) findViewById(R.id.passwordInput);
        final CheckBox checkbox = (CheckBox) findViewById(R.id.isAdminCheckBox);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        Button backButton = (Button) findViewById(R.id.backButton);



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent welcome = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(welcome);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseManager firebaseManager = FirebaseManager.getInstance();
                firebaseManager.write();

                boolean validData = BackendManager.validateUserPassword(usernameButton.getText().toString(), passwordButton.getText().toString(), getApplicationContext());
                final String username = usernameButton.getText().toString();
                final String password = passwordButton.getText().toString();
                final Boolean isAdmin = checkbox.isChecked();


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
                                    Log.d(logTag, "CRITICAL: Registration failed, response code " + statusCode);
                                    return "";
                                } else {
                                    try {
                                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                                        String result = BackendManager.getStringFromInputStream(in);
                                        Log.d(logTag, "Verified user with ID " + result);
                                        BackendManager.handleAuthResult(result);
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
                        if (!result.equals("")) {
                            Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
                            Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(profile);
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();




            }
        });
    }
}
