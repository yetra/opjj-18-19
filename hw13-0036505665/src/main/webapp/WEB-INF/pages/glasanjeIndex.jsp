<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Glasanje za omiljeni bend</title>
    <style>body {background-color: ${sessionScope.pickedBgCol};}</style>
</head>
<body>
    <h1>Glasanje za omiljeni bend:</h1>
    <p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
    <ol>
        <c:forEach var="band" items="${requestScope.index}">
            <li><a href="glasanje-glasaj?id=${band.ID}">${band.name}</a></li>
        </c:forEach>
    </ol>
</body>
</html>
