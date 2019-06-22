/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5.gooey;

import com.vagries1.homework5.BhcEstimator;
import com.vagries1.homework5.bindings.BhcConfig;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Frontend and presentation logic of the BHC Hike Calculator.
 *
 * <p>This class uses Swing to create a form that users can fill out to determine the cost of a hike
 * given the hike, start date, and duration of the hike. It uses com.rbevans.bookingrate package to
 * do the actual calculation.
 *
 * <p>The user interface has been designed for minimal error occurance. For example, when changing
 * the month or year, the date field is updated to only contain valid dates for the given month/year
 * combination. The duration fields are also updated based on the hike field values.
 *
 * <p>When any field is changed or the estimate is calculated, Gooey calls into its backend object
 * (BhcEstimator) to handle the business logic.
 *
 * @author Vincent Agriesti
 */
public class Gooey implements ActionListener {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(Gooey.class);

    GooeyElementMap elements;
    GooeyProperties properties;
    BhcConfig config;
    boolean isHighRes = false;

    /**
     * To simplify the management of JComponent elements, we store all of the components in a single
     * map object. One of the element types is a container type JComboBox<String>. Because of Java
     * Type erasure during compilation, there is a need to generate an unsafe cast of the JComboBox
     * class from the map to object instance. Instead of doing this 'unchecked' on every reference
     * to the map object, we do a single deterministic cast and save the type information into a
     * object property and explicity SupressWarnings for the unchecked cast.
     */
    @SuppressWarnings("unchecked")
    final Class<JComboBox<String>> JComboBoxStringType =
            (Class<JComboBox<String>>) (Object) JComboBox.class;

    BhcEstimator estimator;

    /**
     * Constructor for creating frontend Gooey object instance.
     *
     * @param estimator The backend business logic object instance.
     */
    public Gooey(BhcEstimator estimator) {
        this.estimator = estimator;
        properties = new GooeyProperties();
        elements = new GooeyElementMap();
    }

    /** Actions to perform when the calculate button is pressed. */
    public void onCalculateEstimate() {
        JTextField estimateField;
        estimateField = elements.getAs("ESTIMATE_FIELD", JTextField.class);

        estimator.estimate();

        if (estimator.getEstimate() > 0) {
            estimateField.setText(Double.toString(estimator.getEstimate()));
        }
        String msg = estimator.getEstimateInfo();
        if (msg == "begin or end date was out of season") {
            int FONT_SIZE = properties.asInt("HIGH_RES_FONT_SIZE");
            msg = properties.asStr("END_DATE_OUT_OF_SEASON_MSG");
            JLabel label = new JLabel(msg);
            if (isHighRes) {
                label.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
            }
            JOptionPane.showMessageDialog(null, label);
        } else if (msg != "valid dates") {
            // This is a catch all for unlikely values.
            JOptionPane.showMessageDialog(null, msg);
        }
        logger.info(estimator.getEstimateInfo());
    }

    /** Actions to perform when the hike field is changed. */
    public void onHikeFieldUpdate() {
        JComboBox<String> comboBox;
        JComboBox<String> hikeField;
        hikeField = elements.getAs("HIKE_FIELD", JComboBoxStringType);
        try {
            estimator.setHike(hikeField.getSelectedIndex());
        } catch (Exception e) {
            // Note: Unlikely this will ever happen.
            String msg = "Detected invalid hike entry. Resetting field.";
            JOptionPane.showMessageDialog(null, msg);
            estimator.resetHike();
            hikeField.setSelectedIndex(0);
            hikeField.updateUI();
        }
        // Ensure these are up to date since they may have been updated.
        comboBox = elements.getAs("DURATION_FIELD", JComboBoxStringType);
        if (comboBox != null) {
            comboBox.setSelectedIndex(0);
        }
    }

