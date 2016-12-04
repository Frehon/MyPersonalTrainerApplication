package pl.edu.pwr.a200184student.my_personal_trainer.adapters_listeners;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.model.GpsEvent;
import pl.edu.pwr.a200184student.my_personal_trainer.service.GpsService;

public class GpsListener implements LocationListener {

    private Context context;
    private GpsService service;

    public GpsListener(Context context){
        this.context = context;
        service = new GpsService();
    }

    @Override
    public void onLocationChanged(Location location) {
        GpsEvent newEvent = new GpsEvent(location.getLatitude() , location.getLongitude() , location.getAccuracy() , location.getTime());
        service.addEvent(newEvent);
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
}
