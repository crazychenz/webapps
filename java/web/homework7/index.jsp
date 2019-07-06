
<%@ page import="java.util.Objects" %>
<% 
    String result = Objects.toString((String)request.getAttribute("result"), "");
    String hikes = Objects.toString((String)request.getAttribute("hikes"), "");
    String dates = Objects.toString((String)request.getAttribute("dates"), "");
    String years = Objects.toString((String)request.getAttribute("years"), "");
    String months = Objects.toString((String)request.getAttribute("months"), "");
    String durations = Objects.toString((String)request.getAttribute("durations"), "");
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

            <form action="bhc_calc" method="post">
                Hikes:&nbsp;
                <select name="hike">
                    <%= hikes %>
                </select>
                <br />

                Date:&nbsp;
                <select name="month">
                    <%= months %>
                </select>&nbsp;
                <select name="date">
                    <%= dates %>
                </select>
                <select name="year">
                    <%= years %>
                </select>
                <br />

                Duration:&nbsp;
                <select name="duration">
                    <%= durations %>
                </select>
                <br />

                <input type="submit" name="calc" value="Calculate" />
                <br />

            </form>

            <h2><%= result %></h2>

        </div>
    </body>
</html>