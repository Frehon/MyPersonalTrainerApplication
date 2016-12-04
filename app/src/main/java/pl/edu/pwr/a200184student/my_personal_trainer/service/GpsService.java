package pl.edu.pwr.a200184student.my_personal_trainer.service;


import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.model.GpsEvent;

public class GpsService {

    private List<GpsEvent> events;

    public GpsService() {
        events = new ArrayList<>();
    }

    public void addEvent(GpsEvent newEvent){
        this.events.add(newEvent);
    }

    public List<GpsEvent> getEvents() {
        return events;
    }
}
