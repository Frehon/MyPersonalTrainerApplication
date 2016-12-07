package pl.edu.pwr.a200184student.my_personal_trainer.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.model.GpsEvent;

public class GpsService implements LocationListener{

    private Context context;
    private InterActiveTrainingService service;
    private GoogleMap map;
    private List<LatLng> coordinates;
    private List<Polyline> polylines;
    private TextView speedTextView;
    private TextView distanceTextView;
    private long time;
    private double distance;

    public GpsService(Context context , GoogleMap map){
        this.context = context;
        this.map = map;
        service = new InterActiveTrainingService();
        coordinates = new ArrayList<>();
        polylines = new ArrayList<>();
        speedTextView = (TextView) ((Activity)context).findViewById(R.id.speedValueTextView);
        distanceTextView = (TextView) ((Activity)context).findViewById(R.id.distanceValueTextView);
        time = System.currentTimeMillis();
        distance = 0;
    }

    @Override
    public void onLocationChanged(Location location) {
        GpsEvent newEvent = new GpsEvent(location.getLatitude() , location.getLongitude() , location.getAccuracy() , location.getTime());
        this.service.addEvent(newEvent);
        for(GpsEvent event : service.getEvents()){
            coordinates.add(new LatLng(event.getLatitude() , event.getLongitude()));
        }
        polylines.add(map.addPolyline(new PolylineOptions().width(8).color(Color.BLUE).addAll(coordinates)));
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinates.get(coordinates.size() - 1)));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates.get(coordinates.size() - 1), 17.0f));
        DecimalFormat df = new DecimalFormat("#.####");
        if(coordinates.size() > 1){
            distance += InterActiveTrainingService.getDistanceInKm(coordinates.get(coordinates.size() - 2) , coordinates.get(coordinates.size() - 1));
        }
        distanceTextView.setText(String.valueOf(df.format(distance) + " Km"));
        double currentTimeInHours = InterActiveTrainingService.getCurrentTrainingTime(time);
        if(currentTimeInHours != 0.0){
            double speedKmProHour = distance/currentTimeInHours;
            speedTextView.setText(String.valueOf(df.format(speedKmProHour)) + " Km/H");
        }
        else{
           speedTextView.setText("0.0 Km/H");
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

    public List<GpsEvent> getEvents(){
        return this.service.getEvents();
    }

    public void clearMapAndCoordintates(){
        for(Polyline line : this.polylines){
            line.remove();
        }
        this.polylines.clear();
        this.coordinates.clear();
        time = System.currentTimeMillis();
        distance = 0;
    }
}
