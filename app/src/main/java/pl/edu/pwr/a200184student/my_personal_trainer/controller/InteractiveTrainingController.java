package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.adapters_listeners.GpsListener;
import pl.edu.pwr.a200184student.my_personal_trainer.model.GpsEvent;


public class InteractiveTrainingController extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap map;
    private Button startTrainingButton;
    private LocationManager locationManager;
    private GpsListener listener;
    private Chronometer timer;
    private long time;
    private List<GpsEvent> events;
    private List<LatLng> coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        initialize();
    }

    private void initialize() {
        startTrainingButton = (Button)findViewById(R.id.startTrainingButton);
        listener = new GpsListener(getApplicationContext());
        coordinates = new ArrayList<>();
        startTrainingButton.setText("Rozpocznij Trening");
        timer = (Chronometer)findViewById(R.id.timer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET}, 10);
            }
        }
        else {
            int refreshTimeInMillis = 0;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    refreshTimeInMillis, 0, listener);
        }
        setButtonOnClickListener();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    private void setButtonOnClickListener() {
        startTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(startTrainingButton.getText().equals("Rozpocznij Trening")){
                   startTrainingButton.setText("Zakończ Trening");
                   time = 0;
                   timer.setBase(SystemClock.elapsedRealtime());
                   timer.start();
               }
               else{
                   startTrainingButton.setText("Rozpocznij Trening");
                   //counting time
                   time = SystemClock.elapsedRealtime() - timer.getBase();
                   // stopping timer
                   timer.stop();
                   // getting onLocationChange Events
                   events = listener.getEvents();
                   Log.d("event" , events.get(0).getLatitude() + " " + events.get(0).getLongitude() + " " + events.size());
                   // setting list with LatLng objects for drawing line on the map.
                   for(GpsEvent event : events){
                       coordinates.add(new LatLng(event.getLatitude() , event.getLongitude()));
                   }
                   map.addPolyline(new PolylineOptions().width(3).color(Color.BLUE).addAll(coordinates));
                           map.addMarker(new
                                  MarkerOptions().position(coordinates.get(0)).title("last localization"));
                   map.moveCamera(CameraUpdateFactory.newLatLng(coordinates.get(0)));
                   map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates.get(0), 12.0f));
                   // restarting
                   time = 0;
               }
            }
        });
    }
}
