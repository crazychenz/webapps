/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

import com.rbevans.bookingrate.Rates;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** Main entry point for homework4. */
public class BhcRates extends Rates {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(BhcRates.class);

    public BhcRates(Rates.HIKE hike) {
        super(hike);
        readConfig();
    }

    protected void readConfig() {
        DocumentBuilder builder;
        Document doc = null;
        NodeList nodes;

        // Parse the XML
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = builder.parse(new File("bhcConfig.xml"));
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Grab relevant info via XPath
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            nodes = (NodeList) xpath.evaluate("", doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getHikeName(Rates.HIKE hike) {
        return null;
    }
}
