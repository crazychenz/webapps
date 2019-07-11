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

/** @author Vincent Agriesti */
@XmlRootElement(name = "appointmentRange")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentRange implements Serializable {

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
        Month month;
        month = Month.valueOf(minMonth);
        return month;
    }

    public Month getMaxMonthEnum() {
        Month month;
        month = Month.valueOf(maxMonth);
        return month;
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
