package com.example.finalplanitapp.itinerary;

import android.app.ActivityManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;

import com.example.finalplanitapp.LoginActivity;
import com.example.finalplanitapp.PlaceUtils;
import com.example.finalplanitapp.planit.FilterActivities;
import com.example.finalplanitapp.planit.GoogleAPI;
import com.example.finalplanitapp.planit.Place;
import com.example.finalplanitapp.planit.PlaceManager;
import com.example.finalplanitapp.dao.UserDAO;

public class Personalization {
	
	private static final double TRAVEL_WEIGHT = 1.0;
	private static final double VISITED_TYPE_WEIGHT = 1.0;
	private static final double LIKE_WEIGHT = 0.0;
	private static final double DISLIKE_WEIGHT = -0.0;
	private static final double COST_WEIGHT = 0.0;
	private static final double INTEREST_WEIGHT = 3.0;
	private static final double POPULARITY_WEIGHT = 0.0;
	private static final double RATING_WEIGHT = 1.0;
	private static final double VISITED_WEIGHT = 0.0;
	private static final double SIMILAR_TYPE_WEIGHT = -4.0;
	private static final double TIME_WEIGHT = 1.0;
	
	private PlaceManager places;
	private UserDAO user;
	
	private Set<String> liked, disliked;
	
	public Personalization(PlaceManager places) {
		this.liked = new HashSet<>();
		this.disliked = new HashSet<>();
		//this.user = user;
		//this.liked = user.getLikedPlaceIds(LoginActivity.emailString);
		//this.disliked = user.getDislikedPlaceIds(LoginActivity.emailString);
		this.places = places;
	}
	
	
	public Place[] getPlaceCandidates(Place origin, Place destination, int searchRadius) {
		/*
		 * Returns a list of potential events the user would want to
		 * do, based off the location, radius, start time, end time
		 * preferences (i.e. If they selected "Kid Friendly", the bars
		 * would be removed from the result.
		 */

		Set<Place> candidates = places.filter(origin.getAddress(), searchRadius);
		
		// TODO: remove places that have too high of a negative node score
		
		// Add the origin and destination if they aren't already in there
		List<Place> candidatesList = new ArrayList<Place>(candidates);
		candidatesList.add(0, origin);
		candidatesList.add(candidatesList.size() - 1, destination);

		return (Place[])candidatesList.toArray(new Place[0]);
	}
	
	public double getEdgeScore(int travelTime, Set<String> visitedTypes, Set<String> types) {
		// TODO: take in the interval
		/*
		 * Return a score based on transportation time and distance
		 * between the items.
		 */
		
		// TODO: return false if the establishment is not open during that
		// interval
		
		
		// TODO: modify score based on 
		if (false)
			return Double.NEGATIVE_INFINITY;
		
		double transportationScore = TRAVEL_WEIGHT * 1.0 / (1.0 + travelTime);
		
		double typeScore = 0.0;
		for (String type : types) {
			if (visitedTypes.contains(type))
				typeScore += VISITED_TYPE_WEIGHT;
			else
				typeScore -= VISITED_TYPE_WEIGHT;
		}
		
		return transportationScore + typeScore;
	}
	
	public double getNodeScore(Place place) {
		/*
		 * Return a user rating of the event based on whether they have
		 * liked that event, or similar, in the past. It also takes into
		 * account the cost of the event compared to the target cost, 
		 * among other things.
		 */
		
		
		// TODO: score base on how fitting of a time it is to do the activity
		// i.e. high score for lunch at around 12:00pm
		
		//
		
		double interestScore = 0;
		for (String type : place.getTypes()) {
			if (places.isInterested(type)) {
				interestScore += INTEREST_WEIGHT;
			}
		}
		
		Set<String> types = place.getTypes();
		double likeScore = 0.0;
		for (String type : types) {
			if (liked.contains(type)) {
				likeScore += LIKE_WEIGHT;
			}
			else if(disliked.contains(type)) {
				likeScore += DISLIKE_WEIGHT;
			}
		}

		double costScore = COST_WEIGHT * Math.abs(place.getPriceLvl() - places.getTargetPrice());
		
		double popScore = place.getNumberOfRatings() * POPULARITY_WEIGHT;
		
		double ratingScore = place.getRating() * RATING_WEIGHT;



		return interestScore + likeScore + costScore + popScore + ratingScore;
	}
	
	public double getPathScore(Place place, Interval interval, Set<String> visitedTypes) {
		
		int similarElements = 0;
		for (String type : visitedTypes) {
			if (place.getTypes().contains(type)) {
				similarElements++;
			}
		}
		double typeScore = SIMILAR_TYPE_WEIGHT * similarElements / (double)place.getTypes().size();

		double timeScore = 0.0;
		for (String type : place.getTypes()) {
			if (PlaceUtils.isIdealTime(type,interval.getStartDate())) {
				timeScore += TIME_WEIGHT;
			}
			else {
				timeScore -= TIME_WEIGHT;
			}
		}
		
		return typeScore + timeScore;
	}
	
	public HashMap<Place, Double> precomputeNodeScores(Place[] places) {
		
		HashMap<Place, Double> placeToNodeScore = new HashMap<>();
		for (Place place : places) {
			placeToNodeScore.put(place, getNodeScore(place));
		}
		return placeToNodeScore;
	}
	
	public double getPathSimilarity(Set<Place> a, Set<Place> b) {
		
		int similarElements = 0;
		
		for (Place place : b) {
			if (a.contains(place)) {
				similarElements++;
			}
		}
		
		return similarElements / (double)b.size();
	}
}
