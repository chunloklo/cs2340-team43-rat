package edu.gatech.rattracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    //landing screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Context thisContext = this;

        Button loginButton = findViewById(R.id.welcomeLoginButton);
        Button registerButton = findViewById(R.id.welcomeRegisterButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when user clicks login
                Intent intent = new Intent(thisContext, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when user clicks register
                Intent intent = new Intent(thisContext, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
