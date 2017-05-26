package com.example.quytocngheo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.quytocngheo.myapplication.Service.API;
import com.example.quytocngheo.myapplication.Service.ServiceHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity
public class BaseActivity extends AppCompatActivity {

    protected API retrofitServiceInterface;
    protected ServiceHelper retrofitService;

    @AfterViews
    void init() {
        retrofitService = new ServiceHelper(retrofitServiceInterface);
    }
}
