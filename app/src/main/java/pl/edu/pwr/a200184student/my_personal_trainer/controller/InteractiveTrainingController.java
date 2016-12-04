package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.adapters_listeners.GpsListener;


public class InteractiveTrainingController extends FragmentActivity{

    private Button startTrainingButton;
    private LocationManager locationManager;
    private Chronometer timer;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        initialize();
    }

    private void initialize() {
        startTrainingButton = (Button)findViewById(R.id.startTrainingButton);
        startTrainingButton.setText("Rozpocznij Trening");
        timer = (Chronometer)findViewById(R.id.timer);
        GpsListener listener = new GpsListener(getApplication());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET}, 10);
            }
        }
        else {
            int refreshTimeInMillis = 2;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    refreshTimeInMillis, 0, listener);
        }
        setButtonOnClickListener();
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
                   time = SystemClock.elapsedRealtime() - timer.getBase();
                   timer.stop();
                   Log.d("time" , String.valueOf(time/1000));
                   time = 0;
               }
            }
        });
    }

}
