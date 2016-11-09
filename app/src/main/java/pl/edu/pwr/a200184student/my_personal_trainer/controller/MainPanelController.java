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
import android.widget.Toast;

import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.model.User;

public class MainPanelController extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User currentLoggedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFields();
        collectAndShowLoggedUserData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_panel_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dietaryCalendarTab) {

        } else if (id == R.id.addNewTrainingTab) {

        } else if (id == R.id.trainingHistoryTab) {

        } else if (id == R.id.settingsTab) {

        } else if (id == R.id.rateTheAppTab) {

        } else if (id == R.id.logOutTab){

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
    }

    private void collectAndShowLoggedUserData() {
        Intent intent = getIntent();
        currentLoggedUser.setId(intent.getLongExtra("UserId",0));
        currentLoggedUser.setUserName(intent.getStringExtra("UserName"));
        currentLoggedUser.setGender(intent.getStringExtra("UserGender"));
        currentLoggedUser.setWeight(intent.getIntExtra("UserWeight", 0));
        currentLoggedUser.setHeight(intent.getIntExtra("UserHeight", 0));
        currentLoggedUser.setDietType(intent.getStringExtra("UserDietType"));
        currentLoggedUser.setActivityFactor(intent.getDoubleExtra("UserActivityFactor",0));
        currentLoggedUser.setCaloriesAmount(intent.getIntExtra("UserCaloriesAmount", 0));
        currentLoggedUser.setProteinAmount(intent.getIntExtra("UserProteinAmount", 0));
        currentLoggedUser.setCarbsAmount(intent.getIntExtra("UserCarbsAmount", 0));
        currentLoggedUser.setFatAmount(intent.getIntExtra("UserFatAmount", 0));
        //Toast.makeText(getApplicationContext(), "loggeduser : " + currentLoggedUser.getId()  , Toast.LENGTH_LONG).show();
    }


}
