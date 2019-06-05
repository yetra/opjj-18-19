<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%!
    private String getRandomColor() {
        char[] digits = "0123456789abcdef".toCharArray();
        StringBuilder sb = new StringBuilder("#");

        for (int i = 0; i < 6; i++) {
            sb.append(digits[(int) Math.floor(Math.random() * 16)]);
        }

        return sb.toString();
    }
%>
<html>
<head>
    <title>Funny story</title>
    <style>
        body {
            background-color: ${sessionScope.pickedBgCol};
            color: <% out.write(getRandomColor()); %>;
        }
    </style>
</head>
<body>
    <h1>A funny story.</h1>
    <p>This is a funny story...</p>
</body>
</html>
