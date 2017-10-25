package edu.gatech.rattracker;

import android.content.Intent;
import android.os.UserManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DummyActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_dummy, container, false);
        super.onCreate(savedInstanceState);


        Button logout = (Button)rootView.findViewById(R.id.logOut);
        Button openMap = (Button)rootView.findViewById(R.id.mapButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.clearUser();
                Intent home = new Intent(getActivity().getApplicationContext(), WelcomeActivity.class);
                startActivity(home);
            }
        });

        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(getActivity().getApplicationContext(), MapsActivity.class);
                startActivity(map);
            }
        });

        return rootView;
    }
}
