package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import pl.edu.pwr.a200184student.my_personal_trainer.service.UserService;
import pl.edu.pwr.a200184student.my_personal_trainer.util.UserUtil;
import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class RegistryDetailController extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private HashMap<String,String> newUserData;
    private NumberPicker weightNumberPicker;
    private NumberPicker heightNumberPicker;
    private CheckBox balanceGoalCheckBox;
    private CheckBox massCheckBox;
    private CheckBox lossCheckBox;
    private Spinner activityFactorSpinner;
    private Button infoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registry_detail_view);
        Intent intent = getIntent();
        newUserData = (HashMap<String,String>) intent.getSerializableExtra("map");
        initializeFields();
        preparePickers();
        prepareGoalCheckBoxes();
        prepareActivityFactorSpinner();
        preparePopUpInfoView();
    }

    private void initializeFields() {
        weightNumberPicker = (NumberPicker)findViewById(R.id.weightNumberPicker);
        heightNumberPicker = (NumberPicker)findViewById(R.id.heightNumberPicker);
        balanceGoalCheckBox = (CheckBox)findViewById(R.id.balanceGoalCheckBox);
        massCheckBox = (CheckBox)findViewById(R.id.massCheckBox);
        lossCheckBox = (CheckBox)findViewById(R.id.lossCheckBox);
        activityFactorSpinner = (Spinner)findViewById(R.id.activityFactorSpinner);
        infoButton = (Button)findViewById(R.id.infoButton);
    }

    private void preparePickers() {
        weightNumberPicker.setMaxValue(180);
        weightNumberPicker.setMinValue(30);
        weightNumberPicker.setValue(70);
        heightNumberPicker.setMaxValue(240);
        heightNumberPicker.setMinValue(120);
        heightNumberPicker.setValue(175);
        weightNumberPicker.setWrapSelectorWheel(true);
        heightNumberPicker.setWrapSelectorWheel(true);
    }

    private void prepareGoalCheckBoxes() {
        balanceGoalCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (massCheckBox.isChecked()) {
                    massCheckBox.setChecked(false);
                }
                if (lossCheckBox.isChecked()) {
                    lossCheckBox.setChecked(false);
                }
                balanceGoalCheckBox.setChecked(b);
            }
        });
        massCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (balanceGoalCheckBox.isChecked()) {
                    balanceGoalCheckBox.setChecked(false);
                }
                if (lossCheckBox.isChecked()) {
                    lossCheckBox.setChecked(false);
                }
                massCheckBox.setChecked(b);
            }
        });
        lossCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (balanceGoalCheckBox.isChecked()) {
                    balanceGoalCheckBox.setChecked(false);
                }
                if (massCheckBox.isChecked()) {
                    massCheckBox.setChecked(false);
                }
                lossCheckBox.setChecked(b);
            }
        });
    }

    private void prepareActivityFactorSpinner() {
        activityFactorSpinner.setOnItemSelectedListener(this);
        ArrayList<String> factorValues = new ArrayList<String>();
        factorValues.add("1.2");
        factorValues.add("1.4");
        factorValues.add("1.6");
        factorValues.add("1.8");
        factorValues.add("2.0");

        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line , factorValues);
        activityFactorSpinner.setAdapter(spinner_adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selected_factor = adapterView.getItemAtPosition(i).toString();
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) adapterView.getChildAt(0)).setTextSize(25);
        ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.DEFAULT_BOLD);
        ((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);
        newUserData.put("ActivityFactor" , selected_factor);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    public void preparePopUpInfoView(){
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistryDetailController.this , InfoController.class));
            }
        });
    }


    public void collectDataAndFinishRegistration(View view) {
        newUserData.put("Weight" , String.valueOf(weightNumberPicker.getValue()));
        newUserData.put("Height" , String.valueOf(heightNumberPicker.getValue()));

        if(!balanceGoalCheckBox.isChecked() && !massCheckBox.isChecked() && !lossCheckBox.isChecked()){
            Toast.makeText(getApplicationContext(), "Wybierz swój cel ", Toast.LENGTH_LONG).show();
            return;
        }
        if(balanceGoalCheckBox.isChecked()){
            newUserData.put("DietType" , "Balanced");
        }
        else{
            if(massCheckBox.isChecked()){
                newUserData.put("DietType" , "Mass");
            }
            else{
                if(lossCheckBox.isChecked()){
                    newUserData.put("DietType" , "Loss");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Nie Wybrano Celu " , Toast.LENGTH_LONG).show();
                }
            }
        }
        HashMap<String,String> dietMap = UserUtil.prepareDiet(newUserData.get("DietType") , newUserData.get("Weight") , newUserData.get("Height") , newUserData.get("Gender") , newUserData.get("BirthYear") , newUserData.get("ActivityFactor"));
        newUserData.putAll(dietMap);
        RegistryTask task = new RegistryTask();
        task.execute((Void) null);
    }

    public class RegistryTask extends AsyncTask<Void, Void, User>{

        @Override
        protected User doInBackground(Void... params) {
            User existingUser = UserService.getUserByEmail(newUserData.get("Email"));
            if(existingUser != null){
                return null;
            }
            else{
              User newUser =  UserUtil.prepareNewUser(newUserData);
                try {
                    return UserService.createNewUser(newUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        protected void onPostExecute(final User newUser){
            if(newUser != null){
                Intent intent = new Intent(RegistryDetailController.this, MainPanelController.class);
                intent.putExtra("UserId" , newUser.getId());
                intent.putExtra("UserName" , newUser.getUserName());
                intent.putExtra("UserGender" , newUser.getGender());
                intent.putExtra("UserEmail" , newUser.getEmail());
                intent.putExtra("UserWeight" , newUser.getWeight());
                intent.putExtra("UserHeight" , newUser.getHeight());
                intent.putExtra("UserDietType" , newUser.getDietType());
                intent.putExtra("UserActivityFactor" , newUser.getActivityFactor());
                intent.putExtra("UserCaloriesAmount" , newUser.getCaloriesAmount());
                intent.putExtra("UserProteinAmount" , newUser.getProteinAmount());
                intent.putExtra("UserCarbsAmount" , newUser.getCarbsAmount());
                intent.putExtra("UserFatAmount" , newUser.getFatAmount());
                startActivity(intent);
                RegistryController.registryActivity.finish();
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "Użytkownik o takim adresie Email istenieje!" , Toast.LENGTH_LONG).show();
            }
        }
    }
}
