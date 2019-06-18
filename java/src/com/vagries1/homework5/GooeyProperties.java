/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

import java.awt.GridBagConstraints;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GooeyProperties extends Properties {

    private static final long serialVersionUID = 1L;

    public GooeyProperties() {
        super();
        try {
            InputStream in;
            in = this.getClass().getResourceAsStream("gooey.properties");
            super.load(in);
            in.close();
        } catch (IOException e) {
            System.out.println("Failed to load gooey properties.");
            System.exit(1);
        }
    }

    public int asInt(String name) {
        int ret;
        Integer value = Integer.parseInt(super.getProperty(name));
        ret = value.intValue();
        return ret;
    }

    public String asStr(String name) {
        String ret;
        ret = super.getProperty(name);
        return ret;
    }

    public JLabel asJLabel(String prefix) {
        JLabel label = new JLabel(asStr(prefix), JLabel.RIGHT);
        return label;
    }

    public JTextField asJTextField(String prefix) {
        JTextField field = new JTextField(asInt(prefix + "_SIZE"));
        return field;
    }

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
