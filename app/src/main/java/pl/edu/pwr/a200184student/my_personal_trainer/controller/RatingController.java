package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class RatingController extends AppCompatActivity {

    private EditText commentEditText;
    private RatingBar rating;
    private Button confirmOpinionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_view);
        initializeFields();
        setOnClickListener();
    }

    private void initializeFields() {
        commentEditText = (EditText)findViewById(R.id.commentEditText);
        rating = (RatingBar)findViewById(R.id.ratingBar);
        confirmOpinionButton = (Button)findViewById(R.id.sendOpinionButton);
    }

    public void setOnClickListener() {
        confirmOpinionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext() , "Dziękuję za opinie , na pewno okaże się pomocna w rozwijaniu aplikacji ;)" , Toast.LENGTH_LONG).show();
                commentEditText.setText("");
            }
        });

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getApplicationContext() , "Zmieniłeś swoją ocenę na " + rating , Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
