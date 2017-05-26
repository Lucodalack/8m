package com.example.quytocngheo.myapplication.Model;

/**
 * Created by quytocngheo on 5/26/2017.
 */

public class Search {
    private String keyword;
    private int type = -1;
    private int distance = -1;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
