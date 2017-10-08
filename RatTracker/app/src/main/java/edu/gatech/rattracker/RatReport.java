package edu.gatech.rattracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Structurer for a list of RatSightings.
 *
 * @author Jiseok Choi
 * @version 1.0
 */

public class RatReport {
    private List<RatSighting> sightings;

    public RatReport() {
        sightings = new ArrayList<RatSighting>();
    }

    public List<RatSighting> getSightings() {
        return sightings;
    }
}
