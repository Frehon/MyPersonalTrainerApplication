package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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

import pl.edu.pwr.a200184student.my_personal_trainer.model.DietService;
import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class registry_detail_controller extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private HashMap<String,String> newUserData;
    private NumberPicker weightNumberPicker;
    private NumberPicker heightNumberPicker;
    private TextView weightTextView;
    private TextView heightTextView;
    private CheckBox balanceGoalCheckBox;
    private CheckBox massCheckBox;
    private CheckBox lossCheckBox;
    private Spinner activityFactorSpinner;
    private Button finishRegistrationButton;
    private Button infoButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registry_detail_view);
        // grabbing user data from previous activity
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
        weightTextView = (TextView)findViewById(R.id.weightTextView);
        heightTextView = (TextView)findViewById(R.id.heightTextView);
        balanceGoalCheckBox = (CheckBox)findViewById(R.id.balanceGoalCheckBox);
        massCheckBox = (CheckBox)findViewById(R.id.massCheckBox);
        lossCheckBox = (CheckBox)findViewById(R.id.lossCheckBox);
        activityFactorSpinner = (Spinner)findViewById(R.id.activityFactorSpinner);
        infoButton = (Button)findViewById(R.id.infoButton);
        finishRegistrationButton = (Button)findViewById(R.id.finishRegistrationButton);
    }

    private void preparePickers() {
        weightNumberPicker.setMaxValue(150);
        weightNumberPicker.setMinValue(40);
        weightNumberPicker.setValue(70);
        heightNumberPicker.setMaxValue(225);
        heightNumberPicker.setMinValue(135);
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
        // spinner drop down list
        ArrayList<String> factorValues = new ArrayList<String>();
        factorValues.add("1.2");
        factorValues.add("1.4");
        factorValues.add("1.6");
        factorValues.add("1.8");
        factorValues.add("2.0");

        // creating adapter for spinner
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line , factorValues);
        activityFactorSpinner.setAdapter(spinner_adapter);
    }

    // gender spinner method.
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
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
                startActivity(new Intent(registry_detail_controller.this , info_controller.class));
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
            newUserData.put("Diet_Type" , "Balanced");
        }
        else{
            if(massCheckBox.isChecked()){
                newUserData.put("Diet_Type" , "Mass");
            }
            else{
                if(lossCheckBox.isChecked()){
                    newUserData.put("Diet_Type" , "Loss");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Nie Wybrano Celu " , Toast.LENGTH_LONG).show();
                }
            }
        }
        //  count calories and calories schedule
        HashMap<String,String> dietMap = DietService.prepareDiet(newUserData.get("Diet_Type") , newUserData.get("Weight") , newUserData.get("Height") , newUserData.get("Gender") , newUserData.get("Birth_Year") , newUserData.get("ActivityFactor"));
        newUserData.putAll(dietMap);
        // creating new user ....
        // moving to main Panel ....
        Intent intent = new Intent(registry_detail_controller.this, main_panel_controller.class);
        startActivity(intent);
        registry_controller.registryActivity.finish();
        finish();
    }
}
