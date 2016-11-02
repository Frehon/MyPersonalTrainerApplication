package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.content.Intent;
import android.view.View;

import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class on_start_controller extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.on_start_view);
    }

    // move to log_in activity.
    public void go_to_logIn_activity(View view){
        Intent intent = new Intent(this, log_in_controller.class);
        startActivity(intent);
    }

    // move to registry activity.
    public void go_to_registry_activity(View view){
        Intent intent = new Intent(this, registry_controller.class);
        startActivity(intent);
    }

}
