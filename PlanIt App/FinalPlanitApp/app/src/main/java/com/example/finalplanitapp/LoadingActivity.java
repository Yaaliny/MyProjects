package com.example.finalplanitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalplanitapp.dao.Config;
import com.example.finalplanitapp.dao.UserDAO;
import com.example.finalplanitapp.itinerary.Itinerary;
import com.example.finalplanitapp.itinerary.Journey;
import com.example.finalplanitapp.itinerary.Location;
import com.example.finalplanitapp.itinerary.Personalization;
import com.example.finalplanitapp.itinerary.PlanIt;
import com.example.finalplanitapp.planit.GoogleAPI;
import com.example.finalplanitapp.planit.Place;
import com.example.finalplanitapp.planit.PlaceManager;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LoadingActivity extends AppCompatActivity {

    TextView i;
    public static String finalitin;
    public static Journey returnedjourney;
    private ProgressBar progressBar;
    String date = new SimpleDateFormat("yyyy MM dd", Locale.getDefault()).format(new Date());
    public static List<Journey> journeys;
    public static Map<String, Journey> journeyMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        init();
        i = (TextView) findViewById(R.id.textView2);

        i.setText((DistanceCostActivity.distance * 1000) + "\n" + date + "\n" + StartEndLocation.location + "\n" + StartEndLocation.startTime.substring(0, StartEndLocation.startTime.length() - 3)+ "\n" + StartEndLocation.endTime + "\n" + ActivityAdapter.selectedactivities.get(0));
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();


    }

    private void init() {
        this.progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, 100000);
    }


    private class AsyncTaskRunner extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String res = null;
            // ArrayList<ItineraryItem> results = new ArrayList<>();

            String userlocation = "";

            if(StartEndLocation.location == "") {
                userlocation = "Toronto, Ontario";
            } else {
                userlocation = StartEndLocation.location;
            }


            Place start = null;
            try {
                start = GoogleAPI.getPlace(userlocation);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Place end = null;
            try {
                end = GoogleAPI.getPlace(userlocation);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            String currentdatestart = StartEndLocation.startTime;

            if(currentdatestart == "0:00 null") {
                currentdatestart = "2019 11 17 10:00:00";
            } else {
                currentdatestart = date + " " + currentdatestart.substring(0, currentdatestart.length() - 3) + ":00";
            }


            String currentdatesend = StartEndLocation.endTime;
            if(currentdatesend == "0:00 null") {
                currentdatesend = "2019 11 17 14:00:00";
            } else {
                currentdatesend = date + " " + currentdatesend.substring(0, currentdatesend.length() - 3) + ":00";
            }

            Date startDate = null;
            try {
                startDate = new SimpleDateFormat("yyyy MM dd HH:mm:ss").parse(currentdatestart);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date endDate = null;
            try {
                endDate = new SimpleDateFormat("yyyy MM dd HH:mm:ss").parse(currentdatesend);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            List<String> finalTypes = new ArrayList<String>();

            for(int x = 0; x < ActivityAdapter.selectedactivities.size(); x++) {
                if(ActivityAdapter.selectedactivities.get(x).contains("Amusement")) {
                    finalTypes.add("amusement_park");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Aquarium")) {
                    finalTypes.add("aquarium");
                } else if(ActivityAdapter.selectedactivities.get(x).equals("Park")) {
                    finalTypes.add("park");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Zoo")) {
                    finalTypes.add("zoo");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Book")) {
                    finalTypes.add("book_store");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Clothing") || ActivityAdapter.selectedactivities.get(x).contains("Department") || ActivityAdapter.selectedactivities.get(x).contains("Electronics") || ActivityAdapter.selectedactivities.get(x).contains("Florist") || ActivityAdapter.selectedactivities.get(x).contains("Jewelry") || ActivityAdapter.selectedactivities.get(x).contains("Shoe") || ActivityAdapter.selectedactivities.get(x).contains("Shopping")) {
                    finalTypes.add("shopping_mall");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Bakery")) {
                    finalTypes.add("bakery");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Cafe")) {
                    finalTypes.add("cafe");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Chinese") || ActivityAdapter.selectedactivities.get(x).contains("Japanese") || ActivityAdapter.selectedactivities.get(x).contains("Korean") || ActivityAdapter.selectedactivities.get(x).contains("French")) {
                    finalTypes.add("restaurant");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Bowling")) {
                    finalTypes.add("bowling_alley");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Campground")) {
                    finalTypes.add("campground");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Stadium")) {
                    finalTypes.add("stadium");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Art")) {
                    finalTypes.add("art_gallery");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Library")) {
                    finalTypes.add("library");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Museum")) {
                    finalTypes.add("museum");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Movie")) {
                    finalTypes.add("movie_theater");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Bar")) {
                    finalTypes.add("bar");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Casino") || ActivityAdapter.selectedactivities.get(x).contains("Liquor Store")) {
                    finalTypes.add("casino");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Tourist")) {
                    finalTypes.add("tourist_attraction");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Hindu")) {
                    finalTypes.add("hindu_temple");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Synagogue")) {
                    finalTypes.add("synagogue");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Mosque")) {
                    finalTypes.add("mosque");
                } else if(ActivityAdapter.selectedactivities.get(x).contains("Church")) {
                    finalTypes.add("church");
                }
            }

            if(finalTypes.size() == 0) {
                finalTypes.add("restaurant");
            }



            com.example.finalplanitapp.itinerary.Itinerary itinerary = new Itinerary(new Location(start, startDate), new Location(end, endDate));

            PlaceManager places = new PlaceManager.Builder()
                    .interestedIn(finalTypes)
                    .priceFilter(1)
                    //.touristFilter()
                    .build();

            // ADDED THIS
            //UserDAO user = new UserDAO(new Config());

           // Personalization personalize = new Personalization(user, places);
            Personalization personalize = new Personalization(places);


            List<Journey> journeys = new PlanIt(itinerary, personalize, DistanceCostActivity.distance * 1000, "driving").calculateJourneys();

            LoadingActivity.journeys = journeys;
            journeyMap = new HashMap<>();
            for (Journey journey : journeys) {
                journeyMap.put(journey.getId(), journey);
            }

            returnedjourney = journeys.get(0);

            res = journeys.get(0).toString();
            i.setText(res);
            finalitin = res;
            return res;

        }

        protected void onPostExecute(String result) {
            openActivity10();
        }


    }

    public void openActivity10() {
        //Intent intent = new Intent(this, com.example.finalplanitapp.Itinerary.class);
        Intent intent = new Intent(this, JourneyListActivity.class);
        startActivity(intent);
    }

}
