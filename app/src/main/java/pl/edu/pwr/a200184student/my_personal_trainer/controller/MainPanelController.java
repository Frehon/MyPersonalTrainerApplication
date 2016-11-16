package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import pl.edu.pwr.a200184student.my_personal_trainer.service.UserService;
import pl.edu.pwr.a200184student.my_personal_trainer.util.UserUtil;




public class MainPanelController extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static User currentLoggedUser;
    private TextView currentLoggedUserTextView;
    private TextView dietaryPlanTextView;
    private TextView dailyCaloriesAmountTextView;
    private TextView dailyProteinAmountTextView;
    private TextView dailyCarbsAmountTextView;
    private TextView dailyFatAmountTextView;
    private TextView editDietTypeTextView;
    private EditText userWeightEditText;
    private EditText userHeightEditText;
    private EditText userActivityFactor;
    private Spinner editDietTypeSpinner;
    private Button editDimensionsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFields();
        prepareListeners();
        prepareEditDietTypeSpinner();
        collectAndShowLoggedUserData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_panel_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.dietaryCalendarTab) {
            Intent intent = new Intent(MainPanelController.this,MainCalendarController.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        else if (id == R.id.addNewTrainingTab) {

        }
        else if (id == R.id.trainingHistoryTab) {

        }
        else if (id == R.id.settingsTab) {
            Intent intent = new Intent(MainPanelController.this,MainPanelSettingsController.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        else if (id == R.id.rateTheAppTab) {

        }
        else if (id == R.id.logOutTab){
            onBackPressed();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Wyjście")
                .setMessage("Czy na pewno chcesz wyjść ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(new Intent(MainPanelController.this,OnStartController.class));
                        finish();
                    }
                }).create().show();
    }

    private void initializeFields() {
        setContentView(R.layout.main_panel_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        currentLoggedUser = new User();
        currentLoggedUserTextView = (TextView)findViewById(R.id.currentLoggedUserTextView);
        dietaryPlanTextView = (TextView)findViewById(R.id.dietryPlanTextView);
        dailyCaloriesAmountTextView = (TextView)findViewById(R.id.dailyCaloriesAmountTextView);
        dailyProteinAmountTextView = (TextView)findViewById(R.id.dailyProteinAmountTextView);
        dailyCarbsAmountTextView = (TextView)findViewById(R.id.dailyCarbsAmountTextView);
        dailyFatAmountTextView = (TextView)findViewById(R.id.dailyFatAmountTextView);
        editDietTypeTextView= (TextView)findViewById(R.id.editDietTypeTextView);
        userWeightEditText = (EditText)findViewById(R.id.userWeightTextView);
        userHeightEditText = (EditText)findViewById(R.id.userHeightTextView);
        userActivityFactor = (EditText)findViewById(R.id.userActivityFactorTextView);
        editDietTypeSpinner = (Spinner)findViewById(R.id.editDietTypeSpinner);
        editDimensionsButton = (Button)findViewById(R.id.editDimensionsButton);
        editDimensionsButton.setText("Edytuj Wymiary");

    }

    private void collectAndShowLoggedUserData() {
        Intent intent = getIntent();
        currentLoggedUser.setId(intent.getLongExtra("UserId", 0));
        currentLoggedUser.setUserName(intent.getStringExtra("UserName"));
        currentLoggedUser.setBirthYear(intent.getStringExtra("UserBirthYear"));
        currentLoggedUser.setGender(intent.getStringExtra("UserGender"));
        currentLoggedUser.setEmail(intent.getStringExtra("UserEmail"));
        currentLoggedUser.setWeight(intent.getIntExtra("UserWeight", 0));
        currentLoggedUser.setHeight(intent.getIntExtra("UserHeight", 0));
        currentLoggedUser.setDietType(intent.getStringExtra("UserDietType"));
        currentLoggedUser.setActivityFactor(intent.getDoubleExtra("UserActivityFactor", 0));
        currentLoggedUser.setCaloriesAmount(intent.getIntExtra("UserCaloriesAmount", 0));
        currentLoggedUser.setProteinAmount(intent.getIntExtra("UserProteinAmount", 0));
        currentLoggedUser.setCarbsAmount(intent.getIntExtra("UserCarbsAmount", 0));
        currentLoggedUser.setFatAmount(intent.getIntExtra("UserFatAmount", 0));
        displayUserData();
        lock();
    }

    private void prepareListeners() {
        userWeightEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userWeightEditText.setText("");
            }
        });
        userHeightEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userHeightEditText.setText("");
            }
        });
        userActivityFactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userActivityFactor.setText("");
            }
        });
    }

    private void prepareEditDietTypeSpinner() {
        List<String> dietTypeList = new ArrayList<>();
        dietTypeList.add("Rodzaje Diety");
        dietTypeList.add("Zbilansowana");
        dietTypeList.add("Masowa");
        dietTypeList.add("Redukcyjna");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dietTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editDietTypeSpinner.setAdapter(dataAdapter);
        editDietTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) parent.getChildAt(0)).setTextSize(25);
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                String item = parent.getItemAtPosition(position).toString();
                if(!item.equals("Rodzaje Diety")){
                    if(currentLoggedUser.getDietType().equals(item)){
                        Toast.makeText(getApplicationContext(),"Wybrano ten sam typ diety!" ,Toast.LENGTH_LONG).show();
                    }
                    else{
                        currentLoggedUser.setDietType(item);
                        UpdateTask task = new UpdateTask();
                        task.execute((Void) null);
                    }
                }
                else{
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void displayUserData(){
        currentLoggedUserTextView.setText("Użytkownik: " + currentLoggedUser.getUserName());
        dietaryPlanTextView.setText("Plan Diety: " + currentLoggedUser.getDietType());
        dailyCaloriesAmountTextView.setText("Podaż Kalorii: " + currentLoggedUser.getCaloriesAmount() + " Kcal");
        dailyProteinAmountTextView.setText("Białko: " + currentLoggedUser.getProteinAmount() + " Gram");
        dailyCarbsAmountTextView.setText("Węglowodany: " + currentLoggedUser.getCarbsAmount() + " Gram");
        dailyFatAmountTextView.setText("Tłuszcze: " + currentLoggedUser.getFatAmount() + " Gram");
        userWeightEditText.setText("Waga: " + currentLoggedUser.getWeight() + " Kg");
        userHeightEditText.setText("Wzrost: " + currentLoggedUser.getHeight() + " Cm");
        userActivityFactor.setText("Współczynnik Aktywności: " + currentLoggedUser.getActivityFactor());
        editDietTypeTextView.setText("Zmień typ diety -->");
    }

    private void lock() {
        userWeightEditText.setEnabled(false);
        userHeightEditText.setEnabled(false);
        userActivityFactor.setEnabled(false);
    }

    private void unlock(){
        userWeightEditText.setEnabled(true);
        userHeightEditText.setEnabled(true);
        userActivityFactor.setEnabled(true);
    }


    public void onEditButtonClick(View v){
        if(editDimensionsButton.getText().equals("Edytuj Wymiary")){
            editDimensionsButton.setText("Zatwierdź Zmiany");
            unlock();
        }
        else{
            String newWeight = userWeightEditText.getText().toString();
            if(UserUtil.checkDigits(newWeight , "weight")){
                if(Integer.parseInt(newWeight) > 180){
                    Toast.makeText(getApplicationContext() , "Waga poza przedziałem. Maksymalna waga to 180 kg!" , Toast.LENGTH_LONG).show();
                    displayUserData();
                    lock();
                    editDimensionsButton.setText("Edytuj Wymiary");
                    return;
                }
                currentLoggedUser.setWeight(Integer.parseInt(newWeight));
            }
            String newHeight = userHeightEditText.getText().toString();
            if(UserUtil.checkDigits(newHeight , "height")){
                if(Integer.parseInt(newHeight) > 240){
                    Toast.makeText(getApplicationContext() , "Wzrost poza przedziałem. Maksymalny wzrost to 240 cm!" , Toast.LENGTH_LONG).show();
                    displayUserData();
                    lock();
                    editDimensionsButton.setText("Edytuj Wymiary");
                    return;
                }
                currentLoggedUser.setHeight(Integer.parseInt(newHeight));
            }
            String newActivityFactor = userActivityFactor.getText().toString();
            if(UserUtil.checkDigits(newActivityFactor , "factor")){
                if(Double.parseDouble(newActivityFactor) > 2.0){
                    Toast.makeText(getApplicationContext() , "Współczynnik Aktywności przyjmuje wartości z przedziału 1.0 - 2.0. Aby dowiedzieć się więcej kliknij w opcję w prawym górnym rogu" , Toast.LENGTH_LONG).show();
                    displayUserData();
                    lock();
                    editDimensionsButton.setText("Edytuj Wymiary");
                    return;
                }
                currentLoggedUser.setActivityFactor(Double.parseDouble(newActivityFactor));
            }
            UpdateTask task = new UpdateTask();
            task.execute((Void) null);
        }
    }


    public class UpdateTask extends AsyncTask<Void, Void, User> {

        @Override
        protected User doInBackground(Void... params) {
            HashMap<String,String> newDietMap = UserUtil.prepareNewDiet(currentLoggedUser.getDietType(),currentLoggedUser.getWeight(),currentLoggedUser.getHeight(),currentLoggedUser.getGender(),currentLoggedUser.getBirthYear(),currentLoggedUser.getActivityFactor());
            currentLoggedUser = UserUtil.updateDietValues(currentLoggedUser,newDietMap);
            return currentLoggedUser = UserService.updateUser(currentLoggedUser.getId() , currentLoggedUser);

        }

        protected void onPostExecute(User userAfterUpdate) {
            if(userAfterUpdate == null){
                Toast.makeText(getApplicationContext() , "Proszę Wprowadzić Liczbę" , Toast.LENGTH_LONG).show();
                return;
            }
            currentLoggedUser = userAfterUpdate;
            displayUserData();
            editDimensionsButton.setText("Edytuj Wymiary");
            lock();
        }
    }
}
