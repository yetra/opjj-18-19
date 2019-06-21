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
            </p>
            <a href="logout">Log out</a>
        </c:otherwise>
    </c:choose>

    <h1>Register</h1>

    <form action="register" method="post">

        <div class="form-element">
            <label for="firstName">First name:</label>
            <input type="text" id="firstName" name="firstName"
                   value="${requestScope.form.firstName}">
        </div>

        <div class="form-element">
            <label for="lastName">Last name:</label>
            <input type="text" id="lastName" name="lastName"
                   value="${requestScope.form.lastName}">
        </div>

        <div class="form-element">
            <label for="email">E-mail:</label>
            <input type="text" id="email" name="email"
                   value="${requestScope.form.email}">
        </div>

        <div class="form-element">
            <label for="nick">Nickname:</label>
            <input type="text" id="nick" name="nick"
                   value="${requestScope.form.nick}">
        </div>

        <div class="form-element">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password">
        </div>

        <input type="submit" name="method" value="Register">
    </form>

</body>
</html>
