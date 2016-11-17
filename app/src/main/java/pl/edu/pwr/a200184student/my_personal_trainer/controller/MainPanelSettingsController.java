package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import pl.edu.pwr.a200184student.my_personal_trainer.service.UserService;
import pl.edu.pwr.a200184student.my_personal_trainer.util.UserUtil;

public class MainPanelSettingsController extends AppCompatActivity {

    private User currentLoggedUser;
    private EditText userNewNameEditText;
    private EditText userNewEmailEditText;
    private EditText userNewPasswordEditText;

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
        userNewNameEditText = (EditText)findViewById(R.id.userNameEditText);
        userNewEmailEditText = (EditText)findViewById(R.id.emailEditText);
        userNewPasswordEditText = (EditText)findViewById(R.id.passwordEditText);
    }

    public void updateAccountData(View v){
        String newName = userNewNameEditText.getText().toString();
        String newEmail = userNewEmailEditText.getText().toString();
        String newPassword = userNewPasswordEditText.getText().toString();
        boolean needToUpdate = false;
        if(!newName.isEmpty()){
            currentLoggedUser.setUserName(newName);
            needToUpdate = true;
        }
        if(!newEmail.isEmpty()){
            if(UserUtil.isEmailValid(newEmail)){
                currentLoggedUser.setEmail(newEmail);
                needToUpdate = true;
            }
            else{
                Toast.makeText(getApplicationContext(), "Niepoprawny format adresu Email!" , Toast.LENGTH_LONG).show();
            }
        }
        if(!newPassword.isEmpty()){
            // checking old password missing.
            if(UserUtil.isPasswordValid(newPassword)){
                currentLoggedUser.setPasswordHash(newPassword);
                needToUpdate = true;
            }
            else{
                Toast.makeText(getApplicationContext(), "Hasło powinno zawierać przynajmniej 8 znaków z czego przynajmniej jedną cyfrę!" , Toast.LENGTH_LONG).show();
            }
        }
        if(needToUpdate){
            UpdateAccountDataTask task = new UpdateAccountDataTask();
            task.execute((Void) null);
        }

    }

    public void deleteAccount(View v){
        new AlertDialog.Builder(this)
                .setTitle("Usuwanie Użytkownika")
                .setMessage("Czy na pewno chcesz usunąć swoje konto ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                            DeleteTask task = new DeleteTask();
                            task.execute((Void) null);
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
                return null;
            }
        }
        protected void onPostExecute(User userAfterDelete) {
            if(userAfterDelete == null){
                Toast.makeText(getApplicationContext(), "Problemy z połączeniem z serwerem :(" ,Toast.LENGTH_LONG).show();
            }
            startActivity(new Intent(MainPanelSettingsController.this, OnStartController.class));
            finish();
        }
    }

    public class UpdateAccountDataTask extends AsyncTask<Void,Void,User>{

        @Override
        protected User doInBackground(Void... params) {
            return UserService.updateUser(currentLoggedUser.getId() , currentLoggedUser);
        }
        protected void onPostExecute(User userAfterUpdate) {
            if(userAfterUpdate == null){
                Toast.makeText(getApplicationContext(), "Problemy z połączeniem z serwerem :(" ,Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Dane konta zostały zaktualizowane" ,Toast.LENGTH_LONG).show();
            }
        }
    }

}
