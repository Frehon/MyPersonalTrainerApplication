package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class MainPanelSettingsController extends AppCompatActivity {

    private EditText userNewNameEditText;
    private EditText userNewEmailEditText;
    private EditText userNewPasswordEditText;
    private Button updateAccountDataButton;
    private Button deleteUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_panel_settings_view);
        initializeFields();
    }

    private void initializeFields() {
        userNewEmailEditText = (EditText)findViewById(R.id.userNameEditText);
    }
}
