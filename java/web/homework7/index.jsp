
<%@ page import="java.util.Objects" %>
<% 
    String result = Objects.toString((String)request.getAttribute("result"), "");
    String date = Objects.toString((String)request.getParameter("date"), "");
    String year = Objects.toString((String)request.getParameter("year"), "");
    String duration = Objects.toString((String)request.getParameter("duration"), "");
    String month = Objects.toString((String)request.getParameter("month"), "");
%>

<!DOCTYPE html>

<html lang="en">
    <head>
        <title>Beartooth Hiking Company</title>
        <meta charset="UTF-8">
        <link href="/bhc.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div class='body_div'>
            <img src="../images/Beartooth002-01.jpg" alt="Beartooth Vista" />
            <h1>Beartooth Hiking Company</h1>

            <form action="bhc_calc" method="get">
                Hikes:&nbsp;
                <select name="hike">
                    <option value="GARDINER">Gardiner Lake</option>
                    <option value="HELLROARING">Hellroaring Plateau</option>
                    <option value="BEATEN">The Beaten Path</option>
                </select>
                <br />

                Date:&nbsp;
                <select name="month">
                    <option value="6">Jun</option>
                    <option value="7">Jul</option>
                    <option value="8">Aug</option>
                    <option value="9">Sep</option>
                </select>&nbsp;
                <select name="date">
                    <% for (int i = 1; i <= 31; ++i) { %>
                        <% if (Integer.toString(i).compareTo(date) == 0) { %>
                            <option selected><%= i %></option>
                        <% } else { %>
                            <option><%= i %></option>
                        <% } %>
                    <% } %>
                </select>
                <select name="year">
                    <% for (int i = 2018; i <= 2025; ++i) { %>
                        <% if (Integer.toString(i).compareTo(year) == 0) { %>
                            <option selected><%= i %></option>
                        <% } else { %>
                            <option><%= i %></option>
                        <% } %>
                    <% } %>
                </select>
                <br />

                Duration:&nbsp;
                <select name="duration">
                    <% for (int i = 2; i <= 7; ++i) { %>
                        <% if (Integer.toString(i).compareTo(duration) == 0) { %>
                            <option selected><%= i %></option>
                        <% } else { %>
                            <option><%= i %></option>
                        <% } %>
                    <% } %>
                </select>
                <br />

                <input type="submit" name="calc" value="Calculate" />
                <br />

            </form>

            <h2><%= result %></h2>

        </div>
    </body>
</html>