package edu.gatech.rattracker;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Unit test for Report
 * Created by Chunlok Lo on 11/12/2017.
 */

public class ReportTest {

    @Test
    public void normalTest(){
        Report report = new Report();
        List<Sighting> sightings = new LinkedList<>();
        Sighting sight = new Sighting("", 0, "", 0, "",
                "", "", 0.0, 0.0);
        sightings.add(sight);
        report.setSighting(sightings);
        Assert.assertEquals("", sight, report.getSighting(0));
    }

    @Test
    public void getSightingTest(){
        Report report = new Report();
        List<Sighting> sightings = new LinkedList<>();
        Sighting sight = new Sighting("", 0, "", 0, "",
                "", "", 0.0, 0.0);
        sightings.add(sight);
        report.setSighting(sightings);
        Assert.assertEquals("", sightings, report.getSightings());
    }

    @Test
    public void getSightingByKey(){
        Report report = new Report();
        List<Sighting> sightings = new LinkedList<>();
        Sighting sight = new Sighting("1234", 0, "", 0, "",
                "", "", 0.0, 0.0);
        sightings.add(sight);
        report.setSighting(sightings);
        Assert.assertEquals("", sight, report.getSightingByKey("1234"));
    }

    @Test
    public void sizeTest(){
        Report report = new Report();
        List<Sighting> sightings = new LinkedList<>();
        Sighting sight = new Sighting("", 0, "", 0, "",
                "", "", 0.0, 0.0);
        sightings.add(sight);
        report.setSighting(sightings);
        Assert.assertEquals("", report.size(),
                1);
    }

}
