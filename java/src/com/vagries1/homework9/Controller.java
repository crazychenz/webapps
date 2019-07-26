/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework9;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller (Servlet) that is responsible for handing the bridge between the Model and the View
 * (JSP)
 *
 * @author Vincent Agriesti
 */
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Model model = new Model();

    /** Handle HTTP GET request. */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd;
        rd = request.getRequestDispatcher("index.jsp");

        String hikers = request.getParameter("hikers");
        String date = request.getParameter("date");
        String duration = request.getParameter("duration");
        String hike = request.getParameter("hike");

        request.setAttribute("hikers", model.getHikersOptions(hikers));
        request.setAttribute("hikes", model.getHikeOptions(hike));
        request.setAttribute("date", Objects.toString(date, ""));
        request.setAttribute("durations", model.getDurationOptions(duration));
        request.setAttribute("result", "");
        if (request.getParameter("calc") != null) {
            request.setAttribute("result", model.estimate(hikers, date, duration, hike));
        }

        rd.forward(request, response);
    }

    /** Handle HTTP POST requests. */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
