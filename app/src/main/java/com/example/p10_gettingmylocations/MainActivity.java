package com.example.p10_gettingmylocations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;

public class MainActivity extends AppCompatActivity {

    Button btnMusicOff, btnStart, btnStop, btnCheckRecords;
    TextView tvLastLocation, tvLongitude, tvLatitude;
    private GoogleMap map;
    FusedLocationProviderClient client;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMusicOff = findViewById(R.id.btnMusicOff);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnCheckRecords = findViewById(R.id.btnCheckRecords);

        tvLastLocation = findViewById(R.id.tvLastLocation);
        tvLongitude = findViewById(R.id.tvLongitude);
        tvLatitude = findViewById(R.id.tvLatitude);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(this);

        LocationRequest mlocationRequest = LocationRequest.create();
        mlocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mlocationRequest.setInterval(10000);
        mlocationRequest.setFastestInterval(5000);
        mlocationRequest.setSmallestDisplacement(100);

        LocationCallback mlocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null) {
                    Location data = locationResult.getLastLocation();
                    double lat = data.getLatitude();
                    double lng = data.getLongitude();
                }
            }
        };

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);

                checkPermission();
            }
        });

        btnMusicOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // starting the
                if (btnMusicOff.getText() == "Music On") {
                    checkPermission();
                    startService(new Intent(MainActivity.this, Music.class));
                    btnMusicOff.setText("Music Off");
                } else {
                    stopService(new Intent(MainActivity.this, Music.class));
                    btnMusicOff.setText("Music On");
                }
            }
        });

        btnStart.setOnClickListener(v -> {
            startService(new Intent(MainActivity.this, Detector.class));
            Log.d("Detector", "Started");
        });

        btnStop.setOnClickListener(v -> {
            stopService(new Intent(MainActivity.this, Detector.class));
            Log.d("Detector", "Stopped");
        });

        btnCheckRecords.setOnClickListener(v -> {

        });
    }

    private boolean checkPermission() {
        int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED || permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }
}