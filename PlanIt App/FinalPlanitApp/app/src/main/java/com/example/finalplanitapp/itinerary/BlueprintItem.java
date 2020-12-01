package com.example.finalplanitapp.itinerary;

public abstract class BlueprintItem implements ItineraryItem {

    protected Interval interval;

    public BlueprintItem(Interval interval) {
    	
        this.interval = interval;
    }

    public Interval getInterval() {
    	
        return this.interval;
    }
}
