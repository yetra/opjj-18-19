<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Rezultati</title>
    <style type="text/css">
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
            <tr><th>Naziv</th><th>Broj glasova</th></tr>
        </thead>
        <tbody>
            <c:forEach var="option" items="${requestScope.options}">
                <tr>
                    <td>${option.optionTitle}</td>
                    <td>${option.votesCount}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <h2>Grafički prikaz rezultata</h2>
    <img alt="Pie-chart" src="glasanje-grafika?pollID=${param.pollID}" width="400" height="400" />

    <h2>Rezultati u XLS formatu</h2>
    <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${param.pollID}">ovdje</a></p>

    <h2>Razno</h2>
    <p>Linkovi pobjednika:</p>
    <ul>
        <c:forEach var="winner" items="${requestScope.winners}">
            <li><a href="${winner.optionLink}" target="_blank">
                    ${winner.optionTitle}
            </a></li>
        </c:forEach>
    </ul>
</body>
</html>
