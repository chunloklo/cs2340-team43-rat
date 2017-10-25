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
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    Log.d(logTag, "" + R.id.navigation_dashboard);
                    viewPager.setCurrentItem(1);
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
//                    mTextMessage.setText(R.string.title_notifications);
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
//        mTextMessage = (TextView) findViewById(R.id.message);
        viewPager = (ViewPager) findViewById(R.id.pager);
        CrossRoadAdapter mAdapter = new CrossRoadAdapter(getSupportFragmentManager());
        mAdapter.setFragment(new ReportActivity(), new SubmitReportActivity(), new MapFragment());
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
        Fragment report;
        Fragment submit;
        Fragment map;
        public CrossRoadAdapter(FragmentManager fm) {
            super(fm);

        }
        public Fragment getItem(int i) {
            Log.d(logTag, "" + i);
            if (i == 0) {
                Log.d(logTag, report.toString());
                return report;
            } else if (i == 1) {
                Log.d(logTag, submit.toString());
                return submit;
            } else if (i == 2) {
                return map;
            }
            return report;
        }

        public int getCount() {
            return 3;
        }

        public void setFragment(Fragment report, Fragment submit, Fragment map) {
            this.report = report;
            this.submit = submit;
            this.map = map;
        }
    }

}
