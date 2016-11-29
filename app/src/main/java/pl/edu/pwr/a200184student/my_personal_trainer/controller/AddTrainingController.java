package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

import pl.edu.pwr.a200184student.my_personal_trainer.Adapters.TrainingListAdapter;
import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class AddTrainingController extends Activity {
    private Context context = this;
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
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.add_new_training_view, null);
                final EditText addNewTrainingEditText = (EditText) promptsView
                        .findViewById(R.id.addTrainingDurationEditText);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Zatwierdź",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       try{
                                         int duration = Integer.parseInt(addNewTrainingEditText.getText().toString());
                                       }
                                       catch (Exception e){
                                           Toast.makeText(getApplicationContext(), "Proszę wprowadzić długość treningu." , Toast.LENGTH_LONG).show();
                                       }
                                    }
                                })
                        .setNegativeButton("Anuluj",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
