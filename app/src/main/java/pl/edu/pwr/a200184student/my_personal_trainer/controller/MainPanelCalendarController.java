package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }

    private void prepareOnClickListener() {
        calendar.setOnTouchListener(new View.OnTouchListener() {
            private static final int MAX_CLICK_DURATION = 200;
            private long startClickTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        if(clickDuration < MAX_CLICK_DURATION) {
                            Toast.makeText(getApplicationContext() , "teteeete" , Toast.LENGTH_LONG).show();
                        }
                    }
                }
                return true;
            }
        });
        /*
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = String.valueOf(dayOfMonth)+"-"+String.valueOf(month)+"-"+String.valueOf(year);
                Intent intent = new Intent(MainPanelCalendarController.this , MainPanelCalendarDetailController.class);
                intent.putExtra("SelectedDate" , selectedDate);
                intent.putExtra("userId" , userId);
                intent.putExtra("userCaloriesAmount" , userCaloriesAmount);
                intent.putExtra("userProteinAmount" , userCarbsAmount);
                intent.putExtra("userCarbsAmount" , userCarbsAmount);
                intent.putExtra("userFatAmount" , userFatAmount);
                startActivity(intent);
            }
        });
        */
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
