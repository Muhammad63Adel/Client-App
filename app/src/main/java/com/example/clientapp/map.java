package com.example.clientapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;


public class map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient client;

    public double Latitude = 10;
    public double Longitude = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "inside if condition ", Toast.LENGTH_SHORT).show();
            return;
        }
        client = LocationServices.getFusedLocationProviderClient(this);
        mMap.setMyLocationEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Latitude = location.getLatitude();
                    Longitude = location.getLongitude();
                    LatLng latLng = new LatLng(Latitude,Longitude);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Latitude, Longitude)).title("Locaiton"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng , 10), 3000, null);
                    Toast.makeText(map.this, "latitude is "+ Latitude+"\n"+"Longitude is" + Longitude, Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    public void SaveLocation(View view) {
        String Lat = Double.toString(Latitude);
        String Lon = Double.toString(Longitude);
        Intent intent = new Intent();
        intent.putExtra("Lat", Lat);
        intent.putExtra("Lon", Lon);
        setResult(2, intent);
        finish();

    }
}
