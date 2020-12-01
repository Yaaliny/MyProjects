package com.example.finalplanitapp.planit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;

public class PlaceManager {
	
	private Set<String> blacklist, neutral, interested;
	private boolean freeOnly;
	private int targetPrice;
	
	private PlaceManager(Set<String> blacklist, Set<String> neutral, Set<String> interested) {
		this.blacklist = blacklist;
		this.neutral = neutral;
		this.interested = interested;
	}
	
	public void setTargetPrice(int price) {
		this.targetPrice = price;
	}
	
	public int getTargetPrice() {
		return this.targetPrice;
	}
	
	public void setFreeOnly(boolean v) {
		this.freeOnly = v;
	}
	
	public boolean isNeutral(String type) {
		return this.neutral.contains(type);
	}

	public boolean isBlacklisted(String type) {
		return this.blacklist.contains(type);
	}
	
	public boolean isInterested(String type) {
		return this.interested.contains(type);
	}
	
	public boolean isRestaurant(List<String> types) {
		
		// TODO: make this more efficient
		return !Collections.disjoint(types, Arrays.asList("meal_takeaway", "restaurant"));
	}
	
	public Set<Place> filter(String originAddress, int searchRadius) {

		Set<Place> places = new HashSet<Place>();
		
		for (String type : interested) {
			try {
				List<Place> matches = GoogleAPI.DataExtraction(type, searchRadius, originAddress);
				places.addAll(matches);
			}
			catch (IOException | JSONException e) {
				System.out.printf("Failed to get places for type: " + type + "\nException: \n");
				e.printStackTrace();
				continue;
			}
		}
		
		for (String type : neutral) {
			try {
				List<Place> matches = GoogleAPI.DataExtraction(type, searchRadius, originAddress);
				for (int i = 0; i < Math.min(10, matches.size()); i++) {
					places.add(matches.get(i));
				}
			}
			catch (IOException | JSONException e) {
				System.out.printf("Failed to get places for type: " + type + "\nException: \n");
				e.printStackTrace();
				continue;
			}
		}
		
		if (this.targetPrice != -1)
			places.removeIf(place -> place.getPriceLvl() > this.targetPrice + 1);
		
		if (this.freeOnly)
			places.removeIf(place -> place.getPriceLvl() > 0 || place.getPriceLvl() == -1);
		
		// Remove blacklisted places
		places.removeIf(place -> !Collections.disjoint(place.getTypes(), blacklist));
		
		return places;
	}
	
	public static class Builder {
		
		// Places with even a single type in the blacklist are removed
		private Set<String> blacklist, neutral, interested;
		private boolean freeOnly = false;
		private int targetPrice = -1;
		
		public Builder() {

			interested = new HashSet<String>();
			
			// Create the blacklist with default values
			blacklist = new HashSet<String> (Arrays.asList(
					 "airport", "atm", "bank", "accounting", "bicycle_store", "bus_station", "car_dealer", 
					 "car_rental", "car_repair", "car_wash", "cemetery", "dentist", "embassy", "electrician", 
					 "doctor", "courthouse", "fire_station", "funeral_home", "gas_station", "hair_care", 
					 "insurance_agency", "home_goods_store", "travel_agency", "university", "taxi_stand", 
					 "train_station", "storage", "transit_station", "secondary_school", "roofing_contractor", 
					 "rv_park", "real_estate_agency", "primary_school", "plumber", "police", "physiotherapist", 
					 "school", "post_office", "pharmacy", "local_government_office", "moving_company", "movie_rental", 
					 "locksmith", "lodging", "light_rail_station", "lawyer", "laundry", "hospital"));
			
			// Create the interested with default values
			neutral = new HashSet<String> (Arrays.asList("zoo", "tourist_attraction", "shopping_mall", 
					"park", "museum", "cafe", "bakery", "art_gallery", "aquarium", "amusement_park", 
					"restaurant", "point_of_interest"));
			
		}
		
		public Builder interestedIn(List<String> types) {
			
			interested.addAll(types);
			
			return this;
		}
		
		/*
		 * Removes places that cost more than the target price plus 1
		 */
		public Builder priceFilter(int targetPriceLevel) {
			
			this.targetPrice = targetPriceLevel;
			return this;
		}
		
		/*
		 * Removes typical touristy types
		 */
		public Builder touristFilter() {
			
			interested.add("tourist_attraction");
			interested.add("point_of_interest");
			return this;
		}
		
		/*
		 * Removes any places that cost money. Places that
		 * have -1 for their price level are level in
		 */
		public Builder freeFilter() {
			
			this.freeOnly = true;
			return this;
		}
		
		/*
		 * Removes age-restricted places
		 */
		public Builder ageRestrictedFilter() {
			
			blacklist.add("liquor_store");
			blacklist.add("drugstore");
			blacklist.add("casino");
			blacklist.add("night_club");
			blacklist.add("bar");
			return this;
		}
		
		public PlaceManager build() {
			
			// Get rid of intersections between the sets
			neutral.removeAll(blacklist);
			interested.removeAll(blacklist);
			neutral.removeAll(interested);
			
			PlaceManager a = new PlaceManager(blacklist, neutral, interested);
			a.setFreeOnly(this.freeOnly);
			a.setTargetPrice(targetPrice);
			
			return a;
		}
	}
}
