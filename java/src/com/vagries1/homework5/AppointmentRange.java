/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement(name = "appointmentRange")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentRange implements Serializable {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(BhcRates.class);

    private static final long serialVersionUID = 1L;

    int minYear;
    int maxYear;

    public int getMinYear() {
        return minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMinYear(int year) {
        minYear = year;
    }

    public void setMaxYear(int year) {
        maxYear = year;
    }

    public AppointmentRange() {
        super();
    }

    public AppointmentRange(int minYear, int maxYear) {
        super();
        this.minYear = minYear;
        this.maxYear = maxYear;
    }
}
