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

public class Itin extends AppCompatActivity {

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
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        ArrayList<String> placesandtimes = new ArrayList<>();
        placesandtimes.add("CN Tower: 1:00pm - 2:00pm");
        placesandtimes.add("Ripley's Aquarium: 2:00pm - 5:00pm");
        placesandtimes.add("Mandarin: 5:00pm - 7:00pm");

        for(int i = 0; i < placesandtimes.size(); i++) {
            listDataHeader.add(placesandtimes.get(i));
        }

        /*
        // Adding child data
        listDataHeader.add("CN Tower: 1:00pm - 2:00pm");
        listDataHeader.add("Ripley's Aquarium: 2:00pm - 5:00pm");
        listDataHeader.add("Mandarin: 5:00pm - 7:00pm");
        */



        // Adding child data
        List<String> item1 = new ArrayList<String>();
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
        }
        /*
        listDataChild.put(listDataHeader.get(0), item1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), item2);
        listDataChild.put(listDataHeader.get(2), item3); */
    }
}
