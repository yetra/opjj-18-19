<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Power XLS Error</title>
    <style>body {background-color: ${sessionScope.pickedBgCol};}</style>
</head>
<body>
    <h1>Oops!</h1>
    <p>${requestScope.error}</p>
</body>
</html>
