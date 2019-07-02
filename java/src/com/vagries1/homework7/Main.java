package com.vagries1.homework7;

import com.rbevans.bookingrate.BookingDay;
import com.rbevans.bookingrate.Rates;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String message;

    public void init() throws ServletException {
        // Do required initialization
        message = "Hello mine World";
    }

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
            // TODO: Verify month days here.

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
            // TODO: Verify duration with hike here.
        } catch (Exception e) {
            return "Invalid duration format provided.";
        }

        // logger.debug(String.format("Y %d M %d D %d Dur %d", year, month, date, duration));
        try {
            rates = new Rates(Rates.HIKE.valueOf(request.getParameter("hike")));
        } catch (Exception e) {
            return "Invalid hike value provided.";
        }

        startDay = new BookingDay(year, month, date);
        // TODO: Tour dates must be in season (June 1 - Sep 30).
        // TODO: Make sure date is _after_ today.

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

    // public void genHikeOptions()

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = null;
        String hikeOptions = "";
        String monthOptions = "";
        String dateOptions = "";
        String yearOptions = "";
        String durationOptions = "";

        try {
            RequestDispatcher rd;
            out = response.getWriter();
            response.setContentType("text/html;charset=UTF-8");
            rd = request.getRequestDispatcher("index.jsp");

            // hikeOptions

            if (request.getParameter("calc") != null) {
                request.setAttribute("result", estimate(request, response));
            }

            rd.forward(request, response);
        } finally {
            if (out != null) {
                out.close();
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
