package edu.gatech.rattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final String logTag = "LoginActivity";

        //fetch text input, button
        final EditText usernameButton = (EditText) findViewById(R.id.usernameInput);
        final EditText passwordButton = (EditText) findViewById(R.id.passwordInput);

        Button loginButton = (Button) findViewById(R.id.registerButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //return to welcome screen on back
                User.clearUser();
                Intent welcome = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(welcome);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // login logic

                //testing logic
                Intent report = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(report);

                boolean validData = BackendManager.validateUserPassword(usernameButton.getText().toString(), passwordButton.getText().toString(), getApplicationContext());
                if (!validData) {
                    return;
                }

                final String username = usernameButton.getText().toString().trim();
                final String password = passwordButton.getText().toString();

                //Grabs FirebaseManager
                FirebaseManager firebaseManager = FirebaseManager.getInstance();
                Log.d(logTag, "Verified user with ID HELP ");

                DatabaseReference authenticator = firebaseManager.authenticateListener(username);

                authenticator.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //authenticates username and password in database
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null && username.equals(user.name) && password.equals(user.password)) {
                            User.setUser(user);
                            Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
                            Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(profile);
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}
