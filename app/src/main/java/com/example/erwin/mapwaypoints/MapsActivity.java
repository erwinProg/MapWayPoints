package com.example.erwin.mapwaypoints;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.*;
import java.io.*;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
      //  LatLng sydney = new LatLng(43.771767, -79.624438);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in DDC HeadQuarter"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
      //  mMap.moveCamera(CameraUpdateFactory.zoomTo(5));
        Log.d("Map","we made it");

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        preNFZ(googleMap);
        read(googleMap);

    }

    public void preNFZ(GoogleMap googleMap)
    {
        String teststring;
        int loc = 0 ;

        Vector<String[]> stringSplit = new Vector<String[]> (10);

        InputStream is = this.getResources().openRawResource(R.raw.NFZ);

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;

        try {
            while ((teststring = br.readLine()) != null) {

                //get first line to printable string
                Log.d("Data", teststring);
                String[] parts = teststring.split("\\s+");
                stringSplit.add(parts);
                Log.d("Data splited", stringSplit.get(loc)[0]);
                //this code works
                //array[loadcounter] = br.readLine();
                //want to get this remarked part working for level load
                loc++;
            }

        } catch (IOException e) {
            e.printStackTrace(); //create exception output
        }



    }


    public void read(GoogleMap googleMap)
    {
        String teststring;
        int loc = 0 ;

        Vector<String[]> stringSplit = new Vector<String[]> (10);

        InputStream is = this.getResources().openRawResource(R.raw.samplepath);

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;

        try {
            while ((teststring = br.readLine()) != null) {

                //get first line to printable string
                    Log.d("Data", teststring);
                    String[] parts = teststring.split("\\s+");
                     stringSplit.add(parts);
                    Log.d("Data splited", stringSplit.get(loc)[0]);
                    //this code works
                    //array[loadcounter] = br.readLine();
                    //want to get this remarked part working for level load
                    loc++;
            }

        } catch (IOException e) {
            e.printStackTrace(); //create exception output
        }

        DecimalFormat df = new DecimalFormat("0.00##");

        PolylineOptions rectOptions = new PolylineOptions();
        List<Marker> markers = new ArrayList<Marker>();

        for(int i = 0; i <stringSplit.size(); i ++)
        {
            double lat = Double.parseDouble(stringSplit.get(i)[2]);
            double lon = Double.parseDouble(stringSplit.get(i)[1]);
            double ele = Double.parseDouble(stringSplit.get(i)[3]);

            Marker marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)));
            marker.setTitle("Waypoint: " + stringSplit.get(i)[0]);
            marker.setSnippet("Lat: " + df.format(lat) + " Lon: " + df.format(lon) + "\n Ele: " + df.format(ele));
            if(i == 0)
            {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }
            else if(i== stringSplit.size() -1)
            {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            }

            markers.add(marker);

            rectOptions.add(new LatLng(lat,lon));
        }
        Log.d("Display on GoogleMaps", "Processing happening");
        markers.size();
        Polyline polyline = googleMap.addPolyline(rectOptions);

        double latS = Double.parseDouble(stringSplit.get(0)[2]);
        double lonS = Double.parseDouble(stringSplit.get(0)[1]);

        LatLng startingP = new LatLng(latS, lonS);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(startingP));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(13));


    }

}