    /** Actions to perform when the month field is changed. */
    public void onMonthFieldUpdate() {
        JComboBox<String> comboBox;
        JComboBox<String> monthField;
        monthField = elements.getAs("MONTH_FIELD", JComboBoxStringType);
        try {
            estimator.setMonth(monthField.getSelectedIndex());
        } catch (Exception e) {
            // Note: Unlikely this will ever happen.
            String msg = "Detected invalid month entry. Resetting field.";
            JOptionPane.showMessageDialog(null, msg);
            estimator.resetMonth();
            monthField.setSelectedIndex(0);
        }
        // Ensure these are up to date since they may have been updated.
        comboBox = elements.getAs("DATE_FIELD", JComboBoxStringType);
        if (comboBox != null) {
            comboBox.setSelectedIndex(0);
        }
    }

    /** Actions to perform when the year field is changed. */
    public void onYearFieldUpdate() {
        JComboBox<String> comboBox;
        JComboBox<String> yearField;
        yearField = elements.getAs("YEAR_FIELD", JComboBoxStringType);
        try {
            estimator.setYear(yearField.getSelectedIndex());
        } catch (Exception e) {
            // Note: Unlikely this will ever happen.
            String msg = "Detected invalid year entry. Resetting field.";
            JOptionPane.showMessageDialog(null, msg);
            estimator.resetYear();
            yearField.setSelectedIndex(0);
        }
        // Ensure these are up to date since they may have been updated.
        comboBox = elements.getAs("DATE_FIELD", JComboBoxStringType);
        if (comboBox != null) {
            comboBox.setSelectedIndex(0);
        }
    }

    /** Actions to perform when the date field is changed. */
    public void onDateFieldUpdate() {
        JComboBox<String> dateField;
        dateField = elements.getAs("DATE_FIELD", JComboBoxStringType);
        try {
            estimator.setDate(dateField.getSelectedIndex());
        } catch (Exception e) {
            // Note: Unlikely this will ever happen.
            String msg = "Detected invalid date entry. Resetting field.";
            JOptionPane.showMessageDialog(null, msg);
            estimator.resetDate();
            dateField.setSelectedIndex(0);
        }
    }

    /** Actions to perform when the duration field is changed. */
    public void onDurationFieldUpdate() {
        JComboBox<String> durationField;
        durationField = elements.getAs("DURATION_FIELD", JComboBoxStringType);
        try {
            estimator.setDuration(durationField.getSelectedIndex());
        } catch (Exception e) {
            // Note: Unlikely this will ever happen.
            String msg = "Detected invalid duration entry. Resetting field.";
            JOptionPane.showMessageDialog(null, msg);
            estimator.resetDate();
            durationField.setSelectedIndex(0);
        }
    }

    /** Clears the estimated cost field. */
    public void clearEstimateField() {
        JTextField estimateField;

        estimateField = elements.getAs("ESTIMATE_FIELD", JTextField.class);
        if (estimateField != null) {
            estimateField.setText("");
        }
    }

    /**
     * Callback function for ActionListener interface.
     *
     * <p>Routes GUI component actions to handler functions.
     */
    public void actionPerformed(ActionEvent evt) {
        Object obj = evt.getSource();

        // Clear estimate on any change.
        clearEstimateField();

        if (obj == elements.get("HIKE_FIELD")) {
            onHikeFieldUpdate();
        } else if (obj == elements.get("MONTH_FIELD")) {
            onMonthFieldUpdate();
        } else if (obj == elements.get("YEAR_FIELD")) {
            onYearFieldUpdate();
        } else if (obj == elements.get("DATE_FIELD")) {
            onDateFieldUpdate();
        } else if (obj == elements.get("DURATION_FIELD")) {
            onDurationFieldUpdate();
        } else if (obj == elements.get("CALCULATE_BUTTON")) {
            onCalculateEstimate();
        }
    }

