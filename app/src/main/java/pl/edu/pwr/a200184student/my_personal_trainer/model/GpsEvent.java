package pl.edu.pwr.a200184student.my_personal_trainer.model;



public class GpsEvent {

    private double latitude;
    private double longitude;
    private float eventTime;
    private float accuracy;

    public GpsEvent(double latitude, double longitude, float accuracy, float eventTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
        this.eventTime = eventTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getEventTime() {
        return eventTime;
    }

    public void setEventTime(float eventTime) {
        this.eventTime = eventTime;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
