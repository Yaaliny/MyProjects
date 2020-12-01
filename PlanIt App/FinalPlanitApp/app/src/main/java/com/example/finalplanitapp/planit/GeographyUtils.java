package com.example.finalplanitapp.planit;

import java.util.List;

public class GeographyUtils {
	
	private static final double earthsRadius = 6371.0;
	
	private static final int travelTimeBuffer = 5;	// 5 minute buffer
	private static final double travelTimeErrorMultiplier = 2.0;

	public static double getAverageTravelSpeed(String travelMode) {
		
		switch (travelMode) {
			case "driving":
				return 50.0;
			case "walking":
				return 3.6;
			case "bicycling":
				return 20.0;
			case "transit":
				return 25.0;
			default:
				throw new IllegalArgumentException("Invalid travel mode.");
		}
	}
	
	public static int[][] calculateTravelTimeMatrix(Place[] places, double averageKilometersPerHour) {
		
		double averageKilometersPerMinute = averageKilometersPerHour / 60.0;
		
		double[][] distanceMatrix = calculateDistanceMatrix(places);
		int[][] travelTimeMatrix = new int[distanceMatrix.length][distanceMatrix.length]; 
		
		for (int x = 0; x < places.length; x++) {		
			Place a = places[x];
			for (int y = 0; y < places.length; y++) {
				Place b = places[y];
				
				if (x < y)
					travelTimeMatrix[x][y] = travelTimeBuffer + (int)Math.round(distanceMatrix[x][y] / averageKilometersPerMinute * travelTimeErrorMultiplier);
				else if (x > y)
					travelTimeMatrix[x][y] = travelTimeMatrix[y][x];
			}
		}
		
		return travelTimeMatrix;
	}
	
	
	public static double[][] calculateDistanceMatrix(Place[] places) {
	
		double[][] distanceMatrix = new double[places.length][places.length];
		
		for (int x = 0; x < places.length; x++) {
			
			Place a = places[x];
			
			for (int y = 0; y < places.length; y++) {
				
				Place b = places[y];
				
				if (x < y)
					distanceMatrix[x][y] = toManhattanDistance(getGreatCircleDistanceInKM(a, b));
				else if (x > y)
					distanceMatrix[x][y] = distanceMatrix[y][x];
				else
					distanceMatrix[x][y] = 0.0;
			}
		}
		
		return distanceMatrix;
	}
	
	public static double toManhattanDistance(double distance) {
		
		return distance * Math.cos(Math.PI / 4.0) + distance * Math.sin(Math.PI / 4.0);
	}
	
	/*
	 * Calculates distance between two places using the Haversine formula. Based on:
	 * https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
	 */
	public static double getGreatCircleDistanceInKM(Place p1, Place p2) {
		
		double dLat = Math.toRadians(p2.getLatitude() - p1.getLatitude());
		double dLng = Math.toRadians(p2.getLongitude() - p1.getLongitude());
		
		double a = Math.pow(Math.sin(dLat / 2.0), 2) +
				Math.cos(Math.toRadians(p1.getLatitude())) * Math.cos(Math.toRadians(p2.getLatitude())) *
				Math.pow(Math.sin(dLng / 2.0), 2);
		
		double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return earthsRadius * c;
	}
}
