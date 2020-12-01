package com.example.finalplanitapp;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
// import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import com.example.finalplanitapp.ExpandableListAdapter;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class Itinerary extends AppCompatActivity {

    public ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itin);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        Button button = (Button)findViewById(R.id.map_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });
    }

    public void openMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        //LoadingActivity.returnedjourney.getSchedule().get(0).getPlace().getName();

        /*ArrayList<String> placesandtimes = new ArrayList<>();
        placesandtimes.add("CN Tower: 1:00pm - 2:00pm");
        placesandtimes.add("Ripley's Aquarium: 2:00pm - 5:00pm");
        placesandtimes.add("Mandarin: 5:00pm - 7:00pm"); */
        ArrayList<String> placesandtimes = new ArrayList<>();
       for(int i = 0; i < JourneyListActivity.journey.getSchedule().size(); i++) {
           listDataHeader.add(JourneyListActivity.journey.getSchedule().get(i).getPlace().getName() + " " + JourneyListActivity.journey.getSchedule().get(i).getInterval());
           List<String> item1 = new ArrayList<String>();
           item1.add("Address: " + JourneyListActivity.journey.getSchedule().get(i).getPlace().getAddress());
           item1.add("Average Stay Time: " + JourneyListActivity.journey.getSchedule().get(i).getAverageStayDuration());
           item1.add("Rating: " + JourneyListActivity.journey.getSchedule().get(i).getPlace().getRating());
           double budgetlevel = JourneyListActivity.journey.getSchedule().get(i).getPlace().getPriceLvl();
           if(budgetlevel == 1.0) {
               item1.add("Budget Level: " + "Inexpensive" );
           } else if(budgetlevel == 2.0) {
               item1.add("Budget Level: " + "Moderate" );
           } else if(budgetlevel == 3.0) {
               item1.add("Budget Level: " + "Expensive");
           } else if(budgetlevel == 4.0) {
               item1.add("Budget Level: " + "Very Expensive" );
           } else {
               item1.add("Budget Level: " + "N/A");
           }
           listDataChild.put(listDataHeader.get(i), item1);
       }


        /*for(int i = 0; i < placesandtimes.size(); i++) {
            listDataHeader.add(placesandtimes.get(i));
        }*/

        /*
        // Adding child data
        listDataHeader.add("CN Tower: 1:00pm - 2:00pm");
        listDataHeader.add("Ripley's Aquarium: 2:00pm - 5:00pm");
        listDataHeader.add("Mandarin: 5:00pm - 7:00pm");
        */



        // Adding child data
        /* List<String> item1 = new ArrayList<String>();
        item1.add("Price: $25");
        item1.add("Travel By: Car");
        item1.add("Time it takes to travel here: 1 hour");
        item1.add("Rating: 3.0");


        List<String> item2 = new ArrayList<String>();
        item2.add("Price: $50");
        item2.add("Travel By: Car");
        item2.add("Time it takes to travel here: 10 minutes");
        item2.add("Rating: 4.0");

        List<String> item3 = new ArrayList<String>();
        item3.add("Price: $30");
        item3.add("Travel By: Car");
        item3.add("Time it takes to travel here: 2 hours");
        item3.add("Rating: 2.0");


        ArrayList<List<String>> items = new ArrayList<>();

        items.add(item1);
        items.add(item2);
        items.add(item3);

        for(int j = 0; j < items.size(); j++) {
            listDataChild.put(listDataHeader.get(j), items.get(j));
        } */
        /*
        listDataChild.put(listDataHeader.get(0), item1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), item2);
        listDataChild.put(listDataHeader.get(2), item3); */
    }
}
