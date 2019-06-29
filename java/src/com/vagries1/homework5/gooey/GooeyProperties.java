/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5.gooey;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is an accessor and helper class for loading values from the gooey.properties file.
 * With an undocumented convention, this class allows a single prefix string to be provided and all
 * required attributes of a JComponent can be loaded and applied in a single call.
 *
 * <p>This design provides some advantages: - The property file allows for easy regional settings if
 * desired. - The property file keeps literals and constants from cluttering code. - The property
 * file provides an easy update mechanism without messing around with code.
 *
 * @author Vincent Agriesti
 */
public class GooeyProperties extends Properties {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(GooeyProperties.class);

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_PROP = "/com/vagries1/homework5/gooey/gooey.properties";

    /**
     * Default constructor.
     *
     * <p>This is where the gooey.properties are loaded. If the property file can not be found, the
     * application indicates this status to STDOUT and then terminates.
     *
     * @throws GooeyPropertyException When path DEFAULT_PROP is inaccessible or doesn't exist.
     */
    public GooeyProperties() throws GooeyPropertyException {
        super();
        try {
            InputStream in;
            in = this.getClass().getResourceAsStream(DEFAULT_PROP);
            if (in == null) {
                System.out.println("Couldn't find gooey.properties.");
                System.exit(1);
            }
            super.load(in);
            in.close();
        } catch (IOException e) {
            logger.error("Failed to load gooey properties.");
            logger.debug("", e);
            throw new GooeyPropertyException("no gooey properties");
        }
    }

    /**
     * Load the property with the given name as an int.
     *
     * @param name The name of the property.
     * @return The value of the property converted to an int.
     * @throws GooeyPropertyException When failing to read property as int.
     */
    public int asInt(String name) throws GooeyPropertyException {
        int ret = 0;
        Integer value;

        try {
            value = Integer.parseInt(super.getProperty(name));
            ret = value.intValue();
        } catch (Exception e) {
            String msg = "Failed to fetch property " + name + " as Integer.";
            logger.error(msg);
            logger.debug("", e);
            // Must throw exception since there is no naturally invalid int to pass.
            throw new GooeyPropertyException("invalid int gooey property");
        }

        return ret;
    }

    /**
     * Load the property with the given name as a String.
     *
     * @param name The name of the property.
     * @return The value of the property converted to String.
     * @throws GooeyPropertyException When failing to read property as String
     */
    public String asStr(String name) throws GooeyPropertyException {
        String ret;
        try {
            ret = super.getProperty(name);
        } catch (Exception e) {
            String msg = "Failed to fetch property " + name + " as String.";
            logger.error(msg);
            logger.debug("", e);
            // Must throw exception since there is no naturally invalid int to pass.
            throw new GooeyPropertyException("invalid String gooey property");
        }
        return ret;
    }

    /**
     * Load the property with the given prefix as a JLabel.
     *
     * @param prefix The prefix of the property.
     * @return A JLabel with applicable attributes applied.
     * @throws GooeyPropertyException When failing to read property as JLabel
     */
    public JLabel asJLabel(String prefix) throws GooeyPropertyException {
        JLabel label = null;

        try {
            label = new JLabel(asStr(prefix), JLabel.RIGHT);
        } catch (Exception e) {
            String msg = "Failed to fetch property " + prefix + " as JLabel.";
            logger.error(msg);
            logger.debug("", e);
            // Must throw exception since there is no naturally invalid int to pass.
            throw new GooeyPropertyException("invalid JLabel gooey property");
        }

        return label;
    }

    /**
     * Load the property with the given prefix as a BufferedImage.
     *
     * @param prefix The prefix of the property.
     * @return A BufferedImage with applicable attributes applied.
     * @throws GooeyPropertyException When failing to read property as BufferedImage
     */
    public BufferedImage asBufferedImage(String prefix) throws GooeyPropertyException {
        BufferedImage img = null;

        try {
            InputStream in;
            in = this.getClass().getResourceAsStream(asStr(prefix + "_RES"));
            img = ImageIO.read(in);
        } catch (IOException e) {
            String msg = "Failed to find resource at " + prefix + "_RES as BufferedImage.";
            logger.error(msg);
            logger.debug("", e);
            // Must throw exception since there is no naturally invalid int to pass.
            throw new GooeyPropertyException("resource not found for BufferedImage");
        } catch (Exception e) {
            String msg = "Failed to fetch property " + prefix + "_RES as BufferedImage.";
            logger.error(msg);
            logger.debug("", e);
            // Must throw exception since there is no naturally invalid int to pass.
            throw new GooeyPropertyException("invalid BufferedImage gooey property");
        }

        return img;
    }

    /**
     * Load the property with the given prefix as a JTextField.
     *
     * @param prefix The prefix of the property.
     * @return A JTextField with applicable attributes applied.
     * @throws GooeyPropertyException When failing to read property as JTextField
     */
    public JTextField asJTextField(String prefix) throws GooeyPropertyException {
        JTextField field = null;

        try {
            field = new JTextField(asInt(prefix + "_SIZE"));
        } catch (Exception e) {
            String msg = "Failed to fetch property " + prefix + "_SIZE as JTextField.";
            logger.error(msg);
            logger.debug("", e);
            // Must throw exception since there is no naturally invalid int to pass.
            throw new GooeyPropertyException("invalid JTextField gooey property");
        }
        return field;
    }

    /**
     * Load the property with the given prefix as a JButton.
     *
     * @param prefix The prefix of the property.
     * @return A JButton with applicable attributes applied.
     * @throws GooeyPropertyException When failing to read property as JButton
     */
    public JButton asJButton(String prefix) throws GooeyPropertyException {
        JButton button = null;

        try {
            button = new JButton(asStr(prefix + "_LABEL"));
        } catch (Exception e) {
            String msg = "Failed to fetch property " + prefix + "_LABEL as JButton.";
            logger.error(msg);
            logger.debug("", e);
            // Must throw exception since there is no naturally invalid int to pass.
            throw new GooeyPropertyException("invalid JButton gooey property");
        }

        return button;
    }

    /**
     * Load the property with the given prefix as a GridBagConstraints.
     *
     * @param prefix The prefix of the property.
     * @return A GridBagConstraints with applicable attributes applied.
     * @throws GooeyPropertyException When failing to read property as GridBagConstraints
     */
    public GridBagConstraints asGridBagConstraints(String prefix) throws GooeyPropertyException {
        final int PADDING = 5;
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(PADDING, PADDING, PADDING, PADDING);

        try {
            if (super.containsKey(prefix + "_GRIDX")) {
                constraints.gridx = asInt(prefix + "_GRIDX");
            }

            if (super.containsKey(prefix + "_GRIDY")) {
                constraints.gridy = asInt(prefix + "_GRIDY");
            }

            if (super.containsKey(prefix + "_GRIDWIDTH")) {
                constraints.gridwidth = asInt(prefix + "_GRIDWIDTH");
            }
        } catch (Exception e) {
            String msg = "Failed to fetch prefix " + prefix + " as GridBagConstraints.";
            logger.error(msg);
            logger.debug("", e);
            // Must throw exception since there is no naturally invalid int to pass.
            throw new GooeyPropertyException("invalid GridBagConstraints gooey property");
        }

        return constraints;
    }
}
