package edu.gatech.rattracker;

/*
 * Map Fragment
 * Created by matthewkaufer on 10/25/17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

//public class MapFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
public class MapFragment extends Fragment implements OnMapReadyCallback{

    private Report report;
    private DatePicker startDate, endDate;

//    public static MapFragment newInstance() {
//
//        Bundle args = new Bundle();
//
//        MapFragment fragment = new MapFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        startDate = rootView.findViewById(R.id.startDate);
        endDate = rootView.findViewById(R.id.endDate);

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);


        startDate.updateDate(year - 1, month, day);
        endDate.updateDate(year, month, day);

        startDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                onMapReady(mMap);
            }
        });

        endDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                onMapReady(mMap);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null) {
            return;
        }

        report = new Report();
        mMap = googleMap;
        mMap.clear();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Context context = getActivity().getApplicationContext();
                Intent reportDetail = new Intent(context, IndividualReportActivity.class);
                reportDetail.putExtra("sighting", report.getSightingByKey(marker.getTitle()));
                context.startActivity(reportDetail);

                return true;
            }
        });

        FirebaseManager firebaseManager = FirebaseManager.getInstance();
        DatabaseReference reportListener = firebaseManager.reportListener();

        long dateStart = (new GregorianCalendar(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth())).getTimeInMillis();
        long dateEnd = (new GregorianCalendar(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth())).getTimeInMillis() + 86400;
        Log.d("dates", dateStart + "; " + dateEnd);
//        Query query = reportListener.startAt(dateStart, "date").endAt(dateEnd, "date").limitToLast(100);
        Query query = reportListener.orderByChild("date").startAt(dateStart).endAt(dateEnd).limitToLast(100);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Sighting> sightings = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    try {
                        Sighting sighting = postSnapshot.getValue(Sighting.class);
                        LatLng position = new LatLng(sighting.getLatitude(), sighting.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(position).title(sighting.getKey()).snippet(sighting.getReformedDate()));
                        sightings.add(sighting);
                    } catch (Exception e) {
                        Log.d("MapFragment", "On data change error");
                    }
                }

                report.setSighting(sightings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}