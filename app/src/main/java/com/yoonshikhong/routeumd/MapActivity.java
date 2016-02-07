package com.yoonshikhong.routeumd;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng csic = new LatLng(38.989935, -76.936155);
        LatLng view = new LatLng(38.992724, -76.934337);

        mMap.addMarker(new MarkerOptions().position(csic).title("Marker at CSIC").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.addMarker(new MarkerOptions().position(view).title("Marker at View"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(csic));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16f));


        LatLng[] coords = new LatLng[9];

        coords[0] = new LatLng(38.989935, -76.936155);
        coords[1] = new LatLng(38.989613, -76.936452);
        coords[2] = new LatLng(38.989469, -76.937047);
        coords[3] = new LatLng(38.988487, -76.936597);
        coords[4] = new LatLng(38.988491, -76.935095);
        coords[5] = new LatLng(38.992740, -76.933088);
        coords[6] = new LatLng(38.992944, -76.933684);
        coords[7] = new LatLng(38.992396, -76.933941);
        coords[8] = new LatLng(38.992724, -76.934337);


        plotLine(coords);

    }

    public void plotLine(LatLng[] coords) {

        PolylineOptions rectOptions = new PolylineOptions();

        int i = 0;

        mMap.addMarker(new MarkerOptions().position(coords[i]).title("Marker at start").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coords[0]));

        for (i++; i < coords.length-1; i++) {
            rectOptions.add(coords[i]);
        }
        // Get back the mutable Polyline
        Polyline polyline = mMap.addPolyline(rectOptions);
        
        mMap.addMarker(new MarkerOptions().position(coords[i]).title("Marker at destination"));




    }


}
