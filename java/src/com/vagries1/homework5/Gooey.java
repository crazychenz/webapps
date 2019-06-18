/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Gooey implements ActionListener {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(Main.class);

    @SuppressWarnings("unchecked")
    final Class<JComboBox<String>> JComboBoxStringType =
            (Class<JComboBox<String>>) (Object) JComboBox.class;

    // LinkedHashMap<String, GooeyElement> elements;
    GooeyElementMap elements;

    /*JLabel hikeLabel;
    JComboBox<String> hikeField;
    JLabel dateLabel;
    JComboBox<String> monthField;
    JComboBox<String> dateField;
    JComboBox<String> yearField;
    JLabel durationLabel;
    JComboBox<String> durationField;
    */
    JButton calculateButton;
    // JLabel estimateLabel;
    JTextField estimateField;
    BhcConfig config;
    Vector<String> validDurations;
    Vector<String> validDates;

    public Gooey() {
        elements = new GooeyElementMap();
    }

    public void updateDurations() {
        JComboBox<String> hikeField = elements.getAs("HIKE_FIELD", JComboBoxStringType);

        validDurations.removeAllElements();
        Hike hike = config.getHikes().get(hikeField.getSelectedIndex());
        for (int duration : hike.getDurationList()) {
            validDurations.add(Integer.toString(duration));
        }
    }

    public int getMaxDate(int month, int year) {
        if (month == 2) {
            if (year % 4 == 0) {
                if (year % 100 == 0) {
                    if (year % 400 == 0) {
                        return 29;
                    }
                    return 28;
                }
                return 29;
            }
            return 28;
        } else if (month == 1
                || month == 3
                || month == 5
                || month == 7
                || month == 8
                || month == 10
                || month == 12) {
            return 31;
        }
        return 30;
    }

    public void updateDates() {

        JComboBox<String> dateField = elements.getAs("DATE_FIELD", JComboBoxStringType);
        JComboBox<String> monthField = elements.getAs("MONTH_FIELD", JComboBoxStringType);
        JComboBox<String> yearField = elements.getAs("YEAR_FIELD", JComboBoxStringType);

        int date = dateField.getSelectedIndex();
        int month = monthField.getSelectedIndex() + 1;
        int year = Integer.parseInt((String) yearField.getSelectedItem());
        validDates.removeAllElements();
        for (int i = 1; i < getMaxDate(month, year) + 1; ++i) {
            validDates.add(Integer.toString(i));
        }
        System.out.println("validDates.size() " + validDates.size());
        System.out.println("date " + date);
        if (validDates.size() <= date) {
            System.out.println("Unselecting date.");
            dateField.setSelectedItem(null);
        }
    }

    public void updateEstimate() {
        JComboBox<String> hikeField = elements.getAs("HIKE_FIELD", JComboBoxStringType);

        int hikeIdx = hikeField.getSelectedIndex();
        Hike hike = config.hikes.get(hikeIdx);
        float cost = hike.getBaseRate() * hike.getPremiumMultiplier();
        estimateField.setText(Float.toString(cost));
    }

    public void actionPerformed(ActionEvent evt) {
        Object obj = evt.getSource();

        if (estimateField != null) {
            // Clear estimate on any change.
            estimateField.setText("");
            estimateField.updateUI();
        }

        if (obj == elements.get("HIKE_FIELD")) {
            updateDurations();
            elements.getAs("DURATION_FIELD", JComboBoxStringType).updateUI();
        } else if (obj == elements.get("MONTH_FIELD") || obj == elements.get("YEAR_FIELD")) {
            updateDates();
            elements.getAs("DATE_FIELD", JComboBoxStringType).updateUI();
        } else if (obj == calculateButton) {
            updateEstimate();
            estimateField.updateUI();
        }
    }

    public static void bumpFont(Container container, int size) {
        container.setFont(new Font(container.getName(), Font.PLAIN, size));
    }

    public JPanel createFormPanel(BhcConfig config, boolean highRes) {

        GooeyProperties p = new GooeyProperties();

        // Creating an alias for easier reading.
        GooeyElementMap e = elements;

        // TODO: Do this somewhere else.
        this.config = config;
        JPanel panel = new JPanel(new GridBagLayout());

        // Generic reference holders
        GridBagConstraints constraints;
        JComboBox<String> comboBox;

        // Create valid hikes from bhcConfig.xml
        Vector<String> validHikes = new Vector<String>();
        for (Hike hike : config.getHikes()) {
            validHikes.add(hike.getName());
        }

        String validMonths[] = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };

        // Create valid years from bhcConfig.xml
        Vector<String> validYears = new Vector<String>();
        int minYear = config.getAppointmentRange().getMinYear();
        int maxYear = config.getAppointmentRange().getMaxYear();
        for (int i = minYear; i < maxYear + 1; ++i) {
            validYears.add(Integer.toString(i));
        }

        e.putAs("HIKE_LABEL", JLabel.class, p.asJLabel("HIKE_LABEL"));

        comboBox = new JComboBox<String>(validHikes);
        e.putAs("HIKE_FIELD", JComboBoxStringType, comboBox);
        comboBox.addActionListener(this);

        e.putAs("DATE_LABEL", JLabel.class, p.asJLabel("DATE_LABEL"));

        comboBox = new JComboBox<String>(validMonths);
        e.putAs("MONTH_FIELD", JComboBoxStringType, comboBox);
        comboBox.addActionListener(this);

        comboBox = new JComboBox<String>(validYears);
        e.putAs("YEAR_FIELD", JComboBoxStringType, comboBox);
        comboBox.addActionListener(this);

        validDates = new Vector<String>();
        comboBox = new JComboBox<String>(validDates);
        e.putAs("DATE_FIELD", JComboBoxStringType, comboBox);
        comboBox.addActionListener(this);
        comboBox.setModel(new DefaultComboBoxModel<String>(validDates));
        updateDates();
        comboBox.setSelectedIndex(0);

        e.putAs("DURATION_LABEL", JLabel.class, p.asJLabel("DURATION_LABEL"));

        validDurations = new Vector<String>();
        comboBox = new JComboBox<String>(validDurations);
        comboBox.addActionListener(this);
        DefaultComboBoxModel<String> model;
        model = new DefaultComboBoxModel<String>(validDurations);
        comboBox.setModel(model);
        updateDurations();
        e.putAs("DURATION_FIELD", JComboBoxStringType, comboBox);

        calculateButton = new JButton(p.asStr("CALCULATE_BUTTON_LABEL"));
        calculateButton.addActionListener(this);

        e.putAs("ESTIMATE_LABEL", JLabel.class, p.asJLabel("ESTIMATE_LABEL"));

        // estimateLabel = new JLabel(p.asStr("ESTIMATE_LABEL"), JLabel.RIGHT);
        estimateField = new JTextField(p.asInt("ESTIMATE_FIELD_SIZE"));
        estimateField.setEditable(false);

        if (highRes) {
            final int HIGH_RES_FONT_SIZE = 42;
            Gooey.bumpFont(e.getAs("HIKE_LABEL", JComponent.class), HIGH_RES_FONT_SIZE);
            Gooey.bumpFont(e.getAs("HIKE_FIELD", JComponent.class), HIGH_RES_FONT_SIZE);
            Gooey.bumpFont(e.getAs("MONTH_FIELD", JComponent.class), HIGH_RES_FONT_SIZE);
            Gooey.bumpFont(e.getAs("DATE_LABEL", JComponent.class), HIGH_RES_FONT_SIZE);
            Gooey.bumpFont(e.getAs("DATE_FIELD", JComponent.class), HIGH_RES_FONT_SIZE);
            Gooey.bumpFont(e.getAs("YEAR_FIELD", JComponent.class), HIGH_RES_FONT_SIZE);
            Gooey.bumpFont(e.getAs("DURATION_LABEL", JComponent.class), HIGH_RES_FONT_SIZE);
            Gooey.bumpFont(e.getAs("DURATION_FIELD", JComponent.class), HIGH_RES_FONT_SIZE);
            calculateButton.setFont(
                    new Font(calculateButton.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
            Gooey.bumpFont(e.getAs("ESTIMATE_LABEL", JComponent.class), HIGH_RES_FONT_SIZE);
            estimateField.setFont(
                    new Font(estimateField.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
        }

        // Labels (and Button)
        constraints = p.asGridBagConstraints("HIKE_LABEL");
        panel.add(e.getAs("HIKE_LABEL", JComponent.class), constraints);
        constraints = p.asGridBagConstraints("DATE_LABEL");
        panel.add(e.getAs("DATE_LABEL", JComponent.class), constraints);
        constraints = p.asGridBagConstraints("DURATION_LABEL");
        panel.add(e.getAs("DURATION_LABEL", JComponent.class), constraints);
        constraints = p.asGridBagConstraints("ESTIMATE_LABEL");
        panel.add(e.getAs("ESTIMATE_LABEL", JComponent.class), constraints);
        constraints = p.asGridBagConstraints("CALCULATE_BUTTON");
        panel.add(calculateButton, constraints);

        // Fields
        constraints = p.asGridBagConstraints("HIKE_FIELD");
        panel.add(e.getAs("HIKE_FIELD", JComponent.class), constraints);
        constraints = p.asGridBagConstraints("MONTH_FIELD");
        panel.add(e.getAs("MONTH_FIELD", JComponent.class), constraints);
        constraints = p.asGridBagConstraints("DATE_FIELD");
        panel.add(e.getAs("DATE_FIELD", JComponent.class), constraints);
        constraints = p.asGridBagConstraints("YEAR_FIELD");
        panel.add(e.getAs("YEAR_FIELD", JComponent.class), constraints);
        constraints = p.asGridBagConstraints("DURATION_FIELD");
        panel.add(e.getAs("DURATION_FIELD", JComponent.class), constraints);
        constraints = p.asGridBagConstraints("ESTIMATE_FIELD");
        panel.add(estimateField, constraints);

        return panel;
    }

    public JFrame createFrame(BhcConfig config) {
        JFrame frame = new JFrame("BHC Hike Calculator");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        boolean isHighRes = false;
        if (screenSize.getHeight() > 2000 && screenSize.getWidth() > 2000) {
            isHighRes = true;
        }

        frame.add(createFormPanel(config, isHighRes));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);

        // Display the window.
        frame.setSize(3000, 3000);

        return frame;
    }

    public static void deferredGui(BhcConfig config) {
        JFrame frame = new Gooey().createFrame(config);
        Runnable runFrame =
                new Runnable() {
                    public void run() {
                        frame.pack();
                        frame.setVisible(true);
                    }
                };

        SwingUtilities.invokeLater(runFrame);
    }
}
