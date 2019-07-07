package com.vagries1.homework7;

import com.rbevans.bookingrate.BookingDay;
import com.rbevans.bookingrate.Rates;
import com.vagries1.homework5.bindings.BhcConfig;
import com.vagries1.homework5.bindings.Hike;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected static final String DEFAULT_CONFIG =
            "/com/vagries1/homework5/bindings/bhcConfigDefault.xml";

    public String estimate(HttpServletRequest request, HttpServletResponse response) {
        final String CURRENCY_FORMAT = "#.00";

        BookingDay startDay = null;
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

        rates.setBeginDate(startDay);
        rates.setDuration(duration);

        cost = rates.getCost();
        info = rates.getDetails();

        if (cost < 0) {
            return info;
        }

        df = new DecimalFormat(CURRENCY_FORMAT);
        return "Cost: $" + df.format(cost);
    }

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

    /** Dumb template function to take the place of a JSP. 
     * @param request HttpServletRequest with attributes preloaded for template.
     * @return Returns a string to be written to response stream writer.
     */
    public String genPage(HttpServletRequest request) {
        String result = Objects.toString((String) request.getAttribute("result"), "");
        String hikes = Objects.toString((String) request.getAttribute("hikes"), "");
        String dates = Objects.toString((String) request.getAttribute("dates"), "");
        String years = Objects.toString((String) request.getAttribute("years"), "");
        String months = Objects.toString((String) request.getAttribute("months"), "");
        String durations = Objects.toString((String) request.getAttribute("durations"), "");
        LinkedList<String> ll = new LinkedList<String>();
        String output = null;

        ll.add("<!DOCTYPE html>");

        ll.add("<html lang=\"en\">");
        ll.add("<head>");
        ll.add("<title>Beartooth Hiking Company</title>");
        ll.add("<meta charset=\"UTF-8\">");
        ll.add("<link href=\"/bhc.css\" rel=\"stylesheet\" type=\"text/css\" />");
        ll.add("</head>");
        ll.add("<body>");
        ll.add("<div class='body_div'>");
        ll.add("<img src=\"../images/Beartooth002-01.jpg\" alt=\"Beartooth Vista\" />");
        ll.add("<h1>Beartooth Hiking Company</h1>");

        ll.add("<form action=\"bhc_calc\" method=\"post\">");
        ll.add("Hikes:&nbsp;");
        ll.add("<select name=\"hike\">");
        ll.add(hikes);
        ll.add("</select>");
        ll.add("<br />");

        ll.add("Date:&nbsp;");
        ll.add("<select name=\"month\">");
        ll.add(months);
        ll.add("</select>&nbsp;");
        ll.add("<select name=\"date\">");
        ll.add(dates);
        ll.add("</select>");
        ll.add("<select name=\"year\">");
        ll.add(years);
        ll.add("</select>");
        ll.add("<br />");

        ll.add("Duration:&nbsp;");
        ll.add("<select name=\"duration\">");
        ll.add(durations);
        ll.add("</select>");
        ll.add("<br />");

        ll.add("<input type=\"submit\" name=\"calc\" value=\"Calculate\" />");
        ll.add("<br />");

        ll.add("</form>");

        ll.add("<h2>" + result + "</h2>");

        ll.add("</div>");
        ll.add("</body>");
        ll.add("</html>");

        output = String.join("\n", ll);
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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = null;
        String current;
        BhcConfig config;
        InputStream in = null;
        HashMap<String, String> map;

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
            // If I were to intelligently use JSP, I'd request resource here.
            // rd = request.getRequestDispatcher("index.jsp");

            // Dynamically determine hikes from config.xml
            map = new LinkedHashMap<String, String>();
            for (Hike hike : config.getHikes()) {
                map.put(hike.getKey(), hike.getName());
            }
            current = request.getParameter("hike");
            request.setAttribute("hikes", genOptions(current, map));

            // Dynamically determine month from config.xml
            map = initValidMonths(config);
            if (map != null) {
                current = request.getParameter("month");
                request.setAttribute("months", genOptions(current, map));
            }

            // Just statically doing all dates for simplicity. (No javascript is annoying.)
            map = new LinkedHashMap<String, String>();
            for (int i = 1; i <= 31; ++i) {
                map.put(Integer.toString(i), Integer.toString(i));
            }
            current = request.getParameter("date");
            request.setAttribute("dates", genOptions(current, map));

            // Just statically doing all years for simplicity. (No javascript is annoying.)
            map = new LinkedHashMap<String, String>();
            for (int i = 2018; i <= 2025; ++i) {
                map.put(Integer.toString(i), Integer.toString(i));
            }
            current = request.getParameter("year");
            request.setAttribute("years", genOptions(current, map));

            // Just statically doing all durations for simplicity. (No javascript is annoying.)
            map = new LinkedHashMap<String, String>();
            for (int i = 2; i <= 7; ++i) {
                map.put(Integer.toString(i), Integer.toString(i));
            }
            current = request.getParameter("duration");
            request.setAttribute("durations", genOptions(current, map));

            if (request.getParameter("calc") != null) {
                request.setAttribute("result", estimate(request, response));
            }

            // If I were to intelligently use JSP, I'd pass request forward here.
            // rd.forward(request, response);

            // Just dump a page template here.
            out.println(genPage(request));

        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }

    public void destroy() {
        // do nothing.
    }
}
