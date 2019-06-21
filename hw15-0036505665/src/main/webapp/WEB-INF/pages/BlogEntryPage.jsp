<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Blog entry</title>
</head>
<body>
    <c:choose>
        <c:when test="${sessionScope[\"current.user.id\"] == null}">
            <p>Not logged in.</p>
        </c:when>
        <c:otherwise>
            <p>
                Logged in as ${sessionScope["current.user.fn"]}
                    ${sessionScope["current.user.ln"]}
            </p>
            <a href="servleti/logout">Log out</a>
        </c:otherwise>
    </c:choose>

    <h1>Blog entry #${requestScope.entry.id}</h1>
    <h3>${requestScope.entry.title}</h3>
    <p>${requestScope.entry.text}</p>

    <c:if test="${sessionScope[\"current.user.nick\"].equals(requestScope.nick)}">
        <p><a href="servleti/author/${requestScope.nick}/edit?id=${requestScope.entry.id}">
            Edit
        </a></p>
    </c:if>

    <h5>Comments</h5>
    <c:forEach items="${requestScope.entry.comments}" var="comment">
        <h5>${comment.postedOn} ${comment.usersEMail}</h5>
        <p>${comment.message}</p>
    </c:forEach>

    <h5>Add new comment</h5>
    <form action="servleti/main" method="post">

        <label for="email">E-mail:</label>
        <input type="text" id="email" name="email" required>

        <label for="text">Text:</label>
        <textarea name="text" id="text" cols="30" rows="10" required></textarea>

        <input type="submit" name="method" value="Add comment">
    </form>
</body>
</html>
