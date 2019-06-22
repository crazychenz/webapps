/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.vagries1.homework5.BhcEstimator;
import com.vagries1.homework5.bindings.AppointmentRange;
import com.vagries1.homework5.bindings.BhcConfig;
import com.vagries1.homework5.bindings.Hike;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class BhcEstimatorTest {

    static final String TEST_CONFIG = "/com/vagries1/homework5/tests/bhcConfigTest.xml";

    static BhcConfig buildBhcConfig() {
        AppointmentRange apptRange;
        List<Integer> durations;
        ArrayList<Hike> hikes;
        BhcConfig cfg;

        apptRange = new AppointmentRange(2018, 2025, "JUNE", "OCTOBER");
        durations = new ArrayList<Integer>();
        durations.add(new Integer(3));
        hikes = new ArrayList<Hike>();
        hikes.add(new Hike("Gardiner Lake", "GARDINER", 40.00, 1.5, durations));
        cfg = new BhcConfig(apptRange, hikes);
        return cfg;
    }

    @Test
    public void estimateTests() {
        BhcEstimator obj;
        obj = new BhcEstimator(BhcEstimatorTest.buildBhcConfig());

        // Check initial settings.
        try {
            obj.setDateIdx(0);
            obj.setDurationIdx(0);
            obj.setHikeIdx(0);
            obj.setMonthIdx(0);
            obj.setYearIdx(0);
        } catch (Exception e) {
            fail();
        }
        obj.estimate();
        assertEquals(true, obj.hasEstimate());
        // Note: This isn't intended to test Rates, but instead the
        // successful usage of Rates and BookingDay.
        assertEquals("valid dates", obj.getEstimateInfo());
        assertEquals(160.00, obj.getEstimate(), 0);
    }

    @Test
    public void saneAccessorTests() {

        BhcEstimator obj;
        BhcConfig config;
        InputStream in;

        in = this.getClass().getResourceAsStream(BhcEstimatorTest.TEST_CONFIG);
        config = BhcConfig.unmarshallStream(in);
        obj = new BhcEstimator(config);

        try {
            obj.setDateIdx(1);
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, obj.getDateIdx());

        try {
            obj.setDurationIdx(1);
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, obj.getDurationIdx());

        try {
            obj.setMonthIdx(1);
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, obj.getMonthIdx());

        try {
            obj.setYearIdx(1);
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, obj.getYearIdx());
    }

    @Test
    public void testConfigAccessorTests() {

        BhcEstimator obj;
        BhcConfig config;
        InputStream in;

        in = this.getClass().getResourceAsStream(BhcEstimatorTest.TEST_CONFIG);
        config = BhcConfig.unmarshallStream(in);
        obj = new BhcEstimator(config);

        // Check that dates are updated when month changes.
        assertEquals(31, obj.getValidDates().size());
        try {
            obj.setMonthIdx(1); // FEBURARY
        } catch (Exception e) {
            fail();
        }
        assertEquals(28, obj.getValidDates().size());

        // Check that dates are updated when year changes.
        assertEquals(28, obj.getValidDates().size());
        try {
            obj.setYearIdx(6); // 2024
        } catch (Exception e) {
            fail();
        }
        assertEquals(29, obj.getValidDates().size());

        // Check that durations are updated when hike changes
        assertEquals(2, obj.getValidDurations().size());
        try {
            obj.setHikeIdx(1); // HELLROARING
        } catch (Exception e) {
            fail();
        }
        assertEquals(3, obj.getValidDurations().size());
    }

    @Test
    public void rangeExceptionTests() {
        BhcEstimator obj;
        BhcConfig config;
        InputStream in;

        // Load from test config.
        in = this.getClass().getResourceAsStream(BhcEstimatorTest.TEST_CONFIG);
        config = BhcConfig.unmarshallStream(in);
        obj = new BhcEstimator(config);

        // Range check dates
        try {
            obj.setDateIdx(-1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("bad date", e.getMessage());
        }
        try {
            obj.setDateIdx(obj.getValidDates().size());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("bad date", e.getMessage());
        }

        // Range check years
        try {
            obj.setYearIdx(-1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("bad year", e.getMessage());
        }
        try {
            obj.setYearIdx(obj.getValidYears().size());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("bad year", e.getMessage());
        }

        // Range check months
        try {
            obj.setMonthIdx(-1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("bad month", e.getMessage());
        }
        try {
            obj.setMonthIdx(obj.getValidMonths().size());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("bad month", e.getMessage());
        }

        // Range check hikes
        try {
            obj.setHikeIdx(-1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("bad hike", e.getMessage());
        }
        try {
            obj.setHikeIdx(obj.getValidHikes().size());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("bad hike", e.getMessage());
        }

        // Range check duration
        try {
            obj.setDurationIdx(-1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("bad duration", e.getMessage());
        }
        try {
            obj.setDurationIdx(obj.getValidDurations().size());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("bad duration", e.getMessage());
        }
    }
}
