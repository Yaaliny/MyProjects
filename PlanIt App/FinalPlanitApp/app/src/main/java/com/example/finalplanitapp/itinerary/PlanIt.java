package com.example.finalplanitapp.itinerary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import com.example.finalplanitapp.planit.GeographyUtils;
import com.example.finalplanitapp.planit.Place;

public class PlanIt {
	
	class Node {
		
		private int index;
		private DefiniteItem item;
		private double accumulatedScore;
		private Node previous;
		
		private Set<Place> visitedPlaces;
		private Set<String> visitedTypes;
		
		public double getScore() {
			return this.accumulatedScore;
		}
		
		public void setScore(double score) {
			this.accumulatedScore = score;
		}
		
		public void addVisitedPlace(Place place) {
			this.visitedPlaces.add(place);
		}
		
		public void addVisitedTypes(List<String> types) {
			this.visitedTypes.addAll(types);
		}
		
		public Node(int index, DefiniteItem item) {
			
			this.index = index;
			this.item = item;
			this.accumulatedScore = 0;
			this.previous = null;
			
			visitedPlaces = new HashSet<>();
			visitedTypes = new HashSet<>();
			
			visitedPlaces.add(item.getPlace());
		}
		
		public Node branch(int index, DefiniteItem item, double score) {
			
			Node node = new Node(index, item);
			
			node.accumulatedScore = this.accumulatedScore + score;
			node.previous = this;
			
			node.visitedPlaces.addAll(this.visitedPlaces);
			node.visitedPlaces.add(item.getPlace());
			
			node.visitedTypes.addAll(this.visitedTypes);
			node.visitedTypes.addAll(item.getPlace().getTypes());
			
			return node;
		}
	}
	
	private Itinerary itinerary;
	private Personalization personalize;
	private int searchRadius;
	private String travelMode;
	
	public PlanIt(Itinerary itinerary, Personalization personalize, int searchRadius, String travelMode) {
		
		this.itinerary = itinerary;
		this.personalize = personalize;
		this.searchRadius = searchRadius;
		this.travelMode = travelMode;
	}
	
	public List<Journey> calculateJourneys() {
		
		// TODO: move these somewhere else
		int numOfCandidates = 50;
		int stayTime = 30 * 60 * 1000;
		int numOfPaths = 3;
		int maxTravelTime = 30;
		
		// Step 1:
		// Get a bunch of places to potentially use for the journey
		Place origin = itinerary.getStartLocation().getPlace();
		Place destination = itinerary.getEndLocation().getPlace();
		Place[] candidates = personalize.getPlaceCandidates(origin, destination, searchRadius);

		// Step 2:
		// Pre-compute all possible values
		HashMap<Place, Double> placeToNodeScore = personalize.precomputeNodeScores(candidates);
		double speed = GeographyUtils.getAverageTravelSpeed(travelMode);
		int[][] travelTimeMatrix = GeographyUtils.calculateTravelTimeMatrix(candidates, speed);
		
		// Step 3:
		// Find paths between the origin and destination
		
		// TODO: Store information about when a node branched from another node in order
		// to calculate similariy and fill paths with dissimilar paths
		
		// Create a priority queue prioritizing nodes with a
		// high score
		PriorityQueue<Node> q = new PriorityQueue<Node>(
			new Comparator<Node>() {
				public int compare(Node n1, Node n2) {
					if (n1.accumulatedScore > n2.accumulatedScore)
						return -1;
					else if (n1.accumulatedScore < n2.accumulatedScore)
						return 1;
					else
						return 0;
				}
			}
		);
	
		// Store the paths found in this list
		List<Node> paths = new ArrayList<Node>();
		// Insert the start location as the first node
		q.add(new Node(0, itinerary.getStartLocation()));
		
		// Path finding algorithm based on Dijkstra
		while (!q.isEmpty() && paths.size() < numOfPaths) {
			
			// Remove the highest priority node from the priority queue
			Node node = q.remove();
			
			// If the node is the destination, then this is a valid path
			if (node.item.getPlace().equals(destination)) {
				boolean tooSimilar = false;
				// Check this path isn't too similar to another one
				for (Node path : paths) {
					if (personalize.getPathSimilarity(path.visitedPlaces, node.visitedPlaces) > 0.50) {
						tooSimilar = true;
					}
				}
				if (tooSimilar)
					continue;
				paths.add(node);
				// Remove potential paths too similar to this one
				q.removeIf(n -> personalize.getPathSimilarity(node.visitedPlaces, n.visitedPlaces) > 0.50);
				continue;	// Keep looking for paths (we want more than just one)
			}
			
			// Get the next scheduled item in the itinerary
			// Generally this will just be the destination location
			ItineraryItem item = itinerary.getNextItem(node.item.getInterval().getEndDate());
			
			for (int i = 0; i < candidates.length; i++) {
				
				Place place = candidates[i];
				
				// Skip this place if we have been here before or we are 
				if (place.equals(node.item.getPlace()) || node.visitedPlaces.contains(place))
					continue;
				
				// Calculate travel time in milliseconds from the previous node
				// to this one
				int travelTime = travelTimeMatrix[node.index][i];
				//long travelTime = Math.round(travelTime * 60.0 * 60.0 * 1000.0);

				// Skip this candidate if the travel time is unreasonably high
				if (travelTime > maxTravelTime)
					continue;
				
				// Calculate when we would arrive at this event from the previous event
				// and when we would leave it
				Date arrivalTime = new Date(node.item.getInterval().getEndDate().getTime() + travelTime * 60 * 1000);
				Date leaveTime = new Date(arrivalTime.getTime() + place.getAverageStayTime() * 60 * 1000);
				Interval interval = new Interval(arrivalTime, leaveTime);
				
				// Ensure that we have enough time to do this event before the next
				// scheduled event in the itinerary
				if (!item.getInterval().getStartDate().before(leaveTime)) {
					
					// Calculate scores
					double edgeScore = personalize.getEdgeScore(travelTime, node.visitedTypes, place.getTypes());
					double nodeScore = placeToNodeScore.get(place);
					double pathScore = personalize.getPathScore(place, interval, node.visitedTypes);
					double score = pathScore + edgeScore + nodeScore;
					
					// TODO: exit here if the score is sufficiently low???
					
					// TODO: get rid of this check and make it more integral to
					// how the algorithm s structured
					if (!place.equals(destination)) {
						DefiniteItem newItem = new Event(place, interval);
						q.add(node.branch(i, newItem, score));		
					}
					else {
						q.add(node.branch(i, itinerary.getEndLocation(), score));
					}
				}
			}
		}
		
		// Step 5:
		// Instantiate Journey objects using the above paths and
		// return them
		List<Journey> journeys = new ArrayList<>(paths.size());
		for (int i = 0; i < paths.size(); i++) {
			List<DefiniteItem> schedule = new ArrayList<>();
			Node current = paths.get(i).previous;
			while (current.previous != null) {
				schedule.add(current.item);
				current = current.previous;
			}
			journeys.add(new Journey(schedule));
		}
		
		return journeys;
	}
}
