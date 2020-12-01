package com.example.finalplanitapp.itinerary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Itinerary {

	private static final int MIN_ITEM_DURATION = 30;
	
	private Location start, end;
	
	private List<ItineraryItem> itinerary;
	
	public Itinerary(Location start, Location end) {
		
		itinerary = new ArrayList<ItineraryItem>();
		
		this.start = start;
		this.end = end;
	}
	
	public Location getStartLocation() {	
		return this.start;
	}
	
	public Location getEndLocation() {
		return this.end;
	}
	
	public void setItem(ItineraryItem item, Interval interval) 
	{
		return;
	}
	
	public void addItem(ItineraryItem item) {
		
		// TODO: check that the item is after the start and before the end
		
		itinerary.add(item);
	}
	
	public ItineraryItem getNextItem(Date date) {
		
		// Note: !before so it can be equal
		
		for (int i = 0; i < itinerary.size(); i++) {
			ItineraryItem item = itinerary.get(i);
			if (!item.getInterval().getStartDate().before(date)) {
				return item;
			}
		}
		
		if (!end.getInterval().getStartDate().before(date))
			return end;
		
		return null;
	}
	
	public ItineraryItem getItem(Interval interval) {
		
		for (int i = 0; i < itinerary.size(); i++) {
			ItineraryItem item = itinerary.get(i);
			if (item.getInterval().contains(interval)) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		
		String str = "";
		for (ItineraryItem item : itinerary) {
			str += item.toString() + "\n";
		}
		return str;
	}
	
	public Journey build() {
		// Create schedule class???
		
		DefiniteItem[] defItinerary = new DefiniteItem[itinerary.size()];
		for (int i = 0; i < itinerary.size(); i++) {
			ItineraryItem item = itinerary.get(i);
			if (item instanceof BlueprintItem) {
				//defItinerary[i] = ((BlueprintItem)item).build();
			}
			else if (item instanceof DefiniteItem) {
				defItinerary[i] = (DefiniteItem)item;
			}
			else {
				// TODO: THROW EXCEPTION
				return null;
			}
		}
		return null;
	}
}
