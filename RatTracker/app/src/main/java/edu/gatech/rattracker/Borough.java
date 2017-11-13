package edu.gatech.rattracker;

/**
 * Borough Enum
 * Created by Chunlok Lo on 10/18/2017.
 */

public enum Borough {
    NONE, MANHATTAN, STATEN_ISLAND, QUEENS, BROOKLYN, BRONX;

    @Override public String toString(){
        return this.name();
    }
}
