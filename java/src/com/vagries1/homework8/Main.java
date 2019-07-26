/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework8;

import com.rbevans.bookingrate.BookingDay;
import com.rbevans.bookingrate.Rates;
import com.vagries1.homework5.bindings.BhcConfig;
import com.vagries1.homework5.bindings.Hike;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that is responsible for handing the BHC hike cost calculations and reporting costs or
 * errors to the user.
 *
 * @author Vincent Agriesti
 */
public class Main extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected static final String DEFAULT_CONFIG =
            "/com/vagries1/homework5/bindings/bhcConfigDefault.xml";

    /**
     * Function responsible for calculating the estimate result.
     *
     * @param request The HttpServletRequest used for fetching request parameters.
     * @return Returns the result string.
     */
    public String estimate(HttpServletRequest request, BhcConfig config) {
        final String CURRENCY_FORMAT = "#.00";
        final String DATE_FMT = "MM/dd/yyyy";

        BookingDay startDay = null;
        BookingDay todayDay = null;
        Rates rates = null;
        Calendar today;

        Calendar reqDate;
        int hikers;
        int year;
        int month;
        int date;
        int duration;
        double cost;
        String info;
        DecimalFormat df;

        try {
            hikers = Integer.parseInt(request.getParameter("hikers"));
            if (hikers < 1 || hikers > 10) {
                return "Hikers must be between 1 and 10";
            }
        } catch (Exception e) {
            return "Invalid hikers format provided.";
        }

        today = new GregorianCalendar();

        try {
            reqDate = new GregorianCalendar();
            reqDate.setTime(new SimpleDateFormat(DATE_FMT).parse(request.getParameter("date")));
            month = reqDate.get(Calendar.MONTH) + 1;
            year = reqDate.get(Calendar.YEAR);
            date = reqDate.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            return "Invalid date format provided.";
        }

        try {
            duration = Integer.parseInt(request.getParameter("duration"));
            if (duration < 2 || duration > 7) {
                return "Duration value to low. Duration must be >= 2 and <= 7.";
            }

            for (Hike hike : config.getHikes()) {
                if (hike.getKey().compareTo(request.getParameter("hike")) == 0) {
                    if (!hike.getDurationList().contains(duration)) {
                        return "Invalid duration for hike.";
                    }
                }
            }

        } catch (Exception e) {
            return "Invalid duration format provided.";
        }

        try {
            rates = new Rates(Rates.HIKE.valueOf(request.getParameter("hike")));
        } catch (Exception e) {
            return "Invalid hike value provided.";
        }

        startDay = new BookingDay(year, month, date);

        // Check that date is future (based on date)
        todayDay =
                new BookingDay(
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH) + 1,
                        today.get(Calendar.DATE));
        if (!startDay.after(todayDay)) {
            return "Start date must be no earlier than tomorrow.";
        }

        rates.setBeginDate(startDay);
        rates.setDuration(duration);

        cost = rates.getCost();
        info = rates.getDetails();

        if (cost < 0) {
            return info;
        }

        df = new DecimalFormat(CURRENCY_FORMAT);
        info =
                "Cost Per Hiker: $"
                        + df.format(cost)
                        + " / Total Cost: $"
                        + df.format(cost * hikers);
        return info;
    }

    /**
     * Helper function for generating arrays of HTML &lt;select&gt; options.
     *
     * @param current The key of the option currently selected. (Assumes only one can be selected.)
     * @param map The disctionary of key value pairs that fill out option names and text
     *     respectively.
     * @return A String object with all serialized option elements.
     */
    public String genOptions(String current, Map<String, String> map) {
        String result = null;
        LinkedList<String> lines = new LinkedList<String>();

        for (String key : map.keySet()) {
            String selected = "";
            if (current != null && current.compareTo(key) == 0) {
                selected = "selected";
            }
            lines.add(
                    "<option value=\"" + key + "\" " + selected + ">" + map.get(key) + "</option>");
        }
        result = String.join("\n", lines);
        return result;
    }

    // Source: http://roufid.com/5-ways-convert-inputstream-string-java/
    public String inputStreamToString(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String output;

        try (BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        output = stringBuilder.toString();
        return output;
    }

    /**
     * Dumb template function to take the place of the forbidden JSP.
     *
     * @param request HttpServletRequest with attributes preloaded for template.
     * @return Returns a string to be written to response stream writer.
     */
    public String genTemplatePage(HttpServletRequest req) {
        final String TMPL_PATH = "/WEB-INF/homework8/templates/index.html";
        String output = "";
        InputStream tmpl = null;

        // Fields we use in our template.
        List<String> list = Arrays.asList("result", "hikers", "hikes", "date", "durations");

        try {
            ServletContext context = this.getServletContext();
            tmpl = context.getResourceAsStream(TMPL_PATH);
            if (tmpl == null) {
                output = "no template file found.";
            } else {
                output = inputStreamToString(tmpl);
                for (String field : list) {
                    String value;
                    value = (String) req.getAttribute(field);
                    output = output.replace("{{" + field + "}}", value);
                }
            }
        } catch (IOException e) {
            output = "ERROR: Failed to access template.";
        }

        return output;
    }

    /** Handle HTTP GET request. */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = null;
        String current;
        BhcConfig config;
        InputStream in = null;
        HashMap<String, String> map;
        Calendar tomorrow = new GregorianCalendar();
        tomorrow.add(Calendar.DATE, 1);

        final int MIN_DURATION = 2;
        final int MAX_DURATION = 7;
        final int MIN_HIKERS = 1;
        final int MAX_HIKERS = 10;

        try {

            in = new Main().getClass().getResourceAsStream(DEFAULT_CONFIG);
            config = BhcConfig.unmarshallStream(in);

            if (config == null) {
                System.out.println("ERROR: Failed to load bhcConfig.xml data.");
                return;
            }

            out = response.getWriter();
            response.setContentType("text/html;charset=UTF-8");

            // Hiker count should be between 1 and 10
            map = new LinkedHashMap<String, String>();
            for (int i = MIN_HIKERS; i <= MAX_HIKERS; ++i) {
                map.put(Integer.toString(i), Integer.toString(i));
            }
            current = request.getParameter("hikers");
            if (current == null) {
                current = Integer.toString(MIN_HIKERS);
            }
            request.setAttribute("hikers", genOptions(current, map));

            // Dynamically determine hikes from config.xml
            map = new LinkedHashMap<String, String>();
            for (Hike hike : config.getHikes()) {
                map.put(hike.getKey(), hike.getName());
            }
            current = request.getParameter("hike");
            request.setAttribute("hikes", genOptions(current, map));

            current = request.getParameter("date");
            if (current == null) {
                current = "";
            }
            request.setAttribute("date", current);

            // Just statically doing all durations for simplicity. (No javascript is annoying.)
            map = new LinkedHashMap<String, String>();
            for (int i = MIN_DURATION; i <= MAX_DURATION; ++i) {
                map.put(Integer.toString(i), Integer.toString(i));
            }
            current = request.getParameter("duration");
            request.setAttribute("durations", genOptions(current, map));

            request.setAttribute("result", "");
            if (request.getParameter("calc") != null) {
                request.setAttribute("result", estimate(request, config));
            }

            // Dump a resolved page template as response.
            out.println(genTemplatePage(request));

        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }

    /** Handle HTTP POST requests. */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
