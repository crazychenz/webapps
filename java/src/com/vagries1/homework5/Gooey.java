/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

import com.rbevans.bookingrate.BookingDay;
import com.rbevans.bookingrate.Rates;
import com.vagries1.homework5.bindings.BhcConfig;
import com.vagries1.homework5.bindings.Hike;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;
import javax.swing.ComboBoxModel;
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

    GooeyElementMap elements;
    GooeyProperties properties;
    BhcConfig config;

    @SuppressWarnings("unchecked")
    final Class<JComboBox<String>> JComboBoxStringType =
            (Class<JComboBox<String>>) (Object) JComboBox.class;

    Vector<String> validDurations;
    Vector<String> validDates;

    public Gooey(BhcConfig config) {
        this.config = config;
        properties = new GooeyProperties();
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
        // System.out.println("validDates.size() " + validDates.size());
        // System.out.println("date " + date);
        if (validDates.size() <= date) {
            // System.out.println("Unselecting date.");
            dateField.setSelectedItem(null);
        }
    }

    int monthFromName(String name) {
        Calendar cal = Calendar.getInstance();
        for (int i = Calendar.JANUARY; i <= Calendar.DECEMBER; ++i) {
            String month;
            cal.set(1, i, 1);
            month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
            if (month.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public void updateEstimate() {
        JTextField estimateField;
        JComboBox<String> hikeField;
        JComboBox<String> durationField;
        JComboBox<String> dateField;
        JComboBox<String> monthField;
        JComboBox<String> yearField;

        hikeField = elements.getAs("HIKE_FIELD", JComboBoxStringType);
        dateField = elements.getAs("DATE_FIELD", JComboBoxStringType);
        monthField = elements.getAs("MONTH_FIELD", JComboBoxStringType);
        yearField = elements.getAs("YEAR_FIELD", JComboBoxStringType);
        durationField = elements.getAs("DURATION_FIELD", JComboBoxStringType);
        estimateField = elements.getAs("ESTIMATE_FIELD", JTextField.class);

        String durationStr = (String) durationField.getSelectedItem();
        int duration = Integer.parseInt(durationStr);
        int year = Integer.parseInt((String) yearField.getSelectedItem());
        int month = monthFromName((String) monthField.getSelectedItem()) + 1;
        int date = dateField.getSelectedIndex() + 1;

        int hikeIdx = hikeField.getSelectedIndex();
        Hike hike = config.getHikes().get(hikeIdx);

        BookingDay startDay = new BookingDay(year, month, date);
        Rates rates = new Rates(Rates.HIKE.valueOf(hike.getKey()));
        rates.setBeginDate(startDay);
        boolean success = rates.setDuration(duration);
        estimateField.setText(Double.toString(rates.getCost()));
        System.out.println(rates.getDetails());
    }

    public void actionPerformed(ActionEvent evt) {
        Object obj = evt.getSource();

        JTextField estimateField;
        JButton calculateButton;

        estimateField = elements.getAs("ESTIMATE_FIELD", JTextField.class);
        calculateButton = elements.getAs("CALCULATE_BUTTON", JButton.class);

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

    public JComboBox<String> loadComboBox(
            String key, GooeyElementMap elements, Vector<String> items) {

        JComboBox<String> comboBox;
        ComboBoxModel<String> model;

        model = new DefaultComboBoxModel<String>(items);
        comboBox = new JComboBox<String>(model);
        elements.putAs(key, JComboBoxStringType, comboBox);
        comboBox.addActionListener(this);

        return comboBox;
    }

    public JPanel createFormPanel(boolean highRes) {
        // Creating some aliases for easier reading.
        GooeyElementMap e = elements;
        GooeyProperties p = properties;

        JPanel panel = new JPanel(new GridBagLayout());

        // Create valid hikes from bhcConfig.xml
        Vector<String> validHikes = new Vector<String>();
        for (Hike hike : config.getHikes()) {
            validHikes.add(hike.getName());
        }

        // Generate month vector from Calendar object
        Vector<String> validMonths = new Vector<String>();
        Calendar cal = Calendar.getInstance();
        for (int i = Calendar.JUNE; i < Calendar.OCTOBER; ++i) {
            String month;
            cal.set(1, i, 1);
            month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
            validMonths.add(month);
        }

        // Create valid years from bhcConfig.xml
        Vector<String> validYears = new Vector<String>();
        int minYear = config.getAppointmentRange().getMinYear();
        int maxYear = config.getAppointmentRange().getMaxYear();
        for (int i = minYear; i < maxYear + 1; ++i) {
            validYears.add(Integer.toString(i));
        }

        e.putAs("HIKE_LABEL", JLabel.class, p.asJLabel("HIKE_LABEL"));
        loadComboBox("HIKE_FIELD", elements, validHikes);

        e.putAs("DATE_LABEL", JLabel.class, p.asJLabel("DATE_LABEL"));
        loadComboBox("MONTH_FIELD", elements, validMonths);
        loadComboBox("YEAR_FIELD", elements, validYears);

        JComboBox<String> comboBox;
        validDates = new Vector<String>();
        comboBox = loadComboBox("DATE_FIELD", elements, validDates);
        updateDates();
        comboBox.setSelectedIndex(0);

        e.putAs("DURATION_LABEL", JLabel.class, p.asJLabel("DURATION_LABEL"));
        validDurations = new Vector<String>();
        loadComboBox("DURATION_FIELD", elements, validDurations);
        updateDurations();

        JButton calculateButton;
        calculateButton = p.asJButton("CALCULATE_BUTTON");
        e.putAs("CALCULATE_BUTTON", JButton.class, calculateButton);
        calculateButton.addActionListener(this);

        e.putAs("ESTIMATE_LABEL", JLabel.class, p.asJLabel("ESTIMATE_LABEL"));

        JTextField estimateField;
        estimateField = p.asJTextField("ESTIMATE_FIELD");
        estimateField.setEditable(false);
        e.putAs("ESTIMATE_FIELD", JTextField.class, estimateField);

        if (highRes) {
            int FONT_SIZE = p.asInt("HIGH_RES_FONT_SIZE");
            for (String key : e.keySet()) {
                Container container = e.getAs(key, JComponent.class);
                container.setFont(new Font(container.getName(), Font.PLAIN, FONT_SIZE));
            }
        }

        // Add all the GUI elements to panel.
        for (String key : e.keySet()) {
            GridBagConstraints constraints;
            constraints = p.asGridBagConstraints(key);
            panel.add(e.getAs(key, JComponent.class), constraints);
        }

        return panel;
    }

    public JFrame createFrame() {
        JFrame frame = new JFrame("BHC Hike Calculator");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        boolean isHighRes = false;
        if (screenSize.getHeight() > 2000 && screenSize.getWidth() > 2000) {
            isHighRes = true;
        }

        frame.add(createFormPanel(isHighRes));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);

        return frame;
    }

    public static void deferredGui(BhcConfig config) {
        JFrame frame = new Gooey(config).createFrame();
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
