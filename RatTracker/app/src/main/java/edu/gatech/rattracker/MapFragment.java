package edu.gatech.rattracker;

/**
 * Created by matthewkaufer on 10/25/17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
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

//public class MapFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
public class MapFragment extends Fragment implements OnMapReadyCallback{

    private Report report;

    public static MapFragment newInstance() {

        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private GoogleMap mMap;

//    private GoogleApiClient mGoogleApiClient;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, null);
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

        Query query = reportListener.limitToLast(100);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Sighting> sightings = new ArrayList<Sighting>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    try {
                        Sighting sighting = postSnapshot.getValue(Sighting.class);
                        LatLng position = new LatLng(sighting.getLatitude(), sighting.getLongitude());
//                        Log.d("hello", sighting.toShortString());
                        Marker m = mMap.addMarker(new MarkerOptions().position(position).title(sighting.getKey()).snippet(sighting.getReformedDate()));
                        sightings.add(sighting);
                    } catch (Exception e) {
                    }
                }

                report.setSighting(sightings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        // Get location
//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build();
//
//            mGoogleApiClient.connect();
//        }
    }

    @Override
    public void onStart() {
//        if (mGoogleApiClient != null)
//            mGoogleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
//        if (mGoogleApiClient != null)
//            mGoogleApiClient.disconnect();
        super.onStop();
    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
////        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            return;
////        }
//        // Add a marker in Sydney and move the camera
////        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
////        LatLng here = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
////        mMap.addMarker(new MarkerOptions().position(here).title("YOU ARE HERE").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
////        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 14));
//
//    }

//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }

}