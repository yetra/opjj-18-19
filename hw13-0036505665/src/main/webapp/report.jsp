<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>OS usage</title>
    <style>body {background-color: ${sessionScope.pickedBgCol};}</style>
</head>
<body>
    <h1>OS usage</h1>
    <p>Here are the results of OS usage in survey that we completed.</p>
    <img src="reportImage" alt="OS usage pie chart">
</body>
</html>
