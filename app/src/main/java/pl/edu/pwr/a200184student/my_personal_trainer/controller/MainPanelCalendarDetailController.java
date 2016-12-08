package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import pl.edu.pwr.a200184student.my_personal_trainer.adapters_listeners.ExpandableListViewAdapter;
import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Meal;
import pl.edu.pwr.a200184student.my_personal_trainer.model.Product;
import pl.edu.pwr.a200184student.my_personal_trainer.service.MealService;
import pl.edu.pwr.a200184student.my_personal_trainer.service.ProductService;
import pl.edu.pwr.a200184student.my_personal_trainer.util.MealUtil;

public class MainPanelCalendarDetailController extends AppCompatActivity {


    private Context context = this;
    private Intent intent;
    private ExpandableListViewAdapter mealsListAdapter;
    private ExpandableListView mealsExpandableList;
    private List<String> mealsList;
    private HashMap<String, List<String>> mealItemsHashMap;

    private Long userId;
    private int userCaloriesAmount;
    private int userProteinAmount;
    private int userCarbsAmount;
    private int userFatAmount;
    public static List<Meal> userMealsByDate;
    private String selectedDate;
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
        setOnItemLongClickListener();
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
            private void onGroupLongClick(final int groupPosition) {
                if(groupPosition != 5) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.add_product_to_meal_list_view, null);
                    final EditText productNameEdtiText = (EditText) promptsView
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
                                            try {
                                                int productWeight = Integer.parseInt(productAmountEdtiText.getText().toString());
                                                String productName = productNameEdtiText.getText().toString();
                                                FindProductTask task = new FindProductTask(productName, groupPosition, productWeight);
                                                task.execute((Void) null);
                                            } catch (Exception e) {
                                                Toast.makeText(context, "Proszę wprowadzić wagę w gramach.", Toast.LENGTH_LONG).show();
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
            }

