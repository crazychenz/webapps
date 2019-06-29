/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

import com.rbevans.bookingrate.BookingDay;
import com.rbevans.bookingrate.Rates;
import com.vagries1.homework5.bindings.BhcConfig;
import com.vagries1.homework5.bindings.Hike;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Backend and business logic class object for the BHC Hike Calculator.
 *
 * <p>The design assumes that there is some kind of presentation logic that calls back into this
 * object whenever its fields or state changes.
 *
 * @author Vincent Agriesti
 */
public class BhcEstimator {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(BhcEstimator.class);

    protected BhcConfig config;
    protected Vector<String> validDurations;
    protected Vector<String> validDates;
    protected Vector<String> validMonths;
    protected Vector<String> validHikes;
    protected Vector<String> validYears;

    protected HashMap<String, Rates.HIKE> hikeEnumMap;
    protected HashMap<String, Hike> hikeMap;
    protected HashMap<String, Integer> monthMap;

    // Track values as indicies of vectors.
    protected int monthIdx;
    protected int dateIdx;
    protected int yearIdx;
    protected int durationIdx;
    protected int hikeIdx;
    protected Hike hike;

    protected boolean estimated = false;
    protected double estimate = 0.0;
    protected String estimateInfo = null;

    /**
     * Constructor for business logic class of the hike estimate calculator.
     *
     * @param config User options to present
     */
    public BhcEstimator(BhcConfig config) {
        this.config = config;

        // 1. Create valid hikes from bhcConfig.xml and Rates
        hikeMap = new HashMap<String, Hike>();
        hikeEnumMap = new HashMap<String, Rates.HIKE>();
        validHikes = new Vector<String>();
        initValidHikes();

        // 2. Create valid durations based on hike.
        validDurations = new Vector<String>();
        updateValidDurations();

        // 3. Generate month vector from Calendar object
        validMonths = new Vector<String>();
        monthMap = new HashMap<String, Integer>();
        initValidMonths();

        // 4. Create valid years from bhcConfig.xml
        validYears = new Vector<String>();
        initValidYears();

        // 5. Create valid dates based on current month and year.
        validDates = new Vector<String>();
        updateValidDates();

        estimated = false;
        estimate = 0.0;
        estimateInfo = "";
    }

    /**
     * Indicates if a valid estimate has been calculated.
     *
     * @return Flag of estimate validity.
     */
    public boolean hasEstimate() {
        return estimated;
    }

    /**
     * Accessor for current estimate value.
     *
     * <p>User should use hasEstimate() to determine if this data is valid.
     *
     * @return Estimate as a real number value. -0.01 indicates an error.
     */
    public double getEstimate() {
        return estimate;
    }

    /**
     * Accessor for extra details of the estimate calculation.
     *
     * @return Message returned by estimate calculator class.
     */
    public String getEstimateInfo() {
        return estimateInfo;
    }

    /** Primary method to trigger an estimate calculation. */
    public void estimate() {
        BookingDay startDay;
        Rates rates = null;

        // We're assuming that our internal state is up to date when
        // this function is called.
        int year = Integer.parseInt(validYears.get(yearIdx));
        int month = this.monthFromName(validMonths.get(monthIdx)) + 1;
        int date = Integer.parseInt(validDates.get(dateIdx));
        int duration = Integer.parseInt(validDurations.get(durationIdx));

        logger.debug(String.format("Y %d M %d D %d Dur %d", year, month, date, duration));
        startDay = new BookingDay(year, month, date);
        rates = new Rates(Rates.HIKE.valueOf(hike.getKey()));
        rates.setBeginDate(startDay);
        rates.setDuration(duration);

        estimate = rates.getCost();
        estimated = estimate < 0 ? false : true;
        estimateInfo = rates.getDetails();
    }

    /**
     * Method to update duration values when another field potentially changes the possible duration
     * entries. (i.e. Hike)
     */
    private void updateValidDurations() {
        validDurations.removeAllElements();
        for (Integer duration : hike.getDurationList()) {
            if (duration == null) {
                logger.error("Found invalid duration entry. Skipping.");
                continue;
            }
            validDurations.add(duration.toString());
        }

        if (validDurations.size() > 0) {
            // Set index 0 as default value.
            resetDuration();
        }
    }

