package pl.edu.pwr.a200184student.my_personal_trainer.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pl.edu.pwr.a200184student.my_personal_trainer.R;
import pl.edu.pwr.a200184student.my_personal_trainer.model.User;
import pl.edu.pwr.a200184student.my_personal_trainer.service.UserService;
import pl.edu.pwr.a200184student.my_personal_trainer.util.UserUtil;


public class LogInController extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private static User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_view);
        loggedUser = new User();
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (password.matches("") || !UserUtil.isPasswordValid(password)) {
            mPasswordView.setError("Hasło musi zawierać minimum 8 znaków w tym przynajmniej jedną cyfrę");
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("To pole jest wymagane");
            focusView = mEmailView;
            cancel = true;
        }
        else if (!UserUtil.isEmailValid(email)) {
            mEmailView.setError("Niepoprawny format adresu email.");
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {}

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {}

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private Boolean accessCompleted;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                loggedUser = UserService.log_in(mEmail,mPassword);
                accessCompleted = loggedUser == null ? false : true;
                Thread.sleep(2000);
            } catch (Exception e) {
                return false;
            }
            return accessCompleted;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                Intent intent = new Intent(LogInController.this , MainPanelController.class);
                intent.putExtra("UserId" , loggedUser.getId());
                intent.putExtra("UserName" , loggedUser.getUserName());
                intent.putExtra("UserBirthYear" , loggedUser.getBirthYear());
                intent.putExtra("UserGender" , loggedUser.getGender());
                intent.putExtra("UserEmail", loggedUser.getEmail());
                intent.putExtra("UserWeight" , loggedUser.getWeight());
                intent.putExtra("UserHeight" , loggedUser.getHeight());
                intent.putExtra("UserDietType" , loggedUser.getDietType());
                intent.putExtra("UserActivityFactor" , loggedUser.getActivityFactor());
                intent.putExtra("UserCaloriesAmount" , loggedUser.getCaloriesAmount());
                intent.putExtra("UserProteinAmount" , loggedUser.getProteinAmount());
                intent.putExtra("UserCarbsAmount" , loggedUser.getCarbsAmount());
                intent.putExtra("UserFatAmount" , loggedUser.getFatAmount());
                startActivity(intent);
                finish();
            } else {
                mPasswordView.setError("Podane dane są nieprawidłowe , bądź użytkownik o podanych danych nie istenieje!");
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

