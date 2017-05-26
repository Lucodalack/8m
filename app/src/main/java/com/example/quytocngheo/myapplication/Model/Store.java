package com.example.quytocngheo.myapplication.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by quytocngheo on 5/25/2017.
 */

public class Store implements Serializable {
    @SerializedName("_id")
    private String id;

    @SerializedName("nameplace")
    private String nameplace;

    @SerializedName("address")
    private String address;

    @SerializedName("type")
    private int type;

    @SerializedName("menu")
    private List menu;

    @SerializedName("location")
    private Location location;

    private LatLng latLng;

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    private Marker marker;

    private double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameplace() {
        return nameplace;
    }

    public void setNameplace(String nameplace) {
        this.nameplace = nameplace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List getMenu() {
        return menu;
    }

    public void setMenu(List menu) {
        this.menu = menu;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
