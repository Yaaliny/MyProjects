package com.example.finalplanitapp.planit;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class FilterActivities {

	public static final String [] GOOGLE_TYPES = {"amusement_park", "aquarium", "art_gallery", "bakery", "bar", "bowling_alley", "book_store", 
			"cafe", "campground", "casino", "church", "library", "museum", "night_club", "park", "restaurant", 
			"shopping_mall", "synagogue", "tourist_attraction", "zoo", "stadium", "mosque", "movie_theater",
			"hindu_temple"};
	
	// Take a list of places and user choice of activities.
	public List<Hashtable<String, List<Place>>> ActivitiesFilter(
			List<Place> places, List<String> user_act_list) {
		
		Hashtable<String, List<Place>> type_place = new Hashtable<String, List<Place>>();
		Hashtable<String, List<Place>> user_choices = new Hashtable<String, List<Place>>();
		Hashtable<String, List<Place>> not_user_choices = new Hashtable<String, List<Place>>();
		List<Hashtable<String, List<Place>>> list_return = new ArrayList<Hashtable<String, List<Place>>>();
		
		type_place = organize_types(places);
		
		// Breaking places into two array list.
		// It should return 2 tables (User choice and not user choice).
		// loop on type_place by keys
		Enumeration<String> keys = type_place.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			// if key is one of the user activities then add to user choices table else the other table.
			if (user_act_list.contains(key)) {
				user_choices.put(key, type_place.get(key));
			} else {
				not_user_choices.put(key, type_place.get(key));
			}
		}

		list_return.add(user_choices);
		list_return.add(not_user_choices);
		return list_return;
	}

	public Hashtable<String, List<Place>> organize_types(List<Place> all_place) {

		Hashtable<String, List<Place>> type_table = new Hashtable<String, List<Place>>();
		List<String> google_types_lst = new ArrayList<String>();
		List<Place> temp_place_list = new ArrayList<Place>();
		List<String> place_type = new ArrayList<String>();

		// An array list for google types.
		for (String str : GOOGLE_TYPES) {
			google_types_lst.add(str);
		}

		// loop on all places.
		for (Place place : all_place) {
			//place_type = place.getTypes();
			
			for (String type : place_type) {
				
				// if this type is what we want.
				if (google_types_lst.contains(type)) {
					// if this key is already in the table.
					if (type_table.containsKey(type)) {
						// getting the places in the table of the type and add the new place in it.
						temp_place_list = type_table.get(type);
						temp_place_list.add(place);
						// replace it with the new list.
						type_table.replace(type, temp_place_list);

					} else { // else just add to the table.
						temp_place_list.add(place);
						type_table.put(type, temp_place_list);

					}

					// clear the list for next use.
					temp_place_list = new ArrayList<Place>();
				}
			}
		}
		return type_table;
	}

}
