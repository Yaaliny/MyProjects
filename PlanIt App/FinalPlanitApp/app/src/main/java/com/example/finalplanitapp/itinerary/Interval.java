package com.example.finalplanitapp.itinerary;

import android.icu.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Interval {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, HH:mm:");
	
    private Date start, end;
    private long duration;

    public Interval(Date start) {

        this.start = start;
        this.end = start;
        this.duration = 0;
    }
    
    public Interval(Date start, int duration) {

        this.start = start;
        this.end = new Date(start.getTime() + duration);
        this.duration = duration;
    }

    public Interval(Date start, Date end) {

        this.start = start;
        this.end = end;

        if (end != null)
            this.duration = end.getTime() - start.getTime();
        else {
        	this.end = start;
            this.duration = 0;
        }
    }
    
    public Date getStartDate() {
    	
    	return start;
    }
    
    public Date getEndDate() {
    	
    	return end;
    }
    
    public long getDuration() {
    	
    	return duration;
    }
    
    public boolean contains(Interval interval) {
    	
    	if (start.before(interval.getStartDate()) || start.equals(interval.getStartDate())) {
    		if (end.after(interval.getEndDate()) || end.equals(interval.getEndDate())) {
    			return true;
    		}
    	}
    	return false;
    }

    public boolean contains(Date date) {

        java.util.Calendar startOfDay = GregorianCalendar.getInstance();
        java.util.Calendar c = GregorianCalendar.getInstance();

        c.setTime(date);
        long time = c.getTimeInMillis() - startOfDay.getTimeInMillis();
        c.setTime(start);
        long startTime = c.getTimeInMillis() - startOfDay.getTimeInMillis();
        c.setTime(end);
        long endTime = c.getTimeInMillis() - startOfDay.getTimeInMillis();

        if (startTime <= time) {
            if (endTime >= time) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
    	
    	return timeFormat.format(start) + " - " + timeFormat.format(end);
    }
}
