package edu.gatech.rattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView welcomeText = (TextView) findViewById(R.id.helloText);
        Button logOut = (Button) findViewById(R.id.logOut);

        if (BackendManager.getUsername() == null) {
            // if user isn't logged in, send them back to welcome screen
            Intent welcome = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(welcome);
        }

        welcomeText.setText("Welcome, " + BackendManager.getUsername());

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Bye " + BackendManager.getUsername(), Toast.LENGTH_SHORT).show();
                BackendManager.logOut();
                Intent welcome = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(welcome);
            }
        });
    }

}
