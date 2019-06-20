<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Register</title>
    <style type="text/css">
        .error {
            font-family: fantasy;
            font-weight: bold;
            font-size: 0.9em;
            color: #FF0000;
            padding-left: 110px;
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
            <a href="servleti/logout">Log out</a>
        </c:otherwise>
    </c:choose>

    <h1>Register</h1>

    <form action="servleti/register" method="post">

        <label for="firstName">First name:</label>
        <input type="text" id="firstName" name="firstName"
               value="${requestScope.form.firstName}" required>

        <label for="lastName">Last name:</label>
        <input type="text" id="lastName" name="lastName"
               value="${requestScope.form.lastName}" required>

        <label for="email">E-mail:</label>
        <input type="text" id="email" name="email"
               value="${requestScope.form.email}" required>

        <label for="nick">Nickname:</label>
        <input type="text" id="nick" name="nick"
               value="${requestScope.form.nick}" required>
        <c:if test="${requestScope.form.hasError('nick')}">
            <p class="error">${requestScope.form.getError('nick')}</p>
        </c:if>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password"
               value="${requestScope.form.password}" required>

        <input type="submit" name="method" value="Register">
    </form>

</body>
</html>
