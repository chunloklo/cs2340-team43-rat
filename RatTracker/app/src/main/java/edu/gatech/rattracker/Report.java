package edu.gatech.rattracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Structurer for a list of RatSightings.
 *
 * @author Jiseok Choi
 * @version 1.0
 */

public class Report {
    private List<Sighting> sightings;

    public Report() {
        sightings = new ArrayList<Sighting>();
    }

    public void setSighting(List<Sighting> sightings) {
        this.sightings = sightings;
    }

    public Sighting getSighting(int position) {
        return sightings.get(position);
    }

    public List<Sighting> getSightings() {
        return sightings;
    }

    public int size() {
        return sightings.size();
    }

}
