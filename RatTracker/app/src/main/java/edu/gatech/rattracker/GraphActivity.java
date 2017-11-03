package edu.gatech.rattracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * @author Matthew Wang
 * @version 1.0
 */
public class GraphActivity extends Fragment {
    private DatePicker startDate, endDate;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private GraphView graph;

    private class YearMonthFormatter extends DefaultLabelFormatter {
        @Override
        public String formatLabel(double value, boolean isValueX) {
            String month;
            String year;
            if (isValueX) {
                switch ((int)value % 100) {
                    case 0:
                        month = "Jan";
                        break;
                    case 1:
                        month = "Feb";
                        break;
                    case 2:
                        month = "Mar";
                        break;
                    case 3:
                        month = "Apr";
                        break;
                    case 4:
                        month = "May";
                        break;
                    case 5:
                        month = "Jun";
                        break;
                    case 6:
                        month = "Jul";
                        break;
                    case 7:
                        month = "Aug";
                        break;
                    case 8:
                        month = "Sep";
                        break;
                    case 9:
                        month = "Oct";
                        break;
                    case 10:
                        month = "Nov";
                        break;
                    case 11:
                        month = "Dec";
                        break;
                    default:
                        month = "N/A";
                }
                year = Integer.toString((int)value / 100);
                return month + " " + year;
            } else {
                return super.formatLabel(value, isValueX);
            }
        }
    }

    private class YearFormatter extends DefaultLabelFormatter {
        @Override
        public String formatLabel(double value, boolean isValueX) {
            String year;
            if (isValueX) {
                return Integer.toString((int)value);
            } else {
                return super.formatLabel(value, isValueX);
            }
        }
    }
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
        return rootView;
    }

    public void onGraphReady(final String monthOrYear) {
        Toast.makeText(getContext(), monthOrYear, Toast.LENGTH_SHORT).show();

        FirebaseManager firebaseManager = FirebaseManager.getInstance();
        DatabaseReference reportListener = firebaseManager.reportListener();

        long dateStart = (new GregorianCalendar(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth())).getTimeInMillis();
        long dateEnd = (new GregorianCalendar(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth())).getTimeInMillis() + 86400;

        Query query = reportListener.orderByChild("date").startAt(dateStart).endAt(dateEnd);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                graph.removeAllSeries();
                ArrayList<Sighting> sightings = new ArrayList<Sighting>();
                //ArrayList<Date> dates = new ArrayList<Date>();
                //ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();
                Calendar cal = Calendar.getInstance();
                Date date;
                int day;
                int month;
                int year;
                int yearMonth;
                long time;
                HashMap<Integer, Integer> frequencies = new HashMap<Integer, Integer>();
                HashMap<Long, Integer> frequencies2 = new HashMap<Long, Integer>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    try {
                        Sighting sighting = postSnapshot.getValue(Sighting.class);
                        sightings.add(sighting);
                    } catch (Exception e) {
                    }
                }

                for (Sighting sighting : sightings) {
                    date = new Date(sighting.getDate());

                    cal.setTime(date);

                    day = cal.get(Calendar.DAY_OF_MONTH);
                    month = cal.get(Calendar.MONTH);
                    year = cal.get(Calendar.YEAR);
                    /*
                    cal.set(year, month, day, 0 ,0 ,0);
                    cal.setTimeInMillis(cal.getTimeInMillis() / 1000 * 1000);
                    time = cal.getTimeInMillis();
                    */
                    yearMonth = year * 100 + month;
                    if (frequencies.containsKey(yearMonth)) {
                        frequencies.put(yearMonth, frequencies.get(yearMonth) + 1);
                    } else {
                        frequencies.put(yearMonth, 1);
                    }
                    /*
                    if (frequencies2.containsKey(time)) {
                        frequencies2.put(time, frequencies2.get(time) + 1);
                    } else {
                        frequencies2.put(time, 1);
                    } */
                }

                ArrayList<Integer> yearMonths = new ArrayList<Integer>(frequencies.keySet());
                Collections.sort(yearMonths);
                DataPoint[] dataPoints = new DataPoint[yearMonths.size()];
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(yearMonths.get(i), frequencies.get(yearMonths.get(i)));
                }
                /*
                ArrayList<Long> times = new ArrayList<Long>(frequencies2.keySet());
                Collections.sort(times);
                for (int i = 0; i < times.size(); i++) {
                    Log.e("graph", Long.toString(times.get(i)));
                    Log.e("graph", Long.toString(frequencies2.get(times.get(i))));
                } */
                for (int i = 0; i < yearMonths.size(); i++) {
                    Log.e("graph", Long.toString(yearMonths.get(i)));
                    Log.e("graph", Long.toString(frequencies.get(yearMonths.get(i))));
                }
                if (!yearMonths.isEmpty()) {
                    /*
                    DataPoint[] dataPoints = new DataPoint[times.size()];
                    for (int i = 0; i < dataPoints.length; i++) {
                        dataPoints[i] = new DataPoint(new Date(times.get(i)), frequencies2.get(times.get(i)));
                    } */
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

                    graph.getGridLabelRenderer().setLabelFormatter(new YearMonthFormatter());

                    graph.addSeries(series);

                    //graph.getGridLabelRenderer().setNumHorizontalLabels(3);
                    //graph.getGridLabelRenderer().setNumVerticalLabels(10);
                    graph.getViewport().setMinX(yearMonths.get(0));
                    graph.getViewport().setMaxX(yearMonths.get(yearMonths.size() - 1) + 1);
                    //graph.getViewport().setMaxX(201711);
                    //graph.getViewport().setMaxY(20);
                    //graph.getViewport().setXAxisBoundsManual(true);
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
