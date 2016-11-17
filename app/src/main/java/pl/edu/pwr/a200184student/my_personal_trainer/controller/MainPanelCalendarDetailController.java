package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class MainPanelCalendarDetailController extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_panel_calendar_detail_view);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainPanelCalendarDetailController.this , MainPanelCalendarController.class);
        startActivity(intent);
        finish();
    }

}