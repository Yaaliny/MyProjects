package com.example.finalplanitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AfterActivities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_activities);

        TextView activitiesView = (TextView) findViewById(R.id.radiusTextView);

        // date
        // location
        // startTime
        // endTime

        // transportationType
        // progress_value
        // willingTravelDistance

        openItinerary();
    }

    public void openItinerary() {
        Intent intent = new Intent(AfterActivities.this, LoadingActivity.class);
        startActivity(intent);
    }
}