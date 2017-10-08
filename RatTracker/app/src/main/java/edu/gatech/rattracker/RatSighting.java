package edu.gatech.rattracker;

/**
 * Information Holder for the data of a single rat sighting.
 *
 * @author Jiseok Choi
 * @version 1.0
 */

public class RatSighting {
    private String key;
    private long date;
    private String type;
    private int zip;
    private String address;
    private String city;
    private String borough;
    private double longitude;
    private double latitude;

    public RatSighting(String aKey, long aDate, String aType, int aZip, String aAddress,
                       String aCity, String aBorough, double aLongitude, double aLatitude) {
        key = aKey;
        date = aDate;
        type = aType;
        zip = aZip;
        address = aAddress;
        city = aCity;
        borough = aBorough;
        longitude = aLongitude;
        latitude = aLatitude;
    }

    public String getKey() {
        return key;
    }

    public long getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public int getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getBorough() {
        return borough;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
