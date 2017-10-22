package edu.gatech.rattracker;

/**
 * Created by Chunlok Lo on 10/18/2017.
 */

public enum Borough {
    MANHATTAN, STATEN_ISLAND, QUEENS, BROOKLYN, BRONX, NONE;

    @Override public String toString(){
        return this.name();
    }
}
