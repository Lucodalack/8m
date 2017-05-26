package com.example.quytocngheo.myapplication.Model;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;
/**
 * Created by quytocngheo on 5/21/2017.
 */

public class Question implements Serializable {

    @SerializedName("title")
    private String title;

    @Override
    public String toString() {
        return(title);
    }
}
