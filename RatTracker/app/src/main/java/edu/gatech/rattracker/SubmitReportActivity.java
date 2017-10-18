package edu.gatech.rattracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * @author Jiseok Choi
 * @version 1.0
 */

public class SubmitReportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);

        final String logTag = "SubmitReportActivity";

        // fetch text inputs, coordinates, and buttons
        final EditText locationType = (EditText) findViewById(R.id.typeInput);
        final EditText address1 = (EditText) findViewById(R.id.address1Input);
        final EditText address2 = (EditText) findViewById(R.id.address2Input);
        final EditText longitudeText = (EditText) findViewById(R.id.longitudeInput);
        final EditText latitudeText = (EditText) findViewById(R.id.latitudeInput);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // return to recycler view screen on back
                Intent report = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(report);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // TODO: handle no data
                Calendar c = Calendar.getInstance();
                final long date = c.getTimeInMillis();
                final String type = Uri.encode(locationType.getText().toString().trim());
                final String address = address1.getText().toString().trim();
                String address2Text = address2.getText().toString().trim();
                final String borough = address2Text.split(",")[0].trim();
                final String city = "New York City"; // TODO: add logic for city that is not NYC
                final short zip = Short.parseShort(address2Text.substring(address2Text.length() - 5));
                final double longitude = Double.parseDouble(longitudeText.getText().toString()); // TODO: negative coordinates
                final double latitude = Double.parseDouble(latitudeText.getText().toString());
                final Sighting sighting = new Sighting("", date, type, zip, address, city, borough, longitude, latitude);

                // Grabs FirebaseManager
                final FirebaseManager firebaseManager = FirebaseManager.getInstance();
                DatabaseReference reportListener = firebaseManager.reportListener();
                Log.d(logTag, "Report submission by verified user with ID "); // TODO: improve log text

                reportListener.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(logTag, "obtaining dataSnapshot");
                        boolean success = firebaseManager.addSighting(sighting);
                        if (success) {
                            Intent report = new Intent(getApplicationContext(), ReportActivity.class);
                            startActivity(report);
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to submit report", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Submission failed", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}
