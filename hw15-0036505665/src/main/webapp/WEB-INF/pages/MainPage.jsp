<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Main</title>

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
                <a href="logout">Log out</a>
            </p>
        </c:otherwise>
    </c:choose>

    <h1>Main</h1>

    <c:if test="${sessionScope[\"current.user.id\"] == null}">
        <h3>Log in</h3>
        <form action="main" method="post">

            <div class="form-element">
                <label for="nick">Nickname:</label>
                <input type="text" id="nick" name="nick" value="${requestScope.form.nick}">
                <c:if test="${requestScope.form.hasError('nick')}">
                    <p class="error">${requestScope.form.getError('nick')}</p>
                </c:if>
            </div>

            <div class="form-element">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password">
                <c:if test="${requestScope.form.hasError('password')}">
                    <p class="error">${requestScope.form.getError('password')}</p>
                </c:if>
            </div>

            <input type="submit" name="method" value="Log in">
        </form>
    </c:if>

    <h3><a href="register">Register new user</a></h3>

    <h3>Existing users:</h3>
    <ul>
        <c:forEach items="${requestScope.users}" var="user">
            <li><a href="author/${user.nick}">
                    ${user.nick}: ${user.firstName} ${user.lastName}
            </a></li>
        </c:forEach>
    </ul>

</body>
</html>
