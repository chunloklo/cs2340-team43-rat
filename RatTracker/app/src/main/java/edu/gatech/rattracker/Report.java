package edu.gatech.rattracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Structures a list of RatSightings.
 *
 * @author Jiseok Choi
 * @version 1.0
 */

class Report {
    private List<Sighting> sightings;
    private final HashMap<String, Sighting> sightingHashMap;

    public Report() {
        sightings = new ArrayList<>();
        sightingHashMap = new HashMap<>();
    }

    public void setSighting(List<Sighting> sightings) {
        this.sightings = sightings;
        for (Sighting s : sightings) {
            sightingHashMap.put(s.getKey(), s);
        }
    }

    public Sighting getSighting(int position) {
        return sightings.get(position);
    }

    public Sighting getSightingByKey(String key) {
        return sightingHashMap.get(key);
    }

    public List<Sighting> getSightings() {
        return sightings;
    }

    public int size() {
        return sightings.size();
    }

}
