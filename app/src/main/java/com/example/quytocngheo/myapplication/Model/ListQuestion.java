package com.example.quytocngheo.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by quytocngheo on 5/21/2017.
 */

public class ListQuestion implements Serializable {
    @SerializedName("items")
    List<Question> items;
}
