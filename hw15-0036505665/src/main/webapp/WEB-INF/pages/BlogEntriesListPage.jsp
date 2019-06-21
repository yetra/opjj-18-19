<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Blog entries</title>
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

    <h1>Blog entries</h1>

    <c:choose>
        <c:when test="${requestScope.entries.isEmpty()}">
            <p>This user hasn't created any blog entries.</p>
        </c:when>
        <c:otherwise>
            <c:forEach items="${requestScope.entries}" var="entry">
                <li><a href="${requestScope.nick}/${entry.id}">
                        ${entry.title}
                </a></li>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <c:if test="${sessionScope[\"current.user.nick\"].equals(requestScope.nick)}">
        <p><a href="${requestScope.nick}/new">
            New
        </a></p>
    </c:if>
</body>
</html>
