package com.example.quytocngheo.myapplication.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quytocngheo.myapplication.Model.Store;
import com.example.quytocngheo.myapplication.R;
import com.example.quytocngheo.myapplication.Service.GPSHelper;
import com.example.quytocngheo.myapplication.Service.PopupAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

import java.util.ArrayList;
import java.util.List;
//import  com.example.quytocngheo.myapplication.Model.Location;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_map)
public class MapFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener {

    private static final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 100;
    GoogleMap supportMap;
    GPSHelper gpsHelper;
    private GoogleApiClient mGoogleApiClient;
    private List<Store> stores;
    private  SupportMapFragment mapFragment;
    private  LatLng location;

    @ViewById(R.id.fragment_map_ln_search)
    LinearLayout lnSearch;

    @ViewById(R.id.fragment_map_et_keyword)
    EditText etKeyword;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    void init() {
        super.init();

        stores = new ArrayList<>();
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_place_map);

        gpsHelper = new GPSHelper(getActivity());
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                location = null;
                Location locations = getLastKnownLocation();
                if (locations != null) {
                    double longitude = locations.getLongitude();
                    double latitude = locations.getLatitude();
                    location = new LatLng(latitude, longitude);

                } else {
                    location = new LatLng(10.878231, 106.806210);
                }
                PopupAdapter addapter = new PopupAdapter(getActivity().getLayoutInflater());
                googleMap.setInfoWindowAdapter(addapter);
                googleMap.setOnMarkerClickListener(MapFragment.this);
                googleMap.addMarker(new MarkerOptions()
                        .position(location));

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                loading();
            }
        });
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            try {
                mGoogleApiClient = new GoogleApiClient
                        .Builder(getActivity())
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .addApi(LocationServices.API)
                        .enableAutoManage(getActivity(), this)
                        .addConnectionCallbacks(this)
                        .build();
                //mGoogleApiClient.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Background
    public void loading() {
        Task<List<Store>> task = retrofitService.loadStore();
        task.continueWith(new Continuation<List<Store>, Object>() {
            @Override
            public Object then(Task<List<Store>> task) throws Exception {
                if (task.isFaulted() || task.isCancelled()) {

                } else {
                    setStores(task.getResult());
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            for (Store item:stores) {
                                Marker tamp = googleMap.addMarker(new MarkerOptions()
                                        .position(item.getLatLng())
                                        .title(item.getNameplace())
                                        .snippet(item.getNameplace()+","+item.getAddress()));
                                item.setMarker(tamp);
                                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                            }

                        }
                    });
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }


    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                continue;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ACCESS_COARSE_LOCATION: {
                LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
                List<String> providers = mLocationManager.getProviders(true);
                Location bestLocation = null;
                for (String provider : providers) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        continue;
                    }
                    Location l = mLocationManager.getLastKnownLocation(provider);
                    if (l == null) {
                        continue;
                    }
                    if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                        // Found best last known location: %s", l);
                        bestLocation = l;
                    }
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
        for (Store item : this.stores) {
            item.setLatLng(new LatLng(item.getLocation().getLatitude(),item.getLocation().getLongitude()));
            if(location!=null) {
                item.setDistance(distance(location.latitude,location.longitude,item.getLatLng().latitude,item.getLatLng().longitude));
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
        return false;
    }
    public float distance (double lat_a, double lng_a, double lat_b, double lng_b )
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }

    @Click(R.id.fragment_map_et_keyword)
    void search() {
        lnSearch.setVisibility(View.VISIBLE);
        etKeyword.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etKeyword, InputMethodManager.SHOW_IMPLICIT);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Click(R.id.fragment_map_v_hide_search)
    void hideSearch() {
        lnSearch.setVisibility(View.GONE);
    }

    @Click(R.id.fragment_map_tv_1km)
    void below1Km() {
        for(Store item : stores) {
            if(item.getDistance()>1000) {
                item.getMarker().setVisible(false);
            } else {
                item.getMarker().setVisible(true);
            }
        }
        lnSearch.setVisibility(View.GONE);
    }

    @Click(R.id.fragment_map_tv_3km)
    void below3Km() {
        for(Store item : stores) {
            if(item.getDistance()>5000) {
                item.getMarker().setVisible(false);
            } else {
                item.getMarker().setVisible(true);
            }
        }
        lnSearch.setVisibility(View.GONE);
    }

    @Click(R.id.fragment_map_tv_2km)
    void below2Km() {
        for(Store item : stores) {
            if(item.getDistance()>3000) {
                item.getMarker().setVisible(false);
            } else {
                item.getMarker().setVisible(true);
            }
        }
        lnSearch.setVisibility(View.GONE);
    }

    @Click(R.id.fragment_map_tv_food)
    void showFood() {
        for(Store item : stores) {
            if(item.getType()!=0) {
                item.getMarker().setVisible(false);
            } else {
                item.getMarker().setVisible(true);
            }
        }
        lnSearch.setVisibility(View.GONE);
    }

    @Click(R.id.fragment_map_tv_drink)
    void showDrink() {
        for(Store item : stores) {
            if(item.getType()!=1) {
                item.getMarker().setVisible(false);
            } else {
                item.getMarker().setVisible(true);
            }
        }
        lnSearch.setVisibility(View.GONE);
    }
}