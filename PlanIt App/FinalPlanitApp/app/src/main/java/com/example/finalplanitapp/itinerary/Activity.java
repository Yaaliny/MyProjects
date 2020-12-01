package com.example.finalplanitapp.itinerary;

public class Activity extends BlueprintItem {

    private String type;

    public Activity(String type, Interval interval) {
        super(interval);
        this.type = type;
    }
    
    @Override
    public String toString() {
    	
    	return interval.toString() + " : " + type;
    }
}
