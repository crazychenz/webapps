/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework9;

import com.rbevans.bookingrate.BookingDay;
import com.rbevans.bookingrate.Rates;
import com.vagries1.homework5.bindings.BhcConfig;
import com.vagries1.homework5.bindings.Hike;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Model object that is responsible for handing the BHC hike cost calculations and data.
 *
 * @author Vincent Agriesti
 */
public class Model {

    protected static final String DEFAULT_CONFIG =
            "/com/vagries1/homework5/bindings/bhcConfigDefault.xml";

    BhcConfig config;

    /** Model constructor. Responsible for loading the BhcConfig. */
    public Model() {
        InputStream in = null;
        in = this.getClass().getResourceAsStream(DEFAULT_CONFIG);
        config = BhcConfig.unmarshallStream(in);

        if (config == null) {
            System.out.println("ERROR: Failed to load bhcConfig.xml data.");
        }
    }

    /**
     * Function responsible for calculating the estimate result.
     *
     * @param request The HttpServletRequest used for fetching request parameters.
     * @return Returns the result string.
     */
    public String estimate(String hikersStr, String dateStr, String durationStr, String hikeName) {
        final String CURRENCY_FORMAT = "#.00";
        final String DATE_FMT = "MM/dd/yyyy";

        BookingDay startDay = null;
        BookingDay todayDay = null;
        Rates rates = null;
        Calendar today;

        Calendar reqDate;
        int hikers;
        int year;
        int month;
        int date;
        int duration;
        double cost;
        String info;
        DecimalFormat df;

        try {
            hikers = Integer.parseInt(hikersStr);
            if (hikers < 1 || hikers > 10) {
                return "Hikers must be between 1 and 10";
            }
        } catch (Exception e) {
            return "Invalid hikers format provided.";
        }

        today = new GregorianCalendar();

        try {
            reqDate = new GregorianCalendar();
            reqDate.setTime(new SimpleDateFormat(DATE_FMT).parse(dateStr));
            month = reqDate.get(Calendar.MONTH) + 1;
            year = reqDate.get(Calendar.YEAR);
            date = reqDate.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            return "Invalid date format provided.";
        }

        try {
            duration = Integer.parseInt(durationStr);
            if (duration < 2 || duration > 7) {
                return "Duration value to low. Duration must be >= 2 and <= 7.";
            }

            for (Hike hike : config.getHikes()) {
                if (hike.getKey().compareTo(hikeName) == 0) {
                    if (!hike.getDurationList().contains(duration)) {
                        return "Invalid duration for hike.";
                    }
                }
            }

        } catch (Exception e) {
            return "Invalid duration format provided.";
        }

        try {
            rates = new Rates(Rates.HIKE.valueOf(hikeName));
        } catch (Exception e) {
            return "Invalid hike value provided.";
        }

        startDay = new BookingDay(year, month, date);

        // Check that date is future (based on date)
        todayDay =
                new BookingDay(
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH) + 1,
                        today.get(Calendar.DATE));
        if (!startDay.after(todayDay)) {
            return "Start date must be no earlier than tomorrow.";
        }

        rates.setBeginDate(startDay);
        rates.setDuration(duration);

        cost = rates.getCost();
        info = rates.getDetails();

        if (cost < 0) {
            return info;
        }

        df = new DecimalFormat(CURRENCY_FORMAT);
        info =
                "Cost Per Hiker: $"
                        + df.format(cost)
                        + " / Total Cost: $"
                        + df.format(cost * hikers);
        return info;
    }

    /**
     * Generate options
     *
     * @param current The currently selected option
     * @param map The map of options
     * @return The serialized list of options
     */
    public String genOptions(String current, Map<String, String> map) {
        String result = null;
        LinkedList<String> lines = new LinkedList<String>();

        for (String key : map.keySet()) {
            String selected = "";
            if (current != null && current.compareTo(key) == 0) {
                selected = "selected";
            }
            lines.add(
                    "<option value=\"" + key + "\" " + selected + ">" + map.get(key) + "</option>");
        }
        result = String.join("\n", lines);
        return result;
    }

    /**
     * Retrieve options for number of hikers.
     *
     * @param current The currently selected value of hikers
     * @return The hiker number options
     */
    public String getHikersOptions(String current) {
        final int MIN_HIKERS = 1;
        final int MAX_HIKERS = 10;

        HashMap<String, String> map;
        String result;

        // Hiker count should be between 1 and 10
        map = new LinkedHashMap<String, String>();
        for (int i = MIN_HIKERS; i <= MAX_HIKERS; ++i) {
            map.put(Integer.toString(i), Integer.toString(i));
        }

        if (current == null) {
            current = Integer.toString(MIN_HIKERS);
        }

        result = genOptions(current, map);
        return result;
    }

    /**
     * Retrieve options for hikes.
     *
     * @param current The currently selected hike
     * @return The hike options
     */
    public String getHikeOptions(String current) {
        HashMap<String, String> map;
        String result;

        // Dynamically determine hikes from config.xml
        map = new LinkedHashMap<String, String>();
        for (Hike hike : config.getHikes()) {
            map.put(hike.getKey(), hike.getName());
        }

        result = genOptions(current, map);
        return result;
    }

    /**
     * Retrieve options for hike durations.
     *
     * @param current The currently selected hike duration
     * @return The hike duration options
     */
    public String getDurationOptions(String current) {
        final int MIN_DURATION = 2;
        final int MAX_DURATION = 7;

        HashMap<String, String> map;
        String result;

        // Just statically doing all durations for simplicity. (No javascript is annoying.)
        map = new LinkedHashMap<String, String>();
        for (int i = MIN_DURATION; i <= MAX_DURATION; ++i) {
            map.put(Integer.toString(i), Integer.toString(i));
        }

        result = genOptions(current, map);
        return result;
    }
}
