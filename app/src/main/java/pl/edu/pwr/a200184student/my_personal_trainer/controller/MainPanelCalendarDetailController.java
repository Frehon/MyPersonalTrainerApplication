package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pl.edu.pwr.a200184student.my_personal_trainer.Adapters.ExpandableListViewAdapter;
import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Meal;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Product;
import pl.edu.pwr.a200184student.my_personal_trainer.service.MealService;
import pl.edu.pwr.a200184student.my_personal_trainer.service.ProductService;
import pl.edu.pwr.a200184student.my_personal_trainer.util.MealUtil;

public class MainPanelCalendarDetailController extends AppCompatActivity {


    private Context context = this;
    private Intent intent;
    ExpandableListViewAdapter mealsListAdapter;
    ExpandableListView mealsExpandableList;
    List<String> mealsList;
    HashMap<String, List<String>> mealItemsHashMap;

    private Long userId;
    private int userCaloriesAmount;
    private int userProteinAmount;
    private int userCarbsAmount;
    private int userFatAmount;
    public static List<Meal> userMealsByDate;
    private String selectedDate;
    private String productName;
    private int productWeight;
    private Product selectedProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_panel_calendar_detail_view);
        initializeFields();
        collectUserDietaryInfo();
        try {
            getUserMealsByDate();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        prepareListAdapter();
    }

    private void initializeFields() {
        mealsExpandableList = (ExpandableListView) findViewById(R.id.mealsExpandableListView);
        mealsList = new ArrayList<>();
        mealItemsHashMap = new HashMap<>();
        intent = getIntent();
    }

    private void collectUserDietaryInfo() {
        userId = intent.getLongExtra("userId",0);
        userCaloriesAmount = intent.getIntExtra("userCaloriesAmount",0);
        userProteinAmount = intent.getIntExtra("userProteinAmount",0);
        userCarbsAmount = intent.getIntExtra("userCarbsAmount",0);
        userFatAmount = intent.getIntExtra("userFatAmount",0);
    }

    private void getUserMealsByDate() throws ExecutionException, InterruptedException {
        userMealsByDate = new ArrayList<>();
        selectedDate = intent.getStringExtra("SelectedDate");
        GetUserMealsByDate task = new GetUserMealsByDate();
        task.execute((Void) null).get();
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
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.add_product_to_meal_list_view, null);
                final EditText productNameEdtiText  = (EditText) promptsView
                        .findViewById(R.id.productNameEdtiText);
                final EditText productAmountEdtiText = (EditText) promptsView
                        .findViewById(R.id.productAmountEdtiText);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Dodaj",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try{
                                            productWeight = Integer.parseInt(productAmountEdtiText.getText().toString());
                                            productName = productNameEdtiText.getText().toString();
                                            FindProductTask task = new FindProductTask();
                                            task.execute((Void) null);
                                            // zaktualizuj posiłek.
                                        }
                                        catch(Exception e){
                                            Toast.makeText(context,"Proszę wprowadzić wagę w gramach." , Toast.LENGTH_LONG).show();
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

            private void onChildLongClick(int groupPosition, int childPosition) {
                if(childPosition != 0){

                }
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
        breakfestItems.add(0,"Produkty : ");

        List<String> lunchItems = new ArrayList<String>();
        lunchItems.add(0,"Produkty : ");

        List<String> dinnerItems = new ArrayList<String>();
        dinnerItems.add(0,"Produkty : ");

        List<String> teaItems = new ArrayList<String>();
        teaItems.add(0,"Produkty : ");

        List<String> supperItems = new ArrayList<String>();
        supperItems.add(0,"Produkty : ");

        if(userMealsByDate.size() > 0){
            for(Meal m : userMealsByDate){
                switch (m.getMealName()){
                    case "Śniadanie" :
                        breakfestItems.addAll(MealUtil.addProductsToMeal(m));
                        break;
                    case "Lunch" :
                        lunchItems.addAll(MealUtil.addProductsToMeal(m));
                        break;
                    case "Obiad" :
                        dinnerItems.addAll(MealUtil.addProductsToMeal(m));
                        break;
                    case "Podwieczorek" :
                        teaItems.addAll(MealUtil.addProductsToMeal(m));
                        break;
                    case "Kolacja" :
                        supperItems.addAll(MealUtil.addProductsToMeal(m));
                        break;
                }
            }
        }
        mealItemsHashMap.put(mealsList.get(0), breakfestItems);
        mealItemsHashMap.put(mealsList.get(1), lunchItems);
        mealItemsHashMap.put(mealsList.get(2), dinnerItems);
        mealItemsHashMap.put(mealsList.get(3),teaItems);
        mealItemsHashMap.put(mealsList.get(4),supperItems);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public class GetUserMealsByDate extends AsyncTask<Object, Object, List<Meal>> {

        @Override
        protected List<Meal> doInBackground(Object... params) {
            return MealService.getMealsByDateByUserId(userId , selectedDate);
        }
        protected void onPostExecute(List<Meal> meals){
            if(meals == null){
                Toast.makeText(context,"Wystąpił problem z połączniem" , Toast.LENGTH_LONG).show();
            }
            else{
                userMealsByDate = meals;
                setOnItemLongClickListener();
                prepareListData();
            }
        }
    }

    public class FindProductTask extends AsyncTask<Void,Void,Product>{

        @Override
        protected Product doInBackground(Void... params) {
            return selectedProduct = ProductService.findProduct(productName);
        }
        protected void onPostExecute(Product returnedProduct){
            if(selectedProduct == null){
                // dodaj do bazy nowy.
            }
        }
    }
}