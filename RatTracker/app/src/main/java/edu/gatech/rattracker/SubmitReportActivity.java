package edu.gatech.rattracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
        final EditText addressText = (EditText) findViewById(R.id.addressInput);
        final EditText cityText = (EditText) findViewById(R.id.cityInput);
        final Spinner boroughText = (Spinner) findViewById(R.id.spinner);
        boroughText.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Borough.values()));

        final EditText zipText = (EditText) findViewById(R.id.zipInput);
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
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                final long date = c.getTimeInMillis();
                final String type = Uri.encode(locationType.getText().toString().trim());
                final String address = addressText.getText().toString().trim();
                final String borough = boroughText.getSelectedItem().toString();
                final String city = cityText.getText().toString();

                Long zip;
                double longitude;
                double latitude;
                try {
                    zip = Long.parseLong(zipText.getText().toString());
                    longitude = Double.parseDouble(longitudeText.getText().toString());
                    latitude = Double.parseDouble(latitudeText.getText().toString());

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error. Please enter valid numbers", Toast.LENGTH_SHORT).show();
                    Log.e(logTag, "Input conversion failed: " + e.getMessage());
                    return;
                }

                final Sighting sighting = new Sighting("NO KEY", date, type, zip, address, city, borough, longitude, latitude);

                // Grabs FirebaseManager
                final FirebaseManager firebaseManager = FirebaseManager.getInstance();
                boolean success = firebaseManager.addSighting(sighting);
                if (success) {
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    Intent report = new Intent(getApplicationContext(), ReportActivity.class);
                    startActivity(report);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to submit report", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
