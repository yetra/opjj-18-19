<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Register</title>

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
                <a href="../../logout">Log out</a>
            </p>
        </c:otherwise>
    </c:choose>

    <h1>New/Edit blog entry</h1>

    <form action="${requestScope.formAction}" method="post">

        <div class="form-element">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title"
                   value="${requestScope.form.title}">
            <c:if test="${requestScope.form.hasError('title')}">
                <p class="error">${requestScope.form.getError('title')}</p>
            </c:if>
        </div>

        <div class="form-element">
            <label for="text">Text:</label>
            <textarea id="text" name="text">${requestScope.form.text}</textarea>
            <c:if test="${requestScope.form.hasError('text')}">
                <p class="error">${requestScope.form.getError('text')}</p>
            </c:if>
        </div>

        <input type="submit" name="method" value="Save">
    </form>

</body>
</html>
