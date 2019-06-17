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
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement(name = "hikes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Hikes implements Serializable {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(BhcRates.class);

    private static final long serialVersionUID = 1L;

    ArrayList<Hike> hikes;

    public ArrayList<Hike> getHikes() {
        return hikes;
    }

    public Hikes() {
        super();
    }

    public Hikes(ArrayList<Hike> hikes) {
        super();

        this.hikes = hikes;
    }
}