            private void onChildLongClick(int groupPosition, int childPosition) {
                if(childPosition != 0){
                    Product productToDelete = userMealsByDate.get(groupPosition).getProducts().get(childPosition - 1);
                    DeleteProductTask task = new DeleteProductTask(userMealsByDate.get(groupPosition).getId() , productToDelete.getId() , groupPosition);
                    task.execute((Void) null);
                }
            }
        });
    }

    private void prepareListData() {
        if(mealsList.size() == 0){
            mealsList.add("Sniadanie");
            mealsList.add("Lunch");
            mealsList.add("Obiad");
            mealsList.add("Podwieczorek");
            mealsList.add("Kolacja");
            mealsList.add("Statystyki");
        }
        List<String> breakfestItems = new ArrayList<String>();
        breakfestItems.add(0,"Brak Produktów.");

        List<String> lunchItems = new ArrayList<String>();
        lunchItems.add(0,"Brak Produktów.");

        List<String> dinnerItems = new ArrayList<String>();
        dinnerItems.add(0,"Brak Produktów.");

        List<String> teaItems = new ArrayList<String>();
        teaItems.add(0,"Brak Produktów.");

        List<String> supperItems = new ArrayList<String>();
        supperItems.add(0,"Brak Produktów");

        List<String> statistics = new ArrayList<>();

        if(userMealsByDate.size() > 0){
            if(userMealsByDate.get(0).getProducts().size() != 0) {
                breakfestItems.addAll(MealUtil.addProductsToMeal(userMealsByDate.get(0)));
                breakfestItems.set(0, "Produkty : ");
            }
            if(userMealsByDate.get(1).getProducts().size() != 0) {
                lunchItems.addAll(MealUtil.addProductsToMeal(userMealsByDate.get(1)));
                lunchItems.set(0, "Produkty : ");
            }
            if(userMealsByDate.get(2).getProducts().size() != 0) {
                dinnerItems.addAll(MealUtil.addProductsToMeal(userMealsByDate.get(2)));
                dinnerItems.set(0, "Produkty : ");
            }
            if(userMealsByDate.get(3).getProducts().size() != 0) {
                teaItems.addAll(MealUtil.addProductsToMeal(userMealsByDate.get(3)));
                teaItems.set(0, "Produkty : ");
            }
            if(userMealsByDate.get(4).getProducts().size() != 0) {
                supperItems.addAll(MealUtil.addProductsToMeal(userMealsByDate.get(4)));
                supperItems.set(0, "Produkty : ");
            }
        }
        else{
            userMealsByDate.add(new Meal("Sniadanie" , selectedDate ));
            userMealsByDate.add(new Meal("Lunch" , selectedDate ));
            userMealsByDate.add(new Meal("Obiad" , selectedDate ));
            userMealsByDate.add(new Meal("Podwieczorek" , selectedDate ));
            userMealsByDate.add(new Meal("Kolacja" , selectedDate ));
            SaveNewMealsTask task = new SaveNewMealsTask();
            task.execute((Void) null);
        }
        statistics.addAll(MealUtil.prepareStatistics(userMealsByDate , userCaloriesAmount , userProteinAmount , userCarbsAmount , userFatAmount));
        mealItemsHashMap.put(mealsList.get(0), breakfestItems);
        mealItemsHashMap.put(mealsList.get(1), lunchItems);
        mealItemsHashMap.put(mealsList.get(2), dinnerItems);
        mealItemsHashMap.put(mealsList.get(3),teaItems);
        mealItemsHashMap.put(mealsList.get(4),supperItems);
        mealItemsHashMap.put(mealsList.get(5),statistics);
    }

    private void saveNewProduct() {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.add_new_product_view, null);
        final EditText addProductNameEditText   = (EditText) promptsView
                .findViewById(R.id.addProductNameEdtiText);
        final EditText addProductCaloriesEditText = (EditText) promptsView
                .findViewById(R.id.addProductCaloriesEdtiText);
        final EditText addProductProteinEdtiText = (EditText) promptsView
                .findViewById(R.id.addProductProteinEdtiText);
        final EditText addProductCarbsEdtiText = (EditText) promptsView
                .findViewById(R.id.addProductCarbsEdtiText);
        final EditText addProductFatEdtiText = (EditText) promptsView
                .findViewById(R.id.addProductFatEdtiText);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Zapisz",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try{
                                    String newProductName = addProductNameEditText.getText().toString();
                                    int newProductCaloriesAmount = Integer.parseInt(addProductCaloriesEditText.getText().toString());
                                    int newProductProteinAmount = Integer.parseInt(addProductProteinEdtiText.getText().toString());
                                    int newProductCarbsAmount = Integer.parseInt(addProductCarbsEdtiText.getText().toString());
                                    int newProductFatAmount = Integer.parseInt(addProductFatEdtiText.getText().toString());
                                    Product newProduct = new Product(newProductName , newProductCaloriesAmount ,  newProductProteinAmount , newProductCarbsAmount , newProductFatAmount);
                                    SaveProductTask task = new SaveProductTask(newProduct);
                                    task.execute((Void) null);
                                }
                                catch(Exception e){
                                    Toast.makeText(context,"Źle wprowadzona dana , sprawdź ponownie" , Toast.LENGTH_LONG).show();
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

    private void updateMealsList(int mealPosition, Product selectedProduct, int productWeight) {
        if(!userMealsByDate.get(mealPosition).getProductsWeight().containsKey(selectedProduct.getProductName())){
            userMealsByDate.get(mealPosition).getProducts().add(selectedProduct);
            userMealsByDate.get(mealPosition).getProductsWeight().put(selectedProduct.getProductName() , productWeight);
        }
        else {
            userMealsByDate.get(mealPosition).getProductsWeight().put(selectedProduct.getProductName() ,userMealsByDate.get(mealPosition).getProductsWeight().get(selectedProduct.getProductName()) + productWeight);
        }
        prepareListData();
        mealsListAdapter.notifyDataSetChanged();
        UpdateMeal task = new UpdateMeal(userMealsByDate.get(mealPosition).getId() , selectedProduct.getId() , productWeight);
        task.execute((Void) null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        Log.d("onrestart","onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d("ondestroy","onDestroy");
        userMealsByDate = null;
        super.onDestroy();
    }

    private class GetUserMealsByDate extends AsyncTask<Object, Object, List<Meal>> {

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
                Log.d("userMeals" , String.valueOf(userMealsByDate.size()));
                prepareListData();
                prepareListAdapter();
            }
        }
    }

    private class UpdateMeal extends AsyncTask<Void , Void , Meal>{

        private Long mealId;
        private Long productId;
        private int productWeight;

        public UpdateMeal(Long mealId , Long productId , int productWeight){
            this.mealId = mealId;
            this.productId = productId;
            this.productWeight = productWeight;
        }
        @Override
        protected Meal doInBackground(Void... params) {
           return MealService.updateMeal(mealId , productId , productWeight);
        }
        protected void onPostExecute(Meal mealAfterUpdate){
            if(mealAfterUpdate == null){
                Toast.makeText(context,"Wystąpił problem z połączniem" , Toast.LENGTH_LONG).show();
            }
        }
    }

    private class FindProductTask extends AsyncTask<Void,Void,Product>{

        private String productName;
        private int mealPosition;
        private int productWeight;

        public FindProductTask(String productName , int mealPosition , int productWeight){
            this.productName = productName;
            this.mealPosition = mealPosition;
            this.productWeight = productWeight;
        }

        @Override
        protected Product doInBackground(Void... params) {
            return selectedProduct = ProductService.findProduct(productName);
        }
        protected void onPostExecute(Product returnedProduct){
            if(selectedProduct == null){
                saveNewProduct();
            }
            else{
                updateMealsList(mealPosition , selectedProduct , productWeight);
            }
        }
    }

    private class SaveProductTask extends AsyncTask<Void , Void , Product>{

        private Product newProduct;

        public SaveProductTask(Product newProduct){
            super();
            this.newProduct = newProduct;
        }

        @Override
        protected Product doInBackground(Void... params) {
            return ProductService.saveNewProduct(newProduct);
        }
        protected void onPostExecute(Product product){
            if(product == null){
                Toast.makeText(context,"Wystąpił problem z połączniem" , Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context,"Zapisano nowy Produkt , od teraz można go używać :)" , Toast.LENGTH_LONG).show();
            }
        }
    }

    private class SaveNewMealsTask extends AsyncTask<Void , Void , List<Meal>>{

        @Override
        protected List<Meal> doInBackground(Void... params) {
            userMealsByDate =  MealService.saveMeals(userMealsByDate , userId);
            return userMealsByDate;
        }
        protected void onPostExecute(List<Meal> newMeals){
            if(newMeals == null){
                Toast.makeText(context,"Wystąpił problem z połączniem" , Toast.LENGTH_LONG).show();
            }
        }
    }

    private class DeleteProductTask extends AsyncTask<Void , Void , Meal>{

        private Long mealId;
        private Long productId;
        private int mealPositionOnList;

        public DeleteProductTask(Long mealId , Long productId , int mealPositionOnList){
            this.mealId = mealId;
            this.productId = productId;
            this.mealPositionOnList = mealPositionOnList;
        }

        @Override
        protected Meal doInBackground(Void... params) {
            return MealService.deleteMealItem(mealId , productId);
        }
        protected void onPostExecute(Meal mealWithoutItem){
            if(mealWithoutItem == null){
                Toast.makeText(context,"Wystąpił problem z połączniem" , Toast.LENGTH_LONG).show();
            }
            else{
                userMealsByDate.set(mealPositionOnList, mealWithoutItem);
                prepareListData();
                mealsListAdapter.notifyDataSetChanged();
            }
        }
    }
}