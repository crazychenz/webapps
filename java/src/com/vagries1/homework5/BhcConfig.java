/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement(name = "bhcConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class BhcConfig implements Serializable {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(BhcRates.class);

    private static final long serialVersionUID = 1L;

    String name;

    @XmlElement(name = "appointmentRange")
    AppointmentRange appointmentRange;

    @XmlElementWrapper(name = "hikes")
    @XmlElement(name = "hike")
    ArrayList<Hike> hikes;

    public String getName() {
        return this.name;
    }

    public ArrayList<Hike> getHikes() {
        return hikes;
    }

    public AppointmentRange getAppointmentRange() {
        return appointmentRange;
    }

    public BhcConfig() {
        super();
    }

    public BhcConfig(AppointmentRange range, ArrayList<Hike> hikes) {
        super();
        this.appointmentRange = range;
        this.hikes = hikes;
    }
}
