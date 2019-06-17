/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement(name = "hike")
@XmlAccessorType(XmlAccessType.FIELD)
public class Hike implements Serializable {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(BhcRates.class);

    private static final long serialVersionUID = 1L;

    String name;
    int baseRate;
    float premiumMultiplier;
    List<Integer> duration;

    public String getName() {
        return name;
    }

    public int getBaseRate() {
        return baseRate;
    }

    public float getPremiumMultiplier() {
        return premiumMultiplier;
    }

    public List<Integer> getDurationList() {
        return duration;
    }

    // For JComboBox.
    public String toString() {
        return name;
    }

    public Hike() {
        super();
    }

    public Hike(String name, int baseRate, float premiumMultiplier, List<Integer> duration) {
        super();

        this.name = name;
        this.baseRate = baseRate;
        this.premiumMultiplier = premiumMultiplier;
        this.duration = duration;
    }
}
