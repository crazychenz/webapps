/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5.bindings;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
    private static final Logger logger = LogManager.getLogger(BhcConfig.class);

    private static final long serialVersionUID = 1L;

    String name;

    @XmlElement(name = "appointmentRange")
    AppointmentRange appointmentRange;

    @XmlElementWrapper(name = "hikes")
    @XmlElement(name = "hike")
    ArrayList<Hike> hikes;

    /**
     * This method binds the xmlFile to the objects in the com.vagries1.homework5.binding package.
     * This is the loader for the BhcConfig that supplies the possible user options in the
     * BhcEstimator object.
     *
     * @param xmlFile The XML file that contains to bhcConfig data.
     * @return The BhcConfig object tree that represents bhcConfig data.
     */
    public static Unmarshaller createUnmarshaller() {
        JAXBContext jaxbContext;
        Unmarshaller jaxbUnmarshaller = null;

        try {
            jaxbContext = JAXBContext.newInstance(BhcConfig.class);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return jaxbUnmarshaller;
    }

    /**
     * This method binds the xmlFile to the objects in the com.vagries1.homework5.binding package.
     * This is the loader for the BhcConfig that supplies the possible user options in the
     * BhcEstimator object.
     *
     * @param xmlFile The XML file that contains to bhcConfig data.
     * @return The BhcConfig object tree that represents bhcConfig data.
     */
    public static BhcConfig unmarshallFile(File xmlFile) {
        BhcConfig config = null;

        try {
            config = (BhcConfig) createUnmarshaller().unmarshal(xmlFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return config;
    }

    /**
     * This method binds the xmlStream to the objects in the com.vagries1.homework5.binding package.
     * This is the loader for the BhcConfig that supplies the possible user options in the
     * BhcEstimator object.
     *
     * @param xmlFile The XML file that contains to bhcConfig data.
     * @return The BhcConfig object tree that represents bhcConfig data.
     */
    public static BhcConfig unmarshallStream(InputStream xmlStream) {
        BhcConfig config = null;

        try {
            config = (BhcConfig) createUnmarshaller().unmarshal(xmlStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return config;
    }

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
