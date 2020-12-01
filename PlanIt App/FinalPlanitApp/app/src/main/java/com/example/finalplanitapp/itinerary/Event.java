package com.example.finalplanitapp.itinerary;

import com.example.finalplanitapp.PlaceUtils;
import com.example.finalplanitapp.planit.Place;

public class Event extends DefiniteItem {

    public Event(Place place, Interval interval) {
        super(place, interval);
    }

    @Override
    public String toString() {
    	
    	return interval.toString() + " : " + place.getName();
    }
	
	@Override
	public int getAverageStayDuration() {
		return PlaceUtils.getAverageStayDuration(place);
	}
}
