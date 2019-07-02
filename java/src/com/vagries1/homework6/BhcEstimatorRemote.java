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

    /**
     * Utility function to simplify setting estimateInfo in error state.
     *
     * @param msg String message to set estimateInfo to.
     * @param e Exception to pass to logger. May be null when no exception object available.
     */
    private void estimateError(String msg, Exception e) {
        estimateInfo = msg;
        logger.error(estimateInfo);
        if (e != null) {
            logger.debug("", e);
        }
    }

    /** Primary method to trigger a remote estimate calculation. */
    public void estimate() {
        // We're assuming that our internal state is up to date when
        // this function is called.

        int year;
        int month;
        int date;
        int duration;

        String value = "";
        final String REQ_FMT = "%d:%d:%d:%d:%d";
        String req;
        String resp;
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String HOST = "web6.jhuep.com";
        final int HOST_PORT = 20025;

        // Assume failure.
        estimated = false;

        // Translate and sanitize input.
        try {
            value = validYears.get(yearIdx);
            year = Integer.parseInt(value);
        } catch (Exception e) {
            estimateError("Invalid year value: " + value, e);
            return;
        }

        try {
            value = validMonths.get(monthIdx);
            month = super.monthFromName(value) + 1;
        } catch (Exception e) {
            estimateError("Invalid month value: " + value, e);
            return;
        }

        try {
            value = validDates.get(dateIdx);
            date = Integer.parseInt(value);
        } catch (Exception e) {
            estimateError("Invalid date value: " + value, e);
            return;
        }

        try {
            value = validDurations.get(durationIdx);
            duration = Integer.parseInt(value);
        } catch (Exception e) {
            estimateError("Invalid duration value: " + value, e);
            return;
        }

        // Construct request.
        req = String.format(REQ_FMT, hike.getId(), year, month, date, duration);
        resp = null;
        try {
            // Transfer command/response.
            socket = new Socket(HOST, HOST_PORT);
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
            estimateError("Unable to resolve hostname: " + HOST, e);
        } catch (IOException e) {
            estimateError("Couldn't get I/O for the connection to: " + HOST, e);
        } catch (Exception e) {
            estimateError("Failed to transfer command/response with: " + HOST, e);
        }

        if (resp != null) {
            // Parse response.
            String[] parts;
            String info;

            parts = resp.split(":", 2);
            if (parts.length < 2) {
                estimateError("Failed to parse response from: " + HOST, null);
                return;
            }

            try {
                estimate = Double.parseDouble(parts[0]);
                info = parts[1];
            } catch (Exception e) {
                estimateError("Failed to parse values from response: " + HOST, e);
                return;
            }

            estimateInfo = info;

            // If we made it here and the estimate is greater than zero
            // we finally set it to true.
            estimated = estimate < 0 ? false : true;
        }
    }
}
