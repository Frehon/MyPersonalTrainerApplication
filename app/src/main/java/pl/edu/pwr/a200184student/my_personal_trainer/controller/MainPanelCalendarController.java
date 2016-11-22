package pl.edu.pwr.a200184student.my_personal_trainer.controller;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;


import pl.edu.pwr.a200184student.my_personal_trainer.R;

public class MainPanelCalendarController extends AppCompatActivity {

    private CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calendar_view);
        calendar = (CalendarView)findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = String.valueOf(dayOfMonth)+"-"+String.valueOf(month)+"-"+String.valueOf(year);
                Intent intent = new Intent(MainPanelCalendarController.this , MainPanelCalendarDetailController.class);
                Intent intentFromMainPanel = getIntent();
                intent.putExtra("SelectedDate" , selectedDate);
                intent.putExtra("userId" , intentFromMainPanel.getLongExtra("userId" , 0));
                intent.putExtra("userCaloriesAmount" , intentFromMainPanel.getIntExtra("userCaloriesAmount",0));
                intent.putExtra("userProteinAmount" , intentFromMainPanel.getIntExtra("userProteinAmount",0));
                intent.putExtra("userCarbsAmount" , intentFromMainPanel.getIntExtra("userCarbsAmount",0));
                intent.putExtra("userFatAmount" , intentFromMainPanel.getIntExtra("userFatAmount",0));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
