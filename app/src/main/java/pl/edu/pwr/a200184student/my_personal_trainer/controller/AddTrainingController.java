package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

import pl.edu.pwr.a200184student.my_personal_trainer.Adapters.TrainingListAdapter;
import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class AddTrainingController extends Activity {
    ListView list;
    String[] trainings = {
            "Bieganie",
            "Trekking",
            "Jazda na rowerze",
            "Pływanie",
            "Trening na siłowni",
            "Jazda Konna",
            "Jazda na nartach",
            "Gra w Piłkę Nożną",
            "Gra w Koszykówkę",
            "Gra w Tenisa",
            "Sporty Walki",
    };
    Integer[] imageId = {
            R.drawable.running,
            R.drawable.trekking,
            R.drawable.cycle,
            R.drawable.swimming,
            R.drawable.gym,
            R.drawable.horse,
            R.drawable.skiing,
            R.drawable.football,
            R.drawable.basketball,
            R.drawable.tennis,
            R.drawable.boxing
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_training_view);

        TrainingListAdapter adapter = new
                TrainingListAdapter(AddTrainingController.this, trainings, imageId);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(AddTrainingController.this, "You Clicked at " + trainings[+position], Toast.LENGTH_SHORT).show();
                // dodanie treningu do bazy.

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
