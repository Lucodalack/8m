package com.example.quytocngheo.myapplication.Service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quytocngheo.myapplication.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by quytocngheo on 5/26/2017.
 */

public class PopupAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater mInflater;

    public PopupAdapter(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        View popup = mInflater.inflate(R.layout.popup_layout, null);
        Button close = (Button) popup.findViewById(R.id.popup_detail_btn_detail);
        TextView tvName = (TextView) popup.findViewById(R.id.popup_detail_tv_name);
        TextView tvAddress = (TextView) popup.findViewById(R.id.popup_detail_tv_address);
        String[] snippet = marker.getSnippet().split(",");
        tvName.setText(snippet[0]);
        tvAddress.setText(snippet[1]);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker.hideInfoWindow();
            }
        });
        return popup;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
