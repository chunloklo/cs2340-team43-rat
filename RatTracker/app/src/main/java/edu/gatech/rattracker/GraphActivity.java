package edu.gatech.rattracker;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_graph, container, false);

        graph = (GraphView) rootView.findViewById(R.id.graph);
        graph.setTitle("Rat Sightings Over Time");
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("Number of Sightings");
        gridLabel.setHorizontalAxisTitle("Date");

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
                Log.e("graph", "Nothing selected bug on spinner.")
            }
        });

        startDate = (DatePicker) rootView.findViewById(R.id.startDate);
        endDate = (DatePicker) rootView.findViewById(R.id.endDate);

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        //Sets default end date to the current day and the default start date to a month before.
        startDate.updateDate(year, month - 1, day);
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
        return rootView;
    }

    public void onGraphReady(final String monthOrYear) {
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
                Calendar cal = Calendar.getInstance();
                Date date;
                int month;
                int year;
                int unixMonth;
                HashMap<Integer, Integer> frequencies = new HashMap<Integer, Integer>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    try {
                        Sighting sighting = postSnapshot.getValue(Sighting.class);
                        sightings.add(sighting);
                    } catch (Exception e) {
                        Log.e("graph", "Bad sighting");
                    }
                }

                for (Sighting sighting : sightings) {
                    date = new Date(sighting.getDate());
                    cal.setTime(date);
                    month = cal.get(Calendar.MONTH);
                    year = cal.get(Calendar.YEAR);

                    if (monthOrYear.equals("Month")) {
                        /* creates an int whose first 4 digits are the year and the last 2 are the month (0-11)
                         of the sighting used to calculate the "Unix month" in the calculateUnixMonth() helper method.
                         */
                        unixMonth = calculateUnixTimeInMonths(month, year);

                        //Creates hashmap mapping x values (dates) in graph to y values (frequencies).
                        if (frequencies.containsKey(unixMonth)) {
                            frequencies.put(unixMonth, frequencies.get(unixMonth) + 1);
                        } else {
                            frequencies.put(unixMonth, 1);
                        }
                    } else {
                        if (frequencies.containsKey(year)) {
                            frequencies.put(year, frequencies.get(year) + 1);
                        } else {
                            frequencies.put(year, 1);
                        }
                    }
                }

                ArrayList<Integer> dates = new ArrayList<Integer>(frequencies.keySet());
                Collections.sort(dates);
                //Creates an zeroed data point if there's only 1 data point because GraphView can't show a single data point.
                if (dates.size() == 1) {
                    dates.add(dates.get(0) + 1);
                    frequencies.put(dates.get(1), 0);
                }
                DataPoint[] dataPoints = new DataPoint[frequencies.size()];
                for (int i = 0; i < dataPoints.length; i++) {
                    dataPoints[i] = new DataPoint(dates.get(i), frequencies.get(dates.get(i)));
                }
                if (!dates.isEmpty()) {
                    BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
                    //Selects formatter for graph depending on if sightings are sorted by month or year.
                    if (monthOrYear.equals("Month")) {
                        graph.getGridLabelRenderer().setLabelFormatter(new MonthFormatter());
                    } else {
                        graph.getGridLabelRenderer().setLabelFormatter(new YearFormatter());
                    }
                    graph.addSeries(series);

                    //Makes the graph look nice
                    graph.getViewport().setMinX(dates.get(0) - 1);
                    graph.getViewport().setMaxX(dates.get(dates.size() - 1) + 2);
                    graph.getViewport().setMinY(0);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("graph", "Database error occurred.");
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

    /**
     * Calculates the number of months a specific month is from the beginning of the year 1970
     * with Jan 1970 taking the value 1.
     *
     * @param month the month as an int (0-11)
     * @param year the year as a 4 digit int
     * @return the number of months since the beginning of 1970
     */
    private int calculateUnixTimeInMonths(int month, int year) {
        int retval = month;
        retval += (year - 1970) * 12;
        return retval;
    }

    /**
     * Takes in a unixMonth value and returns the month as an int 0-11
     * @param unixMonth the unixMonth value to be converted
     * @return the month as an int 0-11
     */
    private int unixMonthToMonth(int unixMonth) {
        return unixMonth % 12;
    }

    /**
     * Takes in a unixMonth value and returns the year as a 4 digit int
     * @param unixMonth the unixMonth value to be converted
     * @return the year as a 4 digit int
     */
    private int unixMonthToYear(int unixMonth) {
        return 1970 + unixMonth / 12;
    }

    /**
     * Formats axes for sorting rat sightings by month.
     */
    private class MonthFormatter extends DefaultLabelFormatter {
        @Override
        public String formatLabel(double value, boolean isValueX) {
            String month;
            String[] months = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec"};
            String year;
            if (isValueX) {
                int unixMonth = (int) value;
                month = months[unixMonthToMonth(unixMonth)];
                year = Integer.toString(unixMonthToYear(unixMonth));
                return month + " " + year;
            } else {
                return super.formatLabel(value, isValueX);
            }
        }
    }

    /**
     * Formats axes for sorting rat sightings by year.
     */
    private class YearFormatter extends DefaultLabelFormatter {
        @Override
        public String formatLabel(double value, boolean isValueX) {
            String year;
            if (isValueX) {
                year = Integer.toString((int)value);
                return year;
            } else {
                return super.formatLabel(value, isValueX);
            }
        }
    }
}
