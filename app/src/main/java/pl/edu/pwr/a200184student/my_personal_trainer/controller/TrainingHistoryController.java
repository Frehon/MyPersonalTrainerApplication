package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Training;
import pl.edu.pwr.a200184student.my_personal_trainer.service.TrainingService;
import pl.edu.pwr.a200184student.my_personal_trainer.util.TrainingUtil;

public class TrainingHistoryController extends AppCompatActivity {

    private ListView TrainingHistoryListView ;
    private List<Training> userTrainings;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_history_view);

        TrainingHistoryListView = (ListView)findViewById(R.id.TrainingHistoryListView);
        userTrainings = new ArrayList<>();
        userId = getIntent().getLongExtra("userId" , 0);
        getUserTrainings();
    }

    private void getUserTrainings(){
        GetUserTrainings task = new GetUserTrainings(userId);
        task.execute((Void) null);
    }

    private void prepareList() {
        Collections.reverse(userTrainings);
        String [] items = TrainingUtil.prepareTrainingsList(userTrainings);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, items);
        TrainingHistoryListView.setAdapter(adapter);
    }

    private void setListListener() {
        TrainingHistoryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // delete i index of userTrainings;
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class GetUserTrainings extends AsyncTask<Void , Void , List<Training>>{

        private Long userId;

        public GetUserTrainings(Long userId){
            this.userId = userId;
        }

        @Override
        protected List<Training> doInBackground(Void... voids) {
            return TrainingService.getUserTrainings(userId);
        }
        protected void onPostExecute(List<Training> trainings){
            if(trainings == null){
                Toast.makeText(getApplicationContext(), "Wystąpił problem z połączeniem", Toast.LENGTH_LONG).show();
            }
            else{
                userTrainings = trainings;
                prepareList();
                setListListener();
            }
        }
    }
}
