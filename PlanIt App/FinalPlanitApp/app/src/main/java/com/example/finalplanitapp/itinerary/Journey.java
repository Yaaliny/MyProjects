package com.example.finalplanitapp.itinerary;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.example.finalplanitapp.planit.GeographyUtils;
import com.example.finalplanitapp.planit.Place;

public class Journey {

	private List<DefiniteItem> schedule;
	
	public Journey(List<DefiniteItem> schedule) {
		this.schedule = schedule;
	}
	
	public List<DefiniteItem> getSchedule() {
		return this.schedule;
	}

	public double getAverageRating() {

		int totalRating = 0;
		int count = 0;

		for (DefiniteItem item : schedule) {
			double rating = item.getPlace().getRating();
			if (rating >= 0) {
				totalRating += rating;
				count++;
			}
		}

		if (count == 0)
			return -1;
		else
			return totalRating / (double)count;
	}


	public double getAverageCostLevel() {

		int totalCost = 0;
		int count = 0;

		for (DefiniteItem item : schedule) {
			int priceLevel = item.getPlace().getPriceLvl();
			if (priceLevel >= 0) {
				totalCost += priceLevel;
				count++;
			}
		}

		if (count == 0)
			return 0.0;
		else
			return totalCost / (double) count;
	}
	
	public int getScheduledDuration() {
		
		int milliseconds = 0;
		
		for (DefiniteItem item : schedule) {
			milliseconds += item.getInterval().getDuration();
		}
		
		return milliseconds;
	}
	
	public long getTotalDuration() {
		
		return getEndDate().getTime() - getStartDate().getTime();
	}
	
	public Date getStartDate() {
		
		Interval firstItem = schedule.get(0).getInterval();
		return firstItem.getStartDate();
	}
	
	public Date getEndDate() {
		
		Interval lastItem = schedule.get(0).getInterval();
		return lastItem.getEndDate();
	}
	
	@Override
	public String toString() {
		
		return Arrays.deepToString(schedule.toArray());
	}

	public String getId() {
		return "" + this.hashCode();
	}

	public String getTitle() {
		// TODO: generate something pithy here
		return "Journey";
	}

	public String getContent() {
		String content = "";
		for (int i = 0; i < schedule.size(); i++) {
			content += (i + 1) + ") " + schedule.get(i).getPlace().getName();
			content += "\n\t\t\t Rating: " + (int)Math.round(schedule.get(i).getPlace().getRating()) + " (" + schedule.get(i).getPlace().getNumberOfRatings() + ")";


			String priceLevelText = "N/A";
			switch (schedule.get(i).getPlace().getPriceLvl()) {
				case 0:
					priceLevelText = "Free!";
					break;
				case 1:
					priceLevelText = "Low";
					break;
				case 2:
					priceLevelText = "Medium";
					break;
				case 3:
					priceLevelText = "High";
					break;
				case 4:
					priceLevelText = "Very High";
					break;
			}

			content += "\n\t\t\t Price Level: " + priceLevelText;
			if (i != schedule.size() - 1)
				content += "\n\n";
		}
		return content;
	}
}
