package edu.gatech.rattracker;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.jjoe64.graphview.DefaultLabelFormatter;

/**
 * Unit test for Report
 * Created by Jiseok Choi on 11/13/2017.
 */

public class LabelFormatterTest {
    private GraphActivity graphActivity;
    private MonthFormatter monthFormatter;
    private YearFormatter yearFormatter;

    @Before
    public void setUp() {
        graphActivity = new GraphActivity();
        monthFormatter = new MonthFormatter();
        yearFormatter = new YearFormatter();
    }

    @Test
    public void monthXTest() {
        assertEquals(monthFormatter.formatLabel(57148249, true), graphActivity.XAxisLabelMaker(57148249, true));
        assertEquals(monthFormatter.formatLabel(1381, true), graphActivity.XAxisLabelMaker(1381, true));
        assertEquals(monthFormatter.formatLabel(-478248, true), graphActivity.XAxisLabelMaker(-478248, true));
    }

    @Test
    public void monthYTest() {
        assertEquals(monthFormatter.formatLabel(57148249, false), "Y: " + (double) 57148249);
        assertEquals(monthFormatter.formatLabel(1381, false), "Y: " + (double) 1381);
        assertEquals(monthFormatter.formatLabel(-478248, false), "Y: " + (double) -478248);
    }

    @Test
    public void yearXTest() {
        assertEquals(yearFormatter.formatLabel(57148249, true), graphActivity.XAxisLabelMaker(57148249, false));
        assertEquals(yearFormatter.formatLabel(1381, true), graphActivity.XAxisLabelMaker(1381, false));
        assertEquals(yearFormatter.formatLabel(-478248, true), graphActivity.XAxisLabelMaker(-478248, false));
    }

    @Test
    public void yearYTest() {
        assertEquals(yearFormatter.formatLabel(57148249, false), "Y: " + (double) 57148249);
        assertEquals(yearFormatter.formatLabel(1381, false), "Y: " + (double) 1381);
        assertEquals(yearFormatter.formatLabel(-478248, false), "Y: " + (double) -478248);
    }

    /**
     * Formats axes for sorting rat sightings by month.
     */
    private class MonthFormatter extends DefaultLabelFormatter {
        @Override
        public String formatLabel(double value, boolean isValueX) {
            if (isValueX) {
                return graphActivity.XAxisLabelMaker(value, true);
            } else {
                return "Y: " + value;
            }
        }
    }

    /**
     * Formats axes for sorting rat sightings by year.
     */
    private class YearFormatter extends DefaultLabelFormatter {
        @Override
        public String formatLabel(double value, boolean isValueX) {
            if (isValueX) {
                return graphActivity.XAxisLabelMaker(value, false);
            } else {
                return "Y: " + value;
            }
        }
    }
}
