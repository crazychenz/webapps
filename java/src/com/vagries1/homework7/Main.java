/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework7;

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
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
    public String estimate(HttpServletRequest request) {
        final String CURRENCY_FORMAT = "#.00";

        BookingDay startDay = null;
        BookingDay todayDay = null;
        Rates rates = null;
        Calendar today;

        int year;
        int month;
        int date;
        int duration;
        double cost;
        String info;
        DecimalFormat df;

        today = new GregorianCalendar();

        try {
            year = Integer.parseInt(request.getParameter("year"));
            if (year < today.get(Calendar.YEAR)) {
                return "Year must be present or future.";
            } else if (year > 2025) {
                return "Year must be less than or equal to 2025.";
            }
        } catch (Exception e) {
            return "Invalid year format provided.";
        }

        try {
            month = Integer.parseInt(request.getParameter("month"));
            if (month < 1) {
                return "Month value to low. Month must be >= 1 and <= 12.";
            } else if (month > 12) {
                return "Month value to high. Month must be >= 1 and <= 12.";
            }
        } catch (Exception e) {
            return "Invalid month format provided.";
        }

        try {
            date = Integer.parseInt(request.getParameter("date"));
            if (date < 1) {
                return "Date value to low. Date must be >= 1 and <= 31.";
            } else if (date > 31) {
                return "Date value to high. Date must be >= 1 and <= 31.";
            }

        } catch (Exception e) {
            return "Invalid date format provided.";
        }

        try {
            duration = Integer.parseInt(request.getParameter("duration"));
            if (duration < 2) {
                return "Duration value to low. Duration must be >= 1 and <= 31.";
            } else if (duration > 7) {
                return "Duration value to high. Duration must be >= 1 and <= 31.";
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
        info = "Cost: $" + df.format(cost);
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
        final String TMPL_PATH = "/WEB-INF/homework7/templates/index.html";
        String output = "";
        InputStream tmpl = null;

        // Fields we use in our template.
        List<String> list =
                Arrays.asList("result", "hikes", "dates", "years", "months", "durations");

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

    /**
     * Method to initialize month values during construction.
     *
     * @param config The BhcConfig object to get minimum and maximum months from.
     * @return A map of option value as key and option text as value.
     */
    private LinkedHashMap<String, String> initValidMonths(BhcConfig config) {
        Calendar cal;
        int minMonth;
        int maxMonth;
        LinkedHashMap<String, String> monthMap;

        monthMap = new LinkedHashMap<String, String>();
        cal = Calendar.getInstance();

        try {
            minMonth = config.getAppointmentRange().getMinMonthEnum().ordinal();
        } catch (IllegalArgumentException e) {
            return null;
        }

        try {
            maxMonth = config.getAppointmentRange().getMaxMonthEnum().ordinal();
        } catch (IllegalArgumentException e) {
            return null;
        }

        for (int i = minMonth; i < maxMonth; ++i) {
            String month;
            cal.set(1, i, 1);
            month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
            monthMap.put(Integer.toString(i + 1), month);
        }

        return monthMap;
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

        final int MIN_DATE = 1;
        final int MAX_DATE = 31;
        final int MIN_YEAR = 2019;
        final int MAX_YEAR = 2025;
        final int MIN_DURATION = 2;
        final int MAX_DURATION = 7;

        try {
            // RequestDispatcher declaration should go here.
            // RequestDispatcher rd;

            in = new Main().getClass().getResourceAsStream(DEFAULT_CONFIG);
            config = BhcConfig.unmarshallStream(in);

            if (config == null) {
                System.out.println("ERROR: Failed to load bhcConfig.xml data.");
                return;
            }

            out = response.getWriter();
            response.setContentType("text/html;charset=UTF-8");

            // Dynamically determine hikes from config.xml
            map = new LinkedHashMap<String, String>();
            for (Hike hike : config.getHikes()) {
                map.put(hike.getKey(), hike.getName());
            }
            current = request.getParameter("hike");
            request.setAttribute("hikes", genOptions(current, map));

            // Dynamically determine month from config.xml
            map = initValidMonths(config);
            request.setAttribute("months", "");
            if (map != null) {
                current = request.getParameter("month");
                request.setAttribute("months", genOptions(current, map));
            }

            // Just statically doing all dates for simplicity. (No javascript is annoying.)
            map = new LinkedHashMap<String, String>();
            for (int i = MIN_DATE; i <= MAX_DATE; ++i) {
                map.put(Integer.toString(i), Integer.toString(i));
            }
            current = request.getParameter("date");
            request.setAttribute("dates", genOptions(current, map));

            // Just statically doing all years for simplicity. (No javascript is annoying.)
            map = new LinkedHashMap<String, String>();
            for (int i = MIN_YEAR; i <= MAX_YEAR; ++i) {
                map.put(Integer.toString(i), Integer.toString(i));
            }
            current = request.getParameter("year");
            request.setAttribute("years", genOptions(current, map));

            // Just statically doing all durations for simplicity. (No javascript is annoying.)
            map = new LinkedHashMap<String, String>();
            for (int i = MIN_DURATION; i <= MAX_DURATION; ++i) {
                map.put(Integer.toString(i), Integer.toString(i));
            }
            current = request.getParameter("duration");
            request.setAttribute("durations", genOptions(current, map));

            request.setAttribute("result", "");
            if (request.getParameter("calc") != null) {
                request.setAttribute("result", estimate(request));
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
