package edu.gatech.rattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    //profile activity screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView welcomeText = (TextView) findViewById(R.id.helloText);
        Button logOut = (Button) findViewById(R.id.logOut);


        if (User.getCurrentUser() == null || User.getCurrentUser().name == null) {
            // if user isn't logged in, send them back to welcome screen
            Intent welcome = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(welcome);
        }

        welcomeText.setText("Welcome, " + User.getCurrentUser().name + "Admin: " + User.getCurrentUser().isAdmin);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sends user back to welcome screen on logout
                Toast.makeText(getApplicationContext(), "Bye " + User.getCurrentUser().name, Toast.LENGTH_SHORT).show();
                User.clearUser();
                Intent welcome = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(welcome);
            }
        });
    }

}
