<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Trigonometric</title>
    <style>
        body {
            background-color: ${sessionScope.pickedBgCol};
        }

        table {
            border-collapse: collapse;
        }

        td, th {
            border : 1px solid black;
            padding: 0.5em;
        }

        th {
            color: white;
            background: black;
        }
    </style>
</head>
<body>
    <h1>Trigonometric function values</h1>
    <table>
        <thead>
            <tr><th>Function</th><th>a</th><th>b</th></tr>
        </thead>
        <tbody>
            <tr><td>sin(x)</td><td>${requestScope.sinA}</td><td>${requestScope.sinB}</td></tr>
            <tr><td>cos(x)</td><td>${requestScope.cosA}</td><td>${requestScope.cosB}</td></tr>
        </tbody>
    </table>
</body>
</html>
