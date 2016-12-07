package pl.edu.pwr.a200184student.my_personal_trainer.service;


import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.model.GpsEvent;

public class InterActiveTrainingService {

    private List<GpsEvent> events;

    public InterActiveTrainingService() {
        events = new ArrayList<>();
    }

    public void addEvent(GpsEvent newEvent){
        this.events.add(newEvent);
    }

    public List<GpsEvent> getEvents() {
        return events;
    }

    public static double getDistanceInKm(LatLng startLatLng , LatLng currentLatLng) {
        final double R = 6367.0;
        double lat1 = startLatLng.latitude;
        double lng1 = startLatLng.longitude;
        double lat2 = currentLatLng.latitude;
        double lng2 = currentLatLng.longitude;
        double factor = Math.PI / 180.0;
        double dlng = (lng2 - lng1) * factor;
        double dlat = (lat2 - lat1) * factor;
        double a = Math.pow(Math.sin(dlat / 2.0), 2.0) + Math.cos(lat1 * factor) *
                Math.cos(lat2 * factor) * Math.pow(Math.sin(dlng / 2.0), 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
        return R * c;
    }

    public static double getCurrentTrainingTime(long startTime) {
       double currentTime = (System.currentTimeMillis() - startTime)/1000/60;
        Log.d("time" , String.valueOf(currentTime));
        if(Double.isNaN(currentTime) || Double.isInfinite(currentTime)){
            return 0.0;
        }
        else{
            return currentTime;
        }
    }
}
