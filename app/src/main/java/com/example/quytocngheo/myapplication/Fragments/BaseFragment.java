package com.example.quytocngheo.myapplication.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quytocngheo.myapplication.R;
import com.example.quytocngheo.myapplication.Service.API;
import com.example.quytocngheo.myapplication.Service.ServiceHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment
public class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }


    protected API retrofitServiceInterface;
    protected ServiceHelper retrofitService;

    @AfterViews
    void init() {
        retrofitService = new ServiceHelper(retrofitServiceInterface);
    }

}
