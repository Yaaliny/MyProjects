package com.example.finalplanitapp;

import com.example.finalplanitapp.itinerary.Interval;
import com.example.finalplanitapp.planit.Place;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class PlaceUtils {

    private static final Interval MORNING = new Interval(
            new GregorianCalendar(2019, 0, 0, 6, 0, 0).getTime(),
            new GregorianCalendar(2019, 0, 0, 11, 0, 0).getTime());

    private static final Interval LUNCH = new Interval(
            new GregorianCalendar(2019, 0, 0, 11, 0, 0).getTime(),
            new GregorianCalendar(2019, 0, 0, 14, 0, 0).getTime());

    private static final Interval AFTERNOON = new Interval(
            new GregorianCalendar(2019, 0, 0, 13, 0, 0).getTime(),
            new GregorianCalendar(2019, 0, 0, 18, 0, 0).getTime());

    private static final Interval NIGHT = new Interval(
            new GregorianCalendar(2019, 0, 0, 20, 0, 0).getTime(),
            new GregorianCalendar(2019, 0, 0, 23, 0, 0).getTime());

    public static int getAverageStayDuration(Place place) {

        int duration = 60;
        if (!Collections.disjoint(
                Arrays.asList("cafe", "bakery"),
                place.getTypes()))
            duration = 30;
        else if (!Collections.disjoint(
                Arrays.asList("art_gallery", "shopping_mall", "library"),
                place.getTypes()))
            duration = 60;
        else if (!Collections.disjoint(
                Arrays.asList("restaurant", "aquarium", "night_club", "bar"),
                place.getTypes()))
            duration = 60 + 30;
        else if (!Collections.disjoint(
                Arrays.asList("zoo", "amusement_park", "casino"),
                place.getTypes()))
            duration = 60 + 60;

        return duration;
    }

    public static boolean isIdealTime(String type, Date time) {

        if (MORNING.contains(time)) {
            return Arrays.asList("restaurant", "cafe", "bakery").contains(type);
        }
        if (LUNCH.contains(time)) {
            return Arrays.asList("restaurant").contains(type);
        }
        if (AFTERNOON.contains(time)) {
            return Arrays.asList("restaurant", "").contains(type);
        }
        if (NIGHT.contains(time)) {
            return Arrays.asList("night_club", "bar").contains(type);
        }

        return false;
    }
}
