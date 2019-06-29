/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5.bindings;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** @author Vincent Agriesti */
@XmlRootElement(name = "hike")
@XmlAccessorType(XmlAccessType.FIELD)
public class Hike implements Serializable {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(Hike.class);

    private static final long serialVersionUID = 2L;

    private static final int MAX_CHARACTER_WIDTH = 48;

    String name;
    String key;
    int id;
    double baseRate;
    double premiumMultiplier;
    List<Integer> duration;

    public String getName() {
        String shortName;
        shortName = name.substring(0, Math.min(name.length(), MAX_CHARACTER_WIDTH));
        return shortName;
    }

    public String getKey() {
        return key;
    }

    public int getId() {
        return id;
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

    public Hike(
            String name,
            String key,
            int id,
            double baseRate,
            double premiumMultiplier,
            List<Integer> duration) {
        super();

        this.name = name;
        this.key = key;
        this.id = id;
        this.baseRate = baseRate;
        this.premiumMultiplier = premiumMultiplier;
        this.duration = duration;
    }
}
