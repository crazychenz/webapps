/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

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

    JComboBox<String> hike_field;
    JComboBox<String> month_field;
    JComboBox<String> date_field;
    JComboBox<String> year_field;
    JComboBox<String> duration_field;
    JButton calculate_button;
    JTextField estimate_field;
    BhcConfig config;
    Vector<String> validDurations;
    Vector<String> validDates;

    public void updateDurations() {
        validDurations.removeAllElements();
        Hike hike = config.getHikes().get(hike_field.getSelectedIndex());
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
        int date = date_field.getSelectedIndex();
        int month = month_field.getSelectedIndex() + 1;
        int year = Integer.parseInt((String) year_field.getSelectedItem());
        validDates.removeAllElements();
        for (int i = 1; i < getMaxDate(month, year) + 1; ++i) {
            validDates.add(Integer.toString(i));
        }
        System.out.println("validDates.size() " + validDates.size());
        System.out.println("date " + date);
        if (validDates.size() <= date) {
            System.out.println("Unselecting date.");
            date_field.setSelectedItem(null);
        }
    }

    public void updateEstimate() {
        int hikeIdx = hike_field.getSelectedIndex();
        Hike hike = config.hikes.get(hikeIdx);
        float cost = hike.getBaseRate() * hike.getPremiumMultiplier();
        estimate_field.setText(Float.toString(cost));
    }

    public void actionPerformed(ActionEvent evt) {
        Object obj = evt.getSource();

        if (estimate_field != null) {
            // Clear estimate on any change.
            estimate_field.setText("");
            estimate_field.updateUI();
        }

        if (obj == hike_field) {
            updateDurations();
            duration_field.updateUI();
        } else if (obj == month_field || obj == year_field) {
            updateDates();
            date_field.updateUI();
        } else if (obj == calculate_button) {
            updateEstimate();
            estimate_field.updateUI();
        }
    }

    public JPanel createFormPanel(BhcConfig config, boolean highRes) {
        // TODO: Do this somewhere else.
        this.config = config;
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

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

        JLabel hike_label = new JLabel("Hike: ", JLabel.RIGHT);
        hike_field = new JComboBox<String>(validHikes);
        hike_field.addActionListener(this);

        JLabel date_label = new JLabel("Date: ", JLabel.RIGHT);
        month_field = new JComboBox<String>(validMonths);
        month_field.addActionListener(this);
        year_field = new JComboBox<String>(validYears);
        year_field.addActionListener(this);
        validDates = new Vector<String>();
        date_field = new JComboBox<String>(validDates);
        date_field.addActionListener(this);
        date_field.setModel(new DefaultComboBoxModel<String>(validDates));
        updateDates();
        date_field.setSelectedIndex(0);

        JLabel duration_label = new JLabel("Duration: ", JLabel.RIGHT);
        validDurations = new Vector<String>();
        duration_field = new JComboBox<String>(validDurations);
        duration_field.addActionListener(this);
        duration_field.setModel(new DefaultComboBoxModel<String>(validDurations));
        updateDurations();

        calculate_button = new JButton("Estimate Cost");
        calculate_button.addActionListener(this);
        JLabel estimate_label = new JLabel("Estimate: ", JLabel.RIGHT);
        estimate_field = new JTextField(10);
        estimate_field.setEditable(false);

        if (highRes) {
            final int HIGH_RES_FONT_SIZE = 42;

            hike_label.setFont(new Font(hike_label.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
            hike_field.setFont(new Font(hike_field.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
            month_field.setFont(new Font(hike_field.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
            date_label.setFont(new Font(hike_label.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
            date_field.setFont(new Font(hike_field.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
            year_field.setFont(new Font(hike_field.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
            duration_label.setFont(new Font(hike_label.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
            duration_field.setFont(new Font(hike_field.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
            calculate_button.setFont(
                    new Font(calculate_button.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
            estimate_label.setFont(
                    new Font(estimate_label.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
            estimate_field.setFont(
                    new Font(estimate_field.getName(), Font.PLAIN, HIGH_RES_FONT_SIZE));
        }

        // Labels (and Button)
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        panel.add(hike_label, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(date_label, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(duration_label, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        panel.add(estimate_label, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        panel.add(calculate_button, constraints);

        // Fields
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        panel.add(hike_field, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(month_field, constraints);
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(date_field, constraints);
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(year_field, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(duration_field, constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        panel.add(estimate_field, constraints);

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
