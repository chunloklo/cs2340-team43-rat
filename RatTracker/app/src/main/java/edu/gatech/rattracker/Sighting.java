package edu.gatech.rattracker;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * Information Holder for the data of a single rat sighting.
 *
 * @author Jiseok Choi
 * @version 1.0
 */

public class Sighting implements Serializable {
    private String key;
    private long date;
    private String type;
    private long zip;
    private String address;
    private String city;
    private String borough;
    private double longitude;
    private double latitude;

    public Sighting() {
    }

    public Sighting(String aKey, long aDate, String aType, long aZip, String aAddress,
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

    public String getReformedDate() {
        Date date = new Date(this.date * 1000);
        return date.toString();
    }

    public String getReformedLocationType() {
        try {
            return URLDecoder.decode(type, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return type;
        }
    }

    public String getType() {
        return type;
    }

    public long getZip() {
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

    public void setKey(String aKey) {
        key = aKey;
    }

    @Override
    public String toString() {
        Date date = new Date(this.date * 1000);
        return date.toString() + " : " + address;
    }

}
