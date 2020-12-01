package com.example.finalplanitapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DistanceCostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static int distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_cost);

        Spinner transportationSpinner = (Spinner) findViewById(R.id.transportationSpinner);
        final TextView distanceText = (TextView) findViewById(R.id.distanceText);
        final SeekBar distanceBar = (SeekBar) findViewById(R.id.seekBar);
        Spinner priceLevelSpinner = (Spinner) findViewById(R.id.priceLevelSpinner);
        Button preferencesButton = (Button) findViewById(R.id.preferencesButton);

        ArrayAdapter<CharSequence> transportationAdapter = ArrayAdapter.createFromResource(this, R.array.transportationModes, android.R.layout.simple_spinner_item);
        transportationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transportationSpinner.setAdapter(transportationAdapter);
        transportationSpinner.setOnItemSelectedListener(this);


        distanceText.setText("Kilometres to travel: " + distanceBar.getProgress() + "/ " + distanceBar.getMax());

        distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value = progress;
                distance = progress;
                distanceText.setText("Kilometres to travel: " + progress + "/ " + distanceBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ArrayAdapter<CharSequence> priceLevelAdapter = ArrayAdapter.createFromResource(this, R.array.priceLevels, android.R.layout.simple_spinner_item);
        priceLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceLevelSpinner.setAdapter(priceLevelAdapter);
        // position 2 is the moderate price level
        priceLevelSpinner.setSelection(2);
        priceLevelSpinner.setOnItemSelectedListener(this);

        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivities();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        // String text = adapterView.getItemAtPosition(position).toString();
        // Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void openActivities() {
        Intent intent = new Intent(this, Activities.class);
        startActivity(intent);
    }
}