    /**
     * Creates a JComboBox from data in BhcEstimator and saves the reference in the GooeyElementMap
     * container.
     *
     * @param key The key that the JComboBox gets saved as in GooeyElementMap
     * @param elements The GooeyElementMap to save JComboBox to.
     * @param items The vector data that is loaded into the JComboBox model.
     * @return Returns the JComboBox&lt;String&gt; object.
     */
    public JComboBox<String> loadComboBox(
            String key, GooeyElementMap elements, Vector<String> items) {

        JComboBox<String> comboBox;
        ComboBoxModel<String> model;

        model = new DefaultComboBoxModel<String>(items);
        comboBox = new JComboBox<String>(model);
        elements.putAs(key, JComboBoxStringType, comboBox);
        comboBox.addActionListener(this);
        comboBox.setSelectedIndex(0);

        return comboBox;
    }

    /**
     * Creates the primary container for the startup JFrame. This panel includes all of the form
     * elements for the calculator.
     *
     * @return JPanel object with all form elements added.
     */
    public JPanel createFormPanel() {

        JButton calculateButton;
        JTextField estimateField;

        JPanel panel = new JPanel(new GridBagLayout());

        // Creating some aliases for easier reading.
        GooeyElementMap e = elements;
        GooeyProperties p = properties;

        e.putAs("HIKE_LABEL", JLabel.class, p.asJLabel("HIKE_LABEL"));
        loadComboBox("HIKE_FIELD", elements, estimator.getValidHikes());

        e.putAs("DATE_LABEL", JLabel.class, p.asJLabel("DATE_LABEL"));
        loadComboBox("MONTH_FIELD", elements, estimator.getValidMonths());
        loadComboBox("YEAR_FIELD", elements, estimator.getValidYears());
        loadComboBox("DATE_FIELD", elements, estimator.getValidDates());

        e.putAs("DURATION_LABEL", JLabel.class, p.asJLabel("DURATION_LABEL"));
        loadComboBox("DURATION_FIELD", elements, estimator.getValidDurations());

        calculateButton = p.asJButton("CALCULATE_BUTTON");
        e.putAs("CALCULATE_BUTTON", JButton.class, calculateButton);
        calculateButton.addActionListener(this);

        e.putAs("ESTIMATE_LABEL", JLabel.class, p.asJLabel("ESTIMATE_LABEL"));
        estimateField = p.asJTextField("ESTIMATE_FIELD");
        estimateField.setEditable(false);
        e.putAs("ESTIMATE_FIELD", JTextField.class, estimateField);

        // Default GUI is a bit hard to see in 4K.
        if (isHighRes) {
            int FONT_SIZE = p.asInt("HIGH_RES_FONT_SIZE");
            for (String key : e.keySet()) {
                Container cont = e.getAs(key, JComponent.class);
                cont.setFont(new Font(cont.getName(), Font.PLAIN, FONT_SIZE));
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

    /**
     * Creates the JFrame.
     *
     * <p>When the application is run without a GUI environment, it gracefully informs the user of
     * the headless state and returns null.
     *
     * @return JFrame on success, null on failure.
     */
    public JFrame createFrame() {
        JFrame frame = null;
        try {
            frame = new JFrame(properties.asStr("FRAME_TITLE"));

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (screenSize.getHeight() > 2000 && screenSize.getWidth() > 1500) {
                isHighRes = true;
            }

            frame.add(createFormPanel());

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationByPlatform(true);
        } catch (HeadlessException e) {
            System.out.println("No GUI framework found to load window.");
        }
        return frame;
    }

    /**
     * Asynchronous loading of the GUI as recommended by the Java community.
     *
     * <p>If the JFrame failed to generate due to a null JFrame, the method simply returns without
     * any further action.
     *
     * @param estimator Reference to the estimator backend logic.
     */
    public static void deferredGui(BhcEstimator estimator) {
        JFrame frame = new Gooey(estimator).createFrame();
        if (frame != null) {
            Runnable runFrame =
                    new Runnable() {
                        public void run() {
                            frame.pack();
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                        }
                    };

            SwingUtilities.invokeLater(runFrame);
        }
    }
}
