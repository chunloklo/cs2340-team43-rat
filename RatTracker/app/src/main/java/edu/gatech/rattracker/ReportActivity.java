package edu.gatech.rattracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ReportActivity extends AppCompatActivity {

    private RecyclerView reportView;
    private RecyclerView.LayoutManager reportLayoutManager;
    private RecyclerView.Adapter reportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        reportView = (RecyclerView) findViewById(R.id.reportView);
        reportView.setHasFixedSize(true);
        reportLayoutManager = new LinearLayoutManager(this);
        reportView.setLayoutManager(reportLayoutManager);
        String[] dataSet = {"a", "b", "c", "d", "e", "f"};
        reportAdapter = new ReportAdapter(dataSet);
        reportView.setAdapter(reportAdapter);

    }


}



