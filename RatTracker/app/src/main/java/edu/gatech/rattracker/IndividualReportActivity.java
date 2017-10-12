package edu.gatech.rattracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by alan on 10/10/17.
 * Represents the activity when an individual report is pressed. Shows details of that report
 */

public class IndividualReportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_report);

        final String logTag = "individual_report activity";

        Sighting sighting = (Sighting) this.getIntent().getSerializableExtra("sighting");
        Button returnButton = (Button) findViewById(R.id.returnButton);
        TextView reportInfo = (TextView) findViewById(R.id.reportDetails);

        reportInfo.setText(
                "Report Details: \n"
                + "Key: " + sighting.getKey() + "\n"
                + "Creation Date: " + sighting.getReformedDate() + "\n"
                + "Location Type: " + sighting.getReformedLocationType() + "\n"
                + "Zip Code: " + sighting.getZip() + "\n"
                + "Address: " + sighting.getAddress() + "\n"
                + "City: " + sighting.getCity() + "\n"
                + "Borough: " + sighting.getBorough() + "\n"
                + "Latitude: " + sighting.getLatitude() + "\n"
                + "Longitude: " + sighting.getLongitude()
        );
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent report = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(report);
            }
        });
    }
}
