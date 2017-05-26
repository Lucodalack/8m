package com.example.quytocngheo.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by quytocngheo on 5/24/2017.
 */

public class ResponseLogin implements Serializable {
    @SerializedName("login_status")
    boolean login_status;

    public boolean isLogin_status() {
        return login_status;
    }

    public void setLogin_status(boolean login_status) {
        this.login_status = login_status;
    }


}
