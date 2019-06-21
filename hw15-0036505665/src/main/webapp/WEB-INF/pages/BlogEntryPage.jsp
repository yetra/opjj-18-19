<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Blog entry</title>

    <style type="text/css">
        .error {
            font-family: monospace;
            font-weight: bold;
            font-size: 0.9em;
            color: #FF0000;
        }
        .form-element {
            padding: 3px;
        }
    </style>
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
            <a href="logout">Log out</a>
        </c:otherwise>
    </c:choose>

    <h1>Blog entry #${requestScope.entry.id}</h1>
    <h3>${requestScope.entry.title}</h3>
    <p>${requestScope.entry.text}</p>

    <c:if test="${sessionScope[\"current.user.nick\"].equals(requestScope.nick)}">
        <p><a href="edit?id=${requestScope.entry.id}">
            Edit
        </a></p>
    </c:if>

    <h5>Comments</h5>
    <c:forEach items="${requestScope.entry.comments}" var="comment">
        <h5>${comment.postedOn} ${comment.usersEMail}</h5>
        <p>${comment.message}</p>
    </c:forEach>

    <h5>Add new comment</h5>
    <form action="main" method="post">

        <div class="form-element">
            <label for="usersEMail">E-mail:</label>
            <input type="text" id="usersEMail" name="usersEMail"
                   value="${requestScope.form.usersEMail}">
            <c:if test="${requestScope.form.hasError('usersEMail')}">
                <p class="error">${requestScope.form.getError('usersEMail')}</p>
            </c:if>
        </div>

        <div class="form-element">
            <label for="message">Message:</label>
            <textarea name="message" id="message">${requestScope.form.message}</textarea>
            <c:if test="${requestScope.form.hasError('message')}">
                <p class="error">${requestScope.form.getError('message')}</p>
            </c:if>
        </div>

        <input type="submit" name="method" value="Add comment">
    </form>
</body>
</html>
