package com.example.quytocngheo.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by quytocngheo on 5/25/2017.
 */

public class Location  implements Serializable {

    @SerializedName("lat")
    private double latitude;

    @SerializedName("long")
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
