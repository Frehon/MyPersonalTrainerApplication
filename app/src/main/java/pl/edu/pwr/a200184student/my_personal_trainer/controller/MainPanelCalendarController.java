package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;


import java.util.Calendar;

import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class MainPanelCalendarController extends AppCompatActivity {

    private CalendarView calendar;
    private Long userId;
    private int userCaloriesAmount;
    private int userProteinAmount;
    private int userCarbsAmount;
    private int userFatAmount;
    private long initialDate;
    private Calendar today;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calendar_view);
        calendar = (CalendarView)findViewById(R.id.calendar);
        initializeFields();
        prepareOnClickListener();
    }

    private void initializeFields() {
        Intent intentFromMainPanel = getIntent();
        userId = intentFromMainPanel.getLongExtra("userId" , 0);
        userCaloriesAmount = intentFromMainPanel.getIntExtra("userCaloriesAmount",0);
        userProteinAmount = intentFromMainPanel.getIntExtra("userProteinAmount",0);
        userCarbsAmount = intentFromMainPanel.getIntExtra("userCarbsAmount",0);
        userFatAmount = intentFromMainPanel.getIntExtra("userFatAmount",0);
        initialDate = calendar.getDate();
        today = Calendar.getInstance();
        selectedDate = String.valueOf(today.get(Calendar.DAY_OF_MONTH) +"-"+ (today.get(Calendar.MONTH) + 1)  +"-"+ today.get(Calendar.YEAR));
        startDetailActivity();
    }

    private void prepareOnClickListener() {
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if (view.getDate() == initialDate) {
                    return;
                }
                selectedDate = String.valueOf(dayOfMonth)+"-"+String.valueOf(month + 1)+"-"+String.valueOf(year);
                initialDate = view.getDate();
                startDetailActivity();
            }
        });
    }

    private void startDetailActivity() {
        Intent intent = new Intent(MainPanelCalendarController.this , MainPanelCalendarDetailController.class);
        intent.putExtra("SelectedDate" , selectedDate);
        intent.putExtra("userId" , userId);
        intent.putExtra("userCaloriesAmount" , userCaloriesAmount);
        intent.putExtra("userProteinAmount" , userCarbsAmount);
        intent.putExtra("userCarbsAmount" , userCarbsAmount);
        intent.putExtra("userFatAmount" , userFatAmount);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
