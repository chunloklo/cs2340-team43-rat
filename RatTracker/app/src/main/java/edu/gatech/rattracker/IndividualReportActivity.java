package edu.gatech.rattracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

        Sighting sighting = (Sighting) this.getIntent().getSerializableExtra("sighting");
        Button returnButton = findViewById(R.id.returnButton);
        TextView reportInfo = findViewById(R.id.reportDetails);

        reportInfo.setText(
                sighting.toString()
        );
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
