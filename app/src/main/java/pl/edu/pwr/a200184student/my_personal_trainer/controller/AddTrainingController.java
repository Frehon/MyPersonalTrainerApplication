package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.content.Context;
import android.content.DialogInterface;
import java.text.DateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.edu.pwr.a200184student.my_personal_trainer.adapters_listeners.TrainingListAdapter;
import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Training;
import pl.edu.pwr.a200184student.my_personal_trainer.service.TrainingService;

public class AddTrainingController extends Activity {
    private Context context = this;
    private Long userId;
    ListView list;
    String[] trainings = {
            "Bieganie",
            "Trekking",
            "Jazda na rowerze",
            "Plywanie",
            "Trening na silowni",
            "Jazda Konna",
            "Jazda na nartach",
            "Gra w Pilkę Nozną",
            "Gra w Koszykowkę",
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
        userId = getIntent().getLongExtra("userId",0);

        TrainingListAdapter adapter = new
                TrainingListAdapter(AddTrainingController.this, trainings, imageId);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, View view,
                                    final int position, long id) {
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
                                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                            Date date = new Date();
                                            String todaysDate = dateFormat.format(date);
                                            int duration = Integer.parseInt(addNewTrainingEditText.getText().toString());
                                            CreateNewTrainingTask task = new CreateNewTrainingTask(new Training(trainings[+position] , duration , todaysDate , userId));
                                            task.execute((Void) null);
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

    private class CreateNewTrainingTask extends AsyncTask<Void , Void , Training>{

        private Training newTraining;

        public CreateNewTrainingTask(Training training){
            newTraining = training;
        }

        @Override
        protected Training doInBackground(Void... voids) {
            return TrainingService.createNewTraining(newTraining);
        }
        protected void onPostExecute(Training newTraining){
            if(newTraining == null){
                Toast.makeText(getApplicationContext(), "Wystąpił problem z połączeniem.", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Dodano Trening. Wszystkie treningi znajdziesz w zakładce Historia Treningów", Toast.LENGTH_LONG).show();
            }
        }
    }
}
