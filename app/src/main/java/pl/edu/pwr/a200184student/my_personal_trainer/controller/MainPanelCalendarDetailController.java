package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.Adapters.ExpandableListViewAdapter;
import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class MainPanelCalendarDetailController extends AppCompatActivity {

    private ExpandableListView mealsExpandableListView ;
    private List<String> meals;
    private HashMap<String,List<String>> mealItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_panel_calendar_detail_view);
        initializeFields();

    }

    private void initializeFields() {
        mealsExpandableListView = (ExpandableListView)findViewById(R.id.mealsExpandableListView);
        meals = new ArrayList<>();
        List<String> L1 = new ArrayList<>();
        mealItems = new HashMap<>();
        String [] mealsArray = getResources().getStringArray(R.array.header_meals_titles);
        String [] mealsItemArray = getResources().getStringArray(R.array.child_mealItems_titles);
        for(String meal : mealsArray){
            meals.add(meal);
        }
        for(String mealItem : mealsItemArray){
            L1.add(mealItem);
        }
        mealItems.put(meals.get(0),L1);
        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(getApplicationContext(),meals,mealItems);
        mealsExpandableListView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainPanelCalendarDetailController.this , MainPanelCalendarController.class);
        startActivity(intent);
        finish();
    }

}