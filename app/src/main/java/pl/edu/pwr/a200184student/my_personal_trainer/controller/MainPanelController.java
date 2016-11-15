package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.model.User;


public class MainPanelController extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static User currentLoggedUser;
    private TextView currentLoggedUserTextView;
    private TextView dietaryPlanTextView;
    private TextView dailyCaloriesAmountTextView;
    private TextView dailyProteinAmountTextView;
    private TextView dailyCarbsAmountTextView;
    private TextView dailyFatAmountTextView;
    private EditText userWeightEditText;
    private EditText userHeightEditText;
    private EditText userActivityFactor;
    private Button editDimensionsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFields();
        prepareListeners();
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

        } else if (id == R.id.addNewTrainingTab) {

        } else if (id == R.id.trainingHistoryTab) {

        } else if (id == R.id.settingsTab) {

        } else if (id == R.id.rateTheAppTab) {

        } else if (id == R.id.logOutTab){
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
        userWeightEditText = (EditText)findViewById(R.id.userWeightTextView);
        userHeightEditText = (EditText)findViewById(R.id.userHeightTextView);
        userActivityFactor = (EditText)findViewById(R.id.userActivityFactorTextView);
        editDimensionsButton = (Button)findViewById(R.id.editDimensionsButton);
        editDimensionsButton.setText("Edytuj Wymiary");

    }

    private void collectAndShowLoggedUserData() {
        Intent intent = getIntent();
        currentLoggedUser.setId(intent.getLongExtra("UserId", 0));
        currentLoggedUser.setUserName(intent.getStringExtra("UserName"));
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
            editDimensionsButton.setText("Edytuj Wymiary");
            lock();
        }
    }


}