    /**
     * Accessor for valid durations.
     *
     * @return Valid durations as a Vector of Strings.
     */
    public Vector<String> getValidDurations() {
        return validDurations;
    }

    /** A clean reset of the duration index without throwing exceptions. */
    public void resetDuration() {
        durationIdx = 0;
    }

    /**
     * Sets the duration index value.
     *
     * @param index The index value to set duration to.
     * @throws IllegalArgumentException When the duration index is invalid.
     */
    public void setDurationIdx(int index) throws IllegalArgumentException {
        if (index < 0 || index >= getValidDurations().size()) {
            resetDuration();
            throw new IllegalArgumentException("bad duration");
        }
        this.durationIdx = index;
    }

    /**
     * Fetch the duration index.
     *
     * @return Duration index as an int.
     */
    public int getDurationIdx() {
        return durationIdx;
    }

    /**
     * Method to update dates values when another field potentially changes the possible date
     * entries. (i.e. Year or Month)
     */
    private void updateValidDates() {
        int month;
        int year;

        if (validMonths.size() == 0) {
            logger.error("No valid months loaded.");
            throw new IllegalArgumentException("bad months");
        }

        if (validYears.size() == 0) {
            logger.error("No valid years loaded.");
            throw new IllegalArgumentException("bad years");
        }

        month = monthFromName(validMonths.get(monthIdx)) + 1;
        year = Integer.parseInt(validYears.get(yearIdx));
        validDates.removeAllElements();
        for (int i = 1; i < getMaxDate(month, year) + 1; ++i) {
            validDates.add(Integer.toString(i));
        }
        // Set index 0 as default value.
        resetDate();
    }

    /**
     * Accessor for valid dates.
     *
     * @return Valid dates as a Vector of Strings.
     */
    public Vector<String> getValidDates() {
        return validDates;
    }

    /**
     * Fetch the date index.
     *
     * @return Date index as an int.
     */
    public int getDateIdx() {
        return dateIdx;
    }

    /**
     * Calculates the number of days in a month given the month and year.
     *
     * <p>Note: This is more accurate than BookingDay code, but there is zero impact in the
     * difference because the Feburary never falls within a given season.
     *
     * @param month The month to get date count. 1-Jan thru 12-Dec.
     * @param year The year of the month. For leap years and the like.
     * @return The number of days in the month of the year.
     */
    private int getMaxDate(int month, int year) {
        Calendar calendar = new GregorianCalendar(year, month - 1, 1);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }

    /** A clean reset of the date index without throwing exceptions. */
    public void resetDate() {
        dateIdx = 0;
    }

    /**
     * Sets the date index value.
     *
     * @param index The index value to set date to.
     * @throws IllegalArgumentException When the date index is invalid.
     */
    public void setDateIdx(int index) throws IllegalArgumentException {
        if (index < 0 || index >= getValidDates().size()) {
            resetDate();
            throw new IllegalArgumentException("bad date");
        }
        this.dateIdx = index;
    }

    /** Method to initialize month values during construction. */
    private void initValidMonths() {
        Calendar cal;
        int minMonth;
        int maxMonth;

        cal = Calendar.getInstance();

        try {
            minMonth = config.getAppointmentRange().getMinMonthEnum().ordinal();
        } catch (IllegalArgumentException e) {
            logger.error("Min month is not valid Month.");
            logger.debug("", e);
            throw new IllegalArgumentException("bad min month");
        }

        try {
            maxMonth = config.getAppointmentRange().getMaxMonthEnum().ordinal();
        } catch (IllegalArgumentException e) {
            logger.error("Max month is not valid Month.");
            logger.debug("", e);
            throw new IllegalArgumentException("bad max month");
        }

        for (int i = minMonth; i < maxMonth; ++i) {
            String month;
            cal.set(1, i, 1);
            month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
            validMonths.add(month);
            monthMap.put(month, i);
        }
        // Set index 0 as default value.
        resetMonth();
    }

    /**
     * Accessor for valid months.
     *
     * @return Valid months as a Vector of Strings.
     */
    public Vector<String> getValidMonths() {
        return validMonths;
    }

