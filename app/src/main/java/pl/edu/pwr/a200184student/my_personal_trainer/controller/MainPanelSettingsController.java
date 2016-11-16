package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import pl.edu.pwr.a200184student.my_personal_trainer.service.UserService;

public class MainPanelSettingsController extends AppCompatActivity {

    private User currentLoggedUser;
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
        collectUserData();
    }

    private void collectUserData() {
        Intent intent = getIntent();
        currentLoggedUser.setId(intent.getLongExtra("UserId",0));
        currentLoggedUser.setUserName(intent.getStringExtra("UserName"));
        currentLoggedUser.setEmail(intent.getStringExtra("UserEmail"));

    }

    private void initializeFields() {
        currentLoggedUser = new User();
        userNewEmailEditText = (EditText)findViewById(R.id.userNameEditText);
        userNewEmailEditText = (EditText)findViewById(R.id.emailEditText);
        userNewPasswordEditText = (EditText)findViewById(R.id.passwordEditText);
        updateAccountDataButton = (Button) findViewById(R.id.updateAccountDataButton);
        deleteUserButton = (Button)findViewById(R.id.deleteUserButton);
    }

    public void deleteAccount(View v){
        new AlertDialog.Builder(this)
                .setTitle("Usuwanie Użytkownika")
                .setMessage("Czy na pewno chcesz usunąć swoje konto ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            DeleteTask task = new DeleteTask();
                            task.execute((Void) null);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Problemy z połączeniem z serwerem :(" ,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }).create().show();
    }


    public class DeleteTask extends AsyncTask<Void, Void, User> {
        @Override
        protected User  doInBackground(Void... params) {
            try {
                UserService.deleteUser(currentLoggedUser.getId());
                return currentLoggedUser;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(User userAfterDelete) {
            startActivity(new Intent(MainPanelSettingsController.this, OnStartController.class));
            finish();
        }
    }

}
