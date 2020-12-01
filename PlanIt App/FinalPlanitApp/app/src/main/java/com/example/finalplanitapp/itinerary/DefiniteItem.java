package com.example.finalplanitapp.itinerary;
import com.example.finalplanitapp.planit.Place;

public abstract class DefiniteItem implements ItineraryItem {

    protected Interval interval;
    protected Place place;

    public DefiniteItem(Place place, Interval interval){
    	
        this.interval = interval;
        this.place = place;
    }

    public Place getPlace() {
    	
        return this.place;
    }

    public Interval getInterval() {

        return this.interval;
    }
    
    public abstract int getAverageStayDuration();
}
