package com.example.finalplanitapp.planit;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;


import com.example.finalplanitapp.itinerary.Event;
import com.example.finalplanitapp.itinerary.Interval;
import com.example.finalplanitapp.itinerary.Itinerary;
import com.example.finalplanitapp.itinerary.Journey;
import com.example.finalplanitapp.itinerary.Location;
import com.example.finalplanitapp.itinerary.Personalization;
import com.example.finalplanitapp.itinerary.PlanIt;

public class Algorithm {

	public static void main(String[] args) throws ParseException, IOException, JSONException {

		/*
		Place start = GoogleAPI.getPlace("Toronto, Ontario");
		Place end = GoogleAPI.getPlace("Toronto, Ontario");
		
		Date startDate = new SimpleDateFormat("yyyy MM dd HH:mm:ss").parse("2019 11 17 10:00:00");
		Date endDate = new SimpleDateFormat("yyyy MM dd HH:mm:ss").parse("2019 11 17 14:00:00");
		
		Itinerary itinerary = new Itinerary(new Location(start, startDate), new Location(end, endDate));
		
		PlaceManager places = new PlaceManager.Builder()
								.interestedIn(Arrays.asList("restaurant", "library"))
								.priceFilter(1)
								//.touristFilter()
								.build();
		
		Personalization personalize = new Personalization(places);
		
		System.out.println("Finding journeys...");
		List<Journey> journeys = new PlanIt(itinerary, personalize, 10000, "driving").calculateJourneys();
		System.out.println("Done!");
		
		for (int i = 0; i < journeys.size(); i++) {
			System.out.println(journeys.get(i).toString());
		}
		*/
	}

}
