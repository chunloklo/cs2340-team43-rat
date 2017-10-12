package edu.gatech.rattracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private RecyclerView reportView;
    private RecyclerView.LayoutManager reportLayoutManager;
    private RecyclerView.Adapter reportAdapter;
    final String logTag = "reportActivity";
    private Report report = new Report();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        reportView = (RecyclerView) findViewById(R.id.reportView);
        reportView.setHasFixedSize(true);
        reportLayoutManager = new LinearLayoutManager(this);
        reportView.setLayoutManager(reportLayoutManager);
        reportAdapter = new ReportAdapter(report);
        reportView.setAdapter(reportAdapter);

        FirebaseManager firebaseManager = FirebaseManager.getInstance();
        DatabaseReference reportListener = firebaseManager.reportListener();

        Log.d(logTag, "Creating query");
//        Query query = reportListener.orderByChild("date").limitToFirst(100);
        Query query = reportListener.limitToLast(100);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(logTag, "obtaining dataSnapshot");
                ArrayList<Sighting> sightings = new ArrayList<Sighting>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //TODO: PLEASE FIX DATABASE SO THIS DOESNT HAVE TO BE IN A TRYCATCH BLOCK
                    try {
                        Sighting sighting = postSnapshot.getValue(Sighting.class);
                        sightings.add(sighting);
                    } catch (Exception e) {
                        Log.d(logTag, "sighting conversion failed");
                    }
                }
                report.setSighting(sightings);
                reportView.scrollToPosition(0);
                reportAdapter.notifyDataSetChanged();
                Log.d(logTag, "Finished obtaining dataSnapshot");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}



