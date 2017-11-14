package edu.gatech.rattracker;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alan on 11/13/17.
 * Tests the methods involved with converting a unix timestamp into proper
 * months/years.
 */

public class UnixTimeConversionTest {

    @Test
    public void calcUnixTimeInMonthsTypicalCases() {
        assertEquals(calculateUnixTimeInMonths(0, 2017), 564); // Jan, 2017
        assertEquals(calculateUnixTimeInMonths(5, 2017), 569); // May 2017
        assertEquals(calculateUnixTimeInMonths(9, 2018), 585); // September, 2018
    }

    @Test
    public void calcUnixTimeInMonthsEdgeCases() {
        assertEquals(calculateUnixTimeInMonths(0, 1970), 0); // January, 1970
        assertEquals(calculateUnixTimeInMonths(0, 1969), -12); //January, 1969
    }

    @Test
    public void calcUnixMonthsTypical() {
        assertEquals(unixMonthToMonth(12), 0);
        assertEquals(unixMonthToMonth(564), 0);
        assertEquals(unixMonthToMonth(569), 5);
        assertEquals(unixMonthToMonth(585), 9);
    }

    @Test
    public void calcUnixMonthsEdgeCases() {
        assertEquals(unixMonthToMonth(0), 0);
        assertEquals(unixMonthToMonth(-13), 11);
    }

    @Test
    public void unixMonthToYearTypicalCases() {
        assertEquals(unixMonthToYear(585), 2018);
        assertEquals(unixMonthToYear(569), 2017);
        assertEquals(unixMonthToYear(595), 2019);
    }

    @Test
    public void unixMonthToYearEdgeCases() {
        assertEquals(unixMonthToYear(0), 1970);
        assertEquals(unixMonthToYear(-1), 1969);
        assertEquals(unixMonthToYear(-13), 1968);
    }

    /**
     * Takes in a unixMonth value and returns the year as a 4 digit int
     * @param unixMonth the unixMonth value to be converted
     * @return the year as a 4 digit int
     */
    private int unixMonthToYear(int unixMonth) {
        return 1970 + (int) Math.floor(unixMonth / 12.0);
    }


    /**
     * Calculates the number of months a specific month is from the beginning of the year 1970
     * with Jan 1970 taking the value 1.
     *
     * @param month the month as an int (0-11)
     * @param year the year as a 4 digit int
     * @return the number of months since the beginning of 1970
     */
    private int calculateUnixTimeInMonths(int month, int year) {
        int retval = month;
        retval += (year - 1970) * 12;
        return retval;
    }

    /**
     * Takes in a unixMonth value and returns the month as an int 0-11
     * @param unixMonth the unixMonth value to be converted
     * @return the month as an int 0-11
     */
    private int unixMonthToMonth(int unixMonth) {
        int result = unixMonth % 12;
        if (result < 0) {
            result += 12;
        }
        return result;
    }



}
