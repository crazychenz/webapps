/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5.bindings;

import java.io.Serializable;
import java.time.Month;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement(name = "appointmentRange")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentRange implements Serializable {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(AppointmentRange.class);

    private static final long serialVersionUID = 1L;

    int minYear;
    int maxYear;
    String minMonth;
    String maxMonth;

    public int getMinYear() {
        return minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public String getMinMonth() {
        return minMonth;
    }

    public String getMaxMonth() {
        return maxMonth;
    }

    public Month getMinMonthEnum() {
        return Month.valueOf(minMonth);
    }

    public Month getMaxMonthEnum() {
        return Month.valueOf(maxMonth);
    }

    public void setMinYear(int year) {
        minYear = year;
    }

    public void setMaxYear(int year) {
        maxYear = year;
    }

    public void setMinMonth(String month) {
        minMonth = month;
    }

    public void setMaxMonth(String month) {
        maxMonth = month;
    }

    public AppointmentRange() {
        super();
    }

    public AppointmentRange(int minYear, int maxYear, String minMonth, String maxMonth) {
        super();
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.minMonth = minMonth;
        this.maxMonth = maxMonth;
    }
}
