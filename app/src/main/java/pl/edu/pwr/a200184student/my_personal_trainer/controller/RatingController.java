package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class RatingController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
