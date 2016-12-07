package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.service.GpsService;
import pl.edu.pwr.a200184student.my_personal_trainer.model.GpsEvent;


public class InteractiveTrainingController extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap map;
    private Button startTrainingButton;
    private Button saveTrainingButton;
    private TextView speedTextView;
    private TextView distanceTextView;
    private LocationManager locationManager;
    private GoogleApiClient mGoogleApiClient;
    private GpsService service;
    private Chronometer timer;
    private long time;
    private List<GpsEvent> events;
    private List<LatLng> coordinates;
    private Marker startMarker;
    private Marker endMarker;

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
        startTrainingButton = (Button) findViewById(R.id.startTrainingButton);
        saveTrainingButton = (Button) findViewById(R.id.saveTrainingButton);
        speedTextView = (TextView) findViewById(R.id.speedValueTextView);
        distanceTextView = (TextView) findViewById(R.id.distanceValueTextView);
        coordinates = new ArrayList<>();
        startTrainingButton.setText("Rozpocznij Trening");
        timer = (Chronometer) findViewById(R.id.timer);
        setButtonOnClickListener();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if(checkPermission()){
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {}

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext() , "Some Problem with Gps connection" , Toast.LENGTH_LONG).show();
    }

    private void setButtonOnClickListener() {
        startTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(startTrainingButton.getText().equals("Rozpocznij Trening")){
                   startTrainingButton.setText("Zakończ Trening");
                   speedTextView.setText("0.0 Km/H");
                   distanceTextView.setText("0 Km");
                   saveTrainingButton.setEnabled(false);
                   if(startMarker != null && endMarker != null){
                       startMarker.remove();
                       endMarker.remove();
                       startMarker = null;
                       endMarker = null;
                   }
                   if(service == null){
                       service = new GpsService(InteractiveTrainingController.this, map);
                   }
                   else{
                       service.clearMapAndCoordintates();
                   }
                   timer.setBase(SystemClock.elapsedRealtime());
                   time = 0;
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                       if (checkPermission()) {
                           requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                   Manifest.permission.INTERNET}, 10);
                       }
                   }
                   else {
                       int refreshTimeInMillis = 1;
                       locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, refreshTimeInMillis, 0, service);
                   }
                   timer.start();
               }
               else{
                   startTrainingButton.setText("Rozpocznij Trening");
                   saveTrainingButton.setEnabled(true);
                   time = SystemClock.elapsedRealtime() - timer.getBase();
                   timer.stop();
                   events = service.getEvents();
                   locationManager.removeUpdates(service);
                   startMarker = map.addMarker(new MarkerOptions().title("Punkt Startowy").position(new LatLng(events.get(0).getLatitude() , events.get(0).getLongitude())));
                   endMarker = map.addMarker(new MarkerOptions().title("Punkt Końcowy").position(new LatLng(events.get(events.size() - 1).getLatitude() , events.get(events.size() - 1).getLongitude())));
               }
            }
        });

        saveTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save training.
            }
        });
    }

    public boolean checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
}
