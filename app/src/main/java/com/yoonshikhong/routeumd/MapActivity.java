package com.yoonshikhong.routeumd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final static String TAG = "MapActivity";
    private HashMap<String, LatLng> busStops;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        busStops = new HashMap<String, LatLng>();


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

        mMap.moveCamera(CameraUpdateFactory.newLatLng(csic));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16f));


        ParseQuery<ParseObject> query = ParseQuery.getQuery("bus_stops").setLimit(1000);

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                // Get the one row
                for (ParseObject o : objects) {


                    String stopKey = o.get("key").toString();
                    LatLng c = new LatLng(Double.valueOf(o.get("lat").toString()), Double.valueOf(o.get("long").toString()));
                    if (!busStops.containsKey(stopKey)) {
                        busStops.put(stopKey, c);
                        mMap.addMarker(new MarkerOptions().position(c).title(o.get("name").toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    }
                }
            }

        });

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("route0");
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                // Get the one row
                for (ParseObject o : objects) {
                    //keys: id, stops, trace, name
                    Object traces = o.get("traces");
                    if (traces != null) {
                        Log.d(TAG, traces.toString());
                    }


                }
            }

        });

        //testPlotLine();


    }

    public void plotLine(LatLng[] coords) {

        PolylineOptions rectOptions = new PolylineOptions().color(Color.BLUE);

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

    public void testPlotLine(){
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

    public void showDialog(String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(MapActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        // Alert dialog button
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Alert dialog action goes here
                        // onClick button code here
                        dialog.dismiss();// use dismiss to cancel alert dialog
                    }
                });
        alertDialog.show();
    }


}
