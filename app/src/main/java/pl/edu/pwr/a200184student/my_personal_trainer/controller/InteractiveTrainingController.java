package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class InteractiveTrainingController extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks , GoogleApiClient.OnConnectionFailedListener , LocationListener{

    private GoogleMap mMap;
    private Button startTrainingButton;
    private GoogleApiClient mGoogleApiClient;
    private Location myLastLocation;
    private LatLng myCoordinates;
    private MarkerOptions markerOptions;
    private Marker myLocalizationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        startTrainingButton = (Button) findViewById(R.id.start_tracking);
        startTrainingButton.setText("Rozpocznij Trening");
        setButtonOnClickListener();
    }

    private void setButtonOnClickListener() {
        startTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startTrainingButton.getText().equals("Rozpocznij Trening")) {
                    startTrainingButton.setText("Zakończ Trening");
                } else {
                    startTrainingButton.setText("Rozpocznij Trening");
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // permission required.
            return;
        }
        myLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if(myLastLocation != null){
            myCoordinates = new LatLng(myLastLocation.getLatitude(), myLastLocation.getLongitude());
            markerOptions = new MarkerOptions();
            markerOptions.position(myCoordinates);
            markerOptions.title("Moja początkowa Lokalizacja");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            myLocalizationMarker = mMap.addMarker(markerOptions);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(myCoordinates).zoom(14).build();

            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (myLocalizationMarker != null) {
            myLocalizationMarker.remove();
        }
        myLastLocation = location;
        myCoordinates = new LatLng(myLastLocation.getLatitude() , myLastLocation.getLongitude());
        markerOptions.position(myCoordinates);
        markerOptions.title("Aktualna Lokalizacja");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        myLocalizationMarker = mMap.addMarker(markerOptions);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myCoordinates).zoom(14).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"Connection Failed",Toast.LENGTH_SHORT).show();
    }
}
