package com.example.finalplanitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Button;

public class DateActivity extends AppCompatActivity {
    private CalendarView myCalendar;
    Button dateButton;
    TextView myDate;
    public static String date = "";

    //public  static UserFilterInput userfilters = new UserFilterInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        dateButton = (Button) findViewById(R.id.button);
        myCalendar = (CalendarView) findViewById(R.id.calendarView);
        myCalendar.setMinDate(System.currentTimeMillis() - 1000);

        date = Long.toString(myCalendar.getDate());

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // finds calendar date we are and saves it to date variable
                myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        date = (year + 1) + "/" + month + "/" + dayOfMonth;
                        //date = "";
                        //userfilters.setDate(date);
                    }
                });

                // calls on open activity to open the next screen
                openActivity2();
            }
        });
    }

    public void openActivity2() {
        Intent intent = new Intent(this, StartEndLocation.class);
        startActivity(intent);
    }
}