package com.example.finalplanitapp.itinerary;

import java.util.Date;

import com.example.finalplanitapp.planit.Place;

public class Location extends DefiniteItem {

    public Location(Place place, Date date) {
        super(place, new Interval(date));
    }

    @Override
    public String toString() {
    	
    	return interval.toString() + " : " + place.getName();
    }
	
	@Override
	public int getAverageStayDuration() {
		return 0;
	}
}
