<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Polls</title>
</head>
<body>
    <h1>Polls</h1>

    <p>A list of currently stored polls:</p>
    <ul>
        <c:forEach var="poll" items="${requestScope.polls}">
            <li>
                <a href="glasanje?pollID=${poll.ID}">
                        ${poll.title}
                </a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>