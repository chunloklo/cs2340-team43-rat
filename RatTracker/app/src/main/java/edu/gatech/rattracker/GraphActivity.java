package edu.gatech.rattracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Matthew Wang
 * @version 1.0
 */
public class GraphActivity extends Fragment {
    private DatePicker startDate, endDate;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private GraphView graph;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_graph, container, false);

        spinner = (Spinner) rootView.findViewById(R.id.month_or_year_spinner);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.month_or_year, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onGraphReady((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        startDate = (DatePicker) rootView.findViewById(R.id.startDate);
        endDate = (DatePicker) rootView.findViewById(R.id.endDate);


        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);


        startDate.updateDate(year - 1, month, day);
        endDate.updateDate(year, month, day);


        startDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                onGraphReady((String) spinner.getSelectedItem());
            }
        });

        endDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                onGraphReady((String) spinner.getSelectedItem());
            }
        });

        graph = (GraphView) rootView.findViewById(R.id.graph);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();

        graph.setTitle("Rat Sightings Over Time");
        gridLabel.setVerticalAxisTitle("Number of Sightings");
        gridLabel.setHorizontalAxisTitle("Date");

        //FILLING THE GRAPH WITH NONSENSE DATA
        // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        // you can directly pass Date objects to DataPoint-Constructor
        // this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 1),
                new DataPoint(d2, 5),
                new DataPoint(d3, 3)
        });

        graph.addSeries(series);

        // set date label formatter

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

        return rootView;
    }

    public void onGraphReady(String monthOrYear) {
        Toast.makeText(getContext(), monthOrYear, Toast.LENGTH_SHORT).show();

        FirebaseManager firebaseManager = FirebaseManager.getInstance();
        DatabaseReference reportListener = firebaseManager.reportListener();

        long dateStart = (new GregorianCalendar(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth())).getTimeInMillis();
        long dateEnd = (new GregorianCalendar(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth())).getTimeInMillis() + 86400;

        Query query = reportListener.orderByChild("date").startAt(dateStart).endAt(dateEnd);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Sighting> sightings = new ArrayList<Sighting>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    try {
                        Sighting sighting = postSnapshot.getValue(Sighting.class);
                        sightings.add(sighting);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

}
