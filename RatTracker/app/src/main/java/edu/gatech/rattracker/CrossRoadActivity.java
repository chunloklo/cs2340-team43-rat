package edu.gatech.rattracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class CrossRoadActivity extends AppCompatActivity {

//    private TextView mTextMessage;
    private ViewPager viewPager;
    private final String logTag = "crossroadActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    Log.d(logTag, "" + R.id.navigation_dashboard);
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;

                case R.id.navigation_graphs:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Team 43 Rat Tracker");
        setContentView(R.layout.activity_cross_road);
        viewPager = (ViewPager) findViewById(R.id.pager);
        CrossRoadAdapter mAdapter = new CrossRoadAdapter(getSupportFragmentManager());

        Fragment[] fragments = new Fragment[4];
        fragments[0] = new ReportActivity();
        fragments[1] = new SubmitReportActivity();
        fragments[2] = new MapFragment();
        fragments[3] = new GraphActivity();

        mAdapter.setFragment(fragments);
        viewPager.setAdapter(mAdapter);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_report, menu);
        return true;
    }

    class CrossRoadAdapter extends FragmentPagerAdapter {
        Fragment[] fragments;

        public CrossRoadAdapter(FragmentManager fm) {
            super(fm);

        }
        public Fragment getItem(int i) {
           return fragments[i];
        }

        public int getCount() {
            return fragments.length;
        }

        public void setFragment(Fragment[] fragments) {
            this.fragments = fragments;
        }
    }

}
