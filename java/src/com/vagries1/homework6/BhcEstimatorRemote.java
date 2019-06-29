/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework6;

import com.vagries1.homework5.BhcEstimator;
import com.vagries1.homework5.bindings.BhcConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Backend and business logic class object for the BHC Hike Calculator.
 *
 * <p>The design assumes that there is some kind of presentation logic that calls back into this
 * object whenever its fields or state changes.
 *
 * @author Vincent Agriesti
 */
public class BhcEstimatorRemote extends BhcEstimator {

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(BhcEstimatorRemote.class);

    /**
     * Constructor for business logic class of the hike estimate calculator.
     *
     * @param config User options to present
     */
    public BhcEstimatorRemote(BhcConfig config) {
        super(config);
    }

    /** Primary method to trigger an estimate calculation. */
    public void estimate() {
        // BookingDay startDay;
        // Rates rates = null;

        // We're assuming that our internal state is up to date when
        // this function is called.
        int year = Integer.parseInt(validYears.get(yearIdx));
        int month = super.monthFromName(validMonths.get(monthIdx)) + 1;
        int date = Integer.parseInt(validDates.get(dateIdx));
        int duration = Integer.parseInt(validDurations.get(durationIdx));

        String req = String.format("%d:%d:%d:%d:%d", hike.getId(), year, month, date, duration);
        String resp = null;

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String HOST = "web6.jhuep.com";
        try {
            socket = new Socket(HOST, 20025);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            logger.debug("Sending: " + req);
            out.println(req);

            resp = in.readLine();
            logger.debug("Received: " + resp);

            out.close();
            in.close();
            socket.close();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + HOST);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + HOST);
            System.exit(1);
        }

        // startDay = new BookingDay(year, month, date);
        // rates = new Rates(Rates.HIKE.valueOf(hike.getKey()));
        // rates.setBeginDate(startDay);
        // rates.setDuration(duration);

        if (resp != null) {
            String[] parts = resp.split(":", 2);
            estimate = Double.parseDouble(parts[0]);
            estimated = estimate < 0 ? false : true;
            estimateInfo = parts[1];
        }
    }
}
