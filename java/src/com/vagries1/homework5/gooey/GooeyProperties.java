/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5.gooey;

import java.awt.GridBagConstraints;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

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

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_PROP = "/com/vagries1/homework5/gooey/gooey.properties";

    /**
     * Default constructor.
     *
     * <p>This is where the gooey.properties are loaded. If the property file can not be found, the
     * application indicates this status to STDOUT and then terminates.
     */
    public GooeyProperties() {
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
            System.out.println("Failed to load gooey properties.");
            System.exit(1);
        }
    }

    /**
     * Load the property with the given name as an int.
     *
     * @param name The name of the property.
     * @return The value of the property converted to an int.
     */
    public int asInt(String name) {
        int ret;
        Integer value = Integer.parseInt(super.getProperty(name));
        ret = value.intValue();
        return ret;
    }

    /**
     * Load the property with the given name as a String.
     *
     * @param name The name of the property.
     * @return The value of the property converted to String.
     */
    public String asStr(String name) {
        String ret;
        ret = super.getProperty(name);
        return ret;
    }

    /**
     * Load the property with the given prefix as a JLabel.
     *
     * @param prefix The prefix of the property.
     * @return A JLabel with applicable attributes applied.
     */
    public JLabel asJLabel(String prefix) {
        JLabel label = new JLabel(asStr(prefix), JLabel.RIGHT);
        return label;
    }

    /**
     * Load the property with the given prefix as a JTextField.
     *
     * @param prefix The prefix of the property.
     * @return A JTextField with applicable attributes applied.
     */
    public JTextField asJTextField(String prefix) {
        JTextField field = new JTextField(asInt(prefix + "_SIZE"));
        return field;
    }

    /**
     * Load the property with the given prefix as a JButton.
     *
     * @param prefix The prefix of the property.
     * @return A JButton with applicable attributes applied.
     */
    public JButton asJButton(String prefix) {
        JButton button = new JButton(asStr(prefix + "_LABEL"));
        return button;
    }

    /**
     * Load the property with the given prefix as a GridBagConstraints.
     *
     * @param prefix The prefix of the property.
     * @return A GridBagConstraints with applicable attributes applied.
     */
    public GridBagConstraints asGridBagConstraints(String prefix) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        if (super.containsKey(prefix + "_GRIDX")) {
            constraints.gridx = asInt(prefix + "_GRIDX");
        }

        if (super.containsKey(prefix + "_GRIDY")) {
            constraints.gridy = asInt(prefix + "_GRIDY");
        }

        if (super.containsKey(prefix + "_GRIDWIDTH")) {
            constraints.gridwidth = asInt(prefix + "_GRIDWIDTH");
        }

        return constraints;
    }
}
