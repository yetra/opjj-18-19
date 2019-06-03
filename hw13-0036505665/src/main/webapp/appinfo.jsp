<%@ page import="java.util.concurrent.TimeUnit" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Web Application Runtime</title>
    <style>body {background-color: ${sessionScope.pickedBgCol};}</style>
</head>
<body>
    <h1>Web Application Runtime</h1>
    <p>This application has been running for <%
        long startTime = (long) request.getServletContext().getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) - TimeUnit.DAYS.toHours(days);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minutes);
        long miliseconds = duration - TimeUnit.SECONDS.toMillis(seconds);

        out.write(days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds " +
                "and " + miliseconds + " miliseconds.");
    %>
    </p>
</body>
</html>
