package com.example.finalplanitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;

public class StartEndLocation extends AppCompatActivity {

    Button save;
    EditText locationEditText;
    public static String location;
    public static String startTime;
    public static String endTime;

    Button startTimeButton, endTimeButton;
    TextView startTimeTextView, endTimeTextView;
    int startHour, startMinute, endHour, endMinute;
    String start_am_pm, end_am_pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_end_location);

        // all the objects we have on screen
        save = (Button) findViewById(R.id.saveButton);
        locationEditText = (EditText) findViewById(R.id.locationEditText);

        startTimeButton = findViewById(R.id.startTimeButton);
        startTimeTextView = findViewById(R.id.startTimeTextView);

        endTimeButton = findViewById(R.id.endTimeButton);
        endTimeTextView = findViewById(R.id.endTimeTextView);

        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog startTimePickerDialog = new TimePickerDialog(StartEndLocation.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int j) {
                        startHour = i;
                        startMinute = j;
                        if (startHour > 12) {
                            start_am_pm = "PM";
                            startHour = startHour - 12;
                        } else {
                            start_am_pm = "AM";
                        }
                        String startTimeString = "Start time is " + startHour + ":" + new DecimalFormat("00").format(startMinute) + " " + start_am_pm;
                        startTimeTextView.setText(startTimeString);
                    }
                }, startHour, startMinute, true);
                startTimePickerDialog.show();
            }
        });

        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog endTimePickerDialog = new TimePickerDialog(StartEndLocation.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int j) {
                        endHour = i;
                        endMinute = j;
                        if (endHour > 12) {
                            end_am_pm = "PM";
                            endHour = endHour - 12;
                        } else {
                            end_am_pm = "AM";
                        }
                        String endTimeString = "End time is " + endHour + ":" + new DecimalFormat("00").format(endMinute) + " " + end_am_pm;
                        endTimeTextView.setText(endTimeString);
                    }
                }, endHour, endMinute, true);
                endTimePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = locationEditText.getText().toString();
                startTime = startHour + ":" + new DecimalFormat("00").format(startMinute) + " " + start_am_pm;
                endTime = endHour + ":" + new DecimalFormat("00").format(endMinute) + " " + end_am_pm;
                openActivity3();
            }
        });
    }

    public void openActivity3() {
        Intent intent = new Intent(StartEndLocation.this, DistanceCostActivity.class);
        startActivity(intent);
    }
}