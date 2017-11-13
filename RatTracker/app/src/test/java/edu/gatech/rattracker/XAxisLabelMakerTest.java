package edu.gatech.rattracker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the XAxisLabelMaker method in GraphActivity
 *
 * Created by mwang on 11/13/2017.
 */

public class XAxisLabelMakerTest {
    private GraphActivity graphActivity;

    @Before
    public void setUp() {
        graphActivity = new GraphActivity();
    }

    @Test
    public void RecentMonthsTest() {
        String label = graphActivity.XAxisLabelMaker(564.0, true);
        Assert.assertEquals("Jan 2017", label);
        label = graphActivity.XAxisLabelMaker(565.0, true);
        Assert.assertEquals("Feb 2017", label);
        label = graphActivity.XAxisLabelMaker(566.0, true);
        Assert.assertEquals("Mar 2017", label);
        label = graphActivity.XAxisLabelMaker(567.0, true);
        Assert.assertEquals("Apr 2017", label);
        label = graphActivity.XAxisLabelMaker(568.0, true);
        Assert.assertEquals("May 2017", label);
        label = graphActivity.XAxisLabelMaker(569.0, true);
        Assert.assertEquals("Jun 2017", label);
        label = graphActivity.XAxisLabelMaker(570.0, true);
        Assert.assertEquals("Jul 2017", label);
        label = graphActivity.XAxisLabelMaker(571.0, true);
        Assert.assertEquals("Aug 2017", label);
        label = graphActivity.XAxisLabelMaker(572.0, true);
        Assert.assertEquals("Sep 2017", label);
        label = graphActivity.XAxisLabelMaker(573.0, true);
        Assert.assertEquals("Oct 2017", label);
        label = graphActivity.XAxisLabelMaker(574.0, true);
        Assert.assertEquals("Nov 2017", label);
        label = graphActivity.XAxisLabelMaker(575.0, true);
        Assert.assertEquals("Dec 2017", label);
    }

    @Test
    public void MultipleYearsMonthTest() {
        String label = graphActivity.XAxisLabelMaker(586.0, true);
        Assert.assertEquals("Nov 2018", label);
        label = graphActivity.XAxisLabelMaker(562.0, true);
        Assert.assertEquals("Nov 2016", label);
        label = graphActivity.XAxisLabelMaker(550.0, true);
        Assert.assertEquals("Nov 2015", label);
        label = graphActivity.XAxisLabelMaker(538.0, true);
        Assert.assertEquals("Nov 2014", label);
        label = graphActivity.XAxisLabelMaker(526.0, true);
        Assert.assertEquals("Nov 2013", label);
        label = graphActivity.XAxisLabelMaker(514.0, true);
        Assert.assertEquals("Nov 2012", label);
    }

    @Test
    public void FirstMonthTest() {
        String label = graphActivity.XAxisLabelMaker(0.0, true);
        Assert.assertEquals("Jan 1970", label);
    }

    @Test
    public void MonthsBefore1970Test() {
        String label = graphActivity.XAxisLabelMaker(-1.0, true);
        Assert.assertEquals("Dec 1969", label);
        label = graphActivity.XAxisLabelMaker(-12.0, true);
        Assert.assertEquals("Jan 1969", label);
        label = graphActivity.XAxisLabelMaker(-13.0, true);
        Assert.assertEquals("Dec 1968", label);
    }

    @Test
    public void RecentYearsTest() {
        String label = graphActivity.XAxisLabelMaker(2017.0, false);
        Assert.assertEquals("2017", label);
        label = graphActivity.XAxisLabelMaker(2016.0, false);
        Assert.assertEquals("2016", label);
        label = graphActivity.XAxisLabelMaker(2015.0, false);
        Assert.assertEquals("2015", label);
        label = graphActivity.XAxisLabelMaker(2014.0, false);
        Assert.assertEquals("2014", label);
    }

    @Test
    public void YearsBefore1970Test() {
        String label = graphActivity.XAxisLabelMaker(1969.0, false);
        Assert.assertEquals("1969", label);
    }
}