    /**
     * Translates the SHORT name of a month back to the Calendar const value of the given month.
     *
     * @param name Short name of the month. (Jan, Feb, ..., Nov, Dec)
     * @return The integer value of the Calendar const value or -1 on error.
     */
    protected int monthFromName(String name) {
        Calendar cal = Calendar.getInstance();
        for (int i = Calendar.JANUARY; i <= Calendar.DECEMBER; ++i) {
            String month;
            cal.set(1, i, 1);
            month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
            if (month.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /** A clean reset of the month index without throwing exceptions. */
    public void resetMonth() {
        monthIdx = 0;
        if (validDates != null) {
            updateValidDates();
        }
    }

    /**
     * Sets the month index value.
     *
     * @param index The index value to set month to.
     * @throws IllegalArgumentException When the month index is invalid.
     */
    public void setMonthIdx(int index) throws IllegalArgumentException {
        if (index < 0 || index >= getValidMonths().size()) {
            resetMonth();
            throw new IllegalArgumentException("bad month");
        }

        monthIdx = index;

        // Since month changed, date options may have changed.
        updateValidDates();
    }

    /**
     * Fetch the month index.
     *
     * @return Month index as an int.
     */
    public int getMonthIdx() {
        return monthIdx;
    }

    /** Method to initialize year values during construction. */
    private void initValidYears() {
        int minYear = config.getAppointmentRange().getMinYear();
        int maxYear = config.getAppointmentRange().getMaxYear();
        for (int i = minYear; i < maxYear + 1; ++i) {
            validYears.add(Integer.toString(i));
        }
        // Set index 0 as default value.
        // yearIdx = Integer.parseInt(validYears.firstElement());
        resetYear();
    }

    /**
     * Accessor for valid years.
     *
     * @return Valid years as a Vector of Strings.
     */
    public Vector<String> getValidYears() {
        return validYears;
    }

    /** A clean reset of the year index without throwing exceptions. */
    public void resetYear() {
        yearIdx = 0;
        if (validDates != null) {
            updateValidDates();
        }
    }

    /**
     * Sets the year index value.
     *
     * @param index The index value to set year to.
     * @throws IllegalArgumentException When the year index is invalid.
     */
    public void setYearIdx(int index) throws IllegalArgumentException {
        if (index < 0 || index >= getValidYears().size()) {
            resetYear();
            throw new IllegalArgumentException("bad year");
        }
        this.yearIdx = index;

        // Since year changed, date options may have changed.
        updateValidDates();
    }

    /**
     * Fetch the year index.
     *
     * @return Year index as an int.
     */
    public int getYearIdx() {
        return yearIdx;
    }

    /** Method to initialize hike values during construction. */
    private void initValidHikes() {
        for (Hike hike : config.getHikes()) {
            try {
                String name = hike.getName();
                Rates.HIKE hikeEnum = Rates.HIKE.valueOf(hike.getKey());
                validHikes.add(name);
                hikeEnumMap.put(name, hikeEnum);
                hikeMap.put(name, hike);
            } catch (Exception e) {
                logger.error("Invalid hike data detected. Skipping.");
                logger.debug("", e);
                continue;
            }
        }

        if (validHikes.size() > 0) {
            // Set index 0 as default value.
            resetHike();
        }
    }

    /** A clean reset of the hike index without throwing exceptions. */
    public void resetHike() {
        hikeIdx = 0;
        hike = hikeMap.get(getValidHikes().get(hikeIdx));
        if (validDurations != null) {
            updateValidDurations();
        }
    }

    /**
     * Sets the hike index value.
     *
     * @param index The index value to set hike to.
     * @throws IllegalArgumentException When the year hike is invalid.
     */
    public void setHikeIdx(int index) throws IllegalArgumentException {
        if (index < 0 || index >= getValidHikes().size()) {
            resetHike();
            throw new IllegalArgumentException("bad hike");
        }

        hike = hikeMap.get(getValidHikes().get(index));
        hikeIdx = index;

        // Since hike changed, duration options may have changed.
        updateValidDurations();
    }

    /**
     * Accessor for valid hikes.
     *
     * @return Valid hikes as a Vector of Strings.
     */
    public Vector<String> getValidHikes() {
        return validHikes;
    }
}
