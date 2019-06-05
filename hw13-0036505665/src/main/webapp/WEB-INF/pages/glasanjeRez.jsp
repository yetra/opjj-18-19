<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Rezultati</title>
    <style type="text/css">
        body {
            background-color: ${sessionScope.pickedBgCol};
        }

        table {
            border-collapse: collapse;
        }

        table.rez td, th {
            text-align: center;
            border: 1px solid black;
        }
    </style>
</head>
<body>
    <h1>Rezultati glasanja</h1>
    <p>Ovo su rezultati glasanja.</p>
    <table class="rez">
        <thead>
            <tr><th>Bend</th><th>Broj glasova</th></tr>
        </thead>
        <tbody>
            <c:forEach var="result" items="${requestScope.results}">
                <tr><td>${result.name}</td><td>${result.score}</td></tr>
            </c:forEach>
        </tbody>
    </table>

    <h2>Grafički prikaz rezultata</h2>
    <img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />

    <h2>Rezultati u XLS formatu</h2>
    <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>

    <h2>Razno</h2>
    <p>Primjeri pjesama pobjedničkih bendova:</p>
    <ul>
        <c:forEach var="winner" items="${requestScope.winners}">
            <li><a href="${winner.link}" target="_blank">${winner.name}</a></li>
        </c:forEach>
    </ul>
</body>
</html>
