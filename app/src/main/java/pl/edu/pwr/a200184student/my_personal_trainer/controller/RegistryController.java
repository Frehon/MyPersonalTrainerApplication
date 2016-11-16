package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.Calendar;
import java.util.HashMap;
import android.graphics.Color;
import android.widget.Toast;

import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.util.UserUtil;

public class RegistryController extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private HashMap<String,String> newUserData;
    public static Activity registryActivity;
    private Calendar calendar ;
    private Spinner gender_spinner;
    private Button set_birthDate_button;
    private EditText first_name_edit_text;
    private EditText last_name_edit_text;
    private EditText email_edit_text;
    private EditText confirm_email_edit_text;
    private EditText password_edit_text;
    private EditText confirm_password_edit_text;
    private DatePickerDialog date_picker_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registry_view);
        newUserData = new HashMap<String,String>();
        registryActivity = this;
        prepare_listeners_and_adapters();
        initialize_other_fields();
    }

    public void prepare_listeners_and_adapters() {
        prepare_gender_spinner();
        prepare_birth_date_picker();
    }

    private void initialize_other_fields() {
        first_name_edit_text = (EditText)findViewById(R.id.firstName_edit_text);
        last_name_edit_text = (EditText)findViewById(R.id.last_name_edit_text);
        email_edit_text = (EditText)findViewById(R.id.email_edit_text);
        confirm_email_edit_text = (EditText)findViewById(R.id.confirm_email_edit_text);
        password_edit_text = (EditText)findViewById(R.id.password_edit_text);
        confirm_password_edit_text = (EditText)findViewById(R.id.confirm_password_edit_text);
    }


    private void prepare_gender_spinner() {
        gender_spinner = (Spinner)findViewById(R.id.gender_spinner);
        gender_spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.gender_array,R.layout.spinner_center_item);
        adapter.setDropDownViewResource(R.layout.spinner_center_item);
        gender_spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selected_gender = adapterView.getItemAtPosition(i).toString();
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) adapterView.getChildAt(0)).setTextSize(20);
        ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.DEFAULT_BOLD);
        if(selected_gender.equals("Kobieta")){
            newUserData.put("Gender","K");
        }
        else{
            if(selected_gender.equals("Mężczyzna")) {
                newUserData.put("Gender", "M");
            }
            else{
                newUserData.put("Gender", "Wybierz Płeć");
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void prepare_birth_date_picker(){
        calendar = Calendar.getInstance();
        set_birthDate_button = (Button)findViewById(R.id.set_birthDate_button);
        set_birthDate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_picker_dialog = new DatePickerDialog(RegistryController.this , date_picker_listener , 1980 , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
                date_picker_dialog.getDatePicker().setCalendarViewShown(false);
                date_picker_dialog.getDatePicker().setSpinnersShown(true);
                date_picker_dialog.show();

            }
            DatePickerDialog.OnDateSetListener date_picker_listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    i1 = i1 + 1;
                    set_birthDate_button.setText(i2+" / "+i1+" / "+i);
                    set_birthDate_button.setTextSize(20);
                    newUserData.put("BirthYear" , String.valueOf(i));
                }
            };
        });
    }

    public void gather_user_data(View view) {

        String user_first_name = first_name_edit_text.getText().toString().trim();;
        String user_last_name = last_name_edit_text.getText().toString().trim();
        String user_email_adress = email_edit_text.getText().toString().trim();
        String confirmed_user_email_adress = confirm_email_edit_text.getText().toString().trim();
        String user_password = password_edit_text.getText().toString().trim();
        String user_confirmed_password = confirm_password_edit_text.getText().toString().trim();

        if (newUserData.containsKey("Gender") == false || newUserData.get("Gender").equals("Wybierz Płeć")) {
            Toast.makeText(getApplicationContext(), "Wybierz swoja płeć", Toast.LENGTH_LONG).show();
        } else {
            if (!(newUserData.containsKey("BirthYear"))) {
                Toast.makeText(getApplicationContext(), "Brak wypełnionego pola z datą urodzin.", Toast.LENGTH_LONG).show();
            } else {
                if (user_first_name.isEmpty() || user_last_name.isEmpty() || user_first_name.equals("Imię") || user_last_name.equals("Nazwisko")) {
                    Toast.makeText(getApplicationContext(), "Brak wypełnionego pola z imieniem lub nazwiskiem.", Toast.LENGTH_LONG).show();
                } else {
                    newUserData.put("FirstName", user_first_name);
                    newUserData.put("LastName", user_last_name);
                    if (user_email_adress.isEmpty() || confirmed_user_email_adress.isEmpty() || user_email_adress.equals("Adres Email") || confirmed_user_email_adress.equals("Potwierdź Adres Email")) {
                        Toast.makeText(getApplicationContext(), "Brak wypełnionego pola z adresem email.", Toast.LENGTH_LONG).show();
                    } else {
                        if (!UserUtil.checkEmailAdresses(user_email_adress, confirmed_user_email_adress)) {
                            Toast.makeText(getApplicationContext(), "Adresy Email nie są identyczne lub mają zły format !", Toast.LENGTH_LONG).show();
                        } else {
                            newUserData.put("Email", user_email_adress);
                            if (user_password.isEmpty() || user_confirmed_password.isEmpty() || user_password.equals("Nowe Hasło") || user_confirmed_password.equals("Potwierdź Nowe Hasło")) {
                                Toast.makeText(getApplicationContext(), "Brak wypełnionego pola z hasłem.", Toast.LENGTH_LONG).show();
                            } else {
                                if (!UserUtil.checkPasswords(user_password, user_confirmed_password)) {
                                    Toast.makeText(getApplicationContext(), "Hasła nie są identyczne , bądź nie spełniają wymagań 8 znaków w tym minimum jednej cyfry! ", Toast.LENGTH_LONG).show();
                                } else {
                                    newUserData.put("PasswordHash" , user_password);
                                    Intent intent = new Intent(this, RegistryDetailController.class);
                                    intent.putExtra("map", newUserData);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void onBackPressed() {
        startActivity(new Intent(RegistryController.this , OnStartController.class));
        finish();
    }
}

