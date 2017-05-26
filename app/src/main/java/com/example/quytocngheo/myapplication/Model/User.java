package com.example.quytocngheo.myapplication.Model;

/**
 * Created by quytocngheo on 5/24/2017.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class User  implements Serializable {
    @SerializedName("id_fb")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("gender")
    private String gender;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("email")
    private String email;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String id, String name, String birthday, String gender, String avatar, String email) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.avatar = avatar;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
