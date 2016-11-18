package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.Adapters.ExpandableListViewAdapter;
import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class MainPanelCalendarDetailController extends AppCompatActivity {

    ExpandableListViewAdapter mealsListAdapter;
    ExpandableListView mealsExpandableList;
    List<String> mealsList;
    HashMap<String, List<String>> mealItemsHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_panel_calendar_detail_view);
        initializeFields();
        prepareListAdapter();
        setOnItemLongClickListener();
        prepareListData();
    }

    private void initializeFields() {
        mealsExpandableList = (ExpandableListView) findViewById(R.id.mealsExpandableListView);
        mealsList = new ArrayList<>();
        mealItemsHashMap = new HashMap<>();
    }

    private void prepareListAdapter() {
        mealsListAdapter = new ExpandableListViewAdapter(this, mealsList, mealItemsHashMap);
        mealsExpandableList.setAdapter(mealsListAdapter);
    }

    private void setOnItemLongClickListener() {
        mealsExpandableList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                long packedPosition = mealsExpandableList.getExpandableListPosition(position);
                int itemType = ExpandableListView.getPackedPositionType(packedPosition);
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    onGroupLongClick(groupPosition);
                }
                else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    onChildLongClick(groupPosition, childPosition);
                }
                return true;
            }
            private void onGroupLongClick(int groupPosition) {
                Toast.makeText(getApplicationContext(), "klikłem na parenta !" , Toast.LENGTH_LONG).show();
            }

            private void onChildLongClick(int groupPosition, int childPosition) {
                Toast.makeText(getApplicationContext(), "klikłem na childa" , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void prepareListData() {

        mealsList.add("Śniadanie");
        mealsList.add("Lunch");
        mealsList.add("Obiad");
        mealsList.add("Podwieczorek");
        mealsList.add("Kolacja");

        List<String> breakfestItems = new ArrayList<String>();
        breakfestItems.add("item one");
        breakfestItems.add("item two");
        breakfestItems.add("item three");
        breakfestItems.add("item four");
        breakfestItems.add("item five");
        breakfestItems.add("item six");
        breakfestItems.add("item seven");

        List<String> lunchItems = new ArrayList<String>();
        lunchItems.add("item one");
        lunchItems.add("item two");
        lunchItems.add("item three");
        lunchItems.add("item four");
        lunchItems.add("item five");
        lunchItems.add("item six");

        List<String> dinnerItems = new ArrayList<String>();
        dinnerItems.add("item one");
        dinnerItems.add("item two");
        dinnerItems.add("item three");
        dinnerItems.add("item four");
        dinnerItems.add("item five");

        List<String> teaItems = new ArrayList<String>();
        teaItems.add("item one");
        teaItems.add("item two");
        teaItems.add("item three");
        teaItems.add("item four");
        teaItems.add("item five");

        List<String> supperItems = new ArrayList<String>();
        supperItems.add("item one");
        supperItems.add("item two");
        supperItems.add("item three");
        supperItems.add("item four");
        supperItems.add("item five");

        mealItemsHashMap.put(mealsList.get(0), breakfestItems); // Header, Child data
        mealItemsHashMap.put(mealsList.get(1), lunchItems);
        mealItemsHashMap.put(mealsList.get(2), dinnerItems);
        mealItemsHashMap.put(mealsList.get(3),teaItems);
        mealItemsHashMap.put(mealsList.get(4),supperItems);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainPanelCalendarDetailController.this , MainPanelCalendarController.class);
        startActivity(intent);
        finish();
    }

}