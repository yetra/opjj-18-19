<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>${requestScope.poll.title}</title>
</head>
<body>
    <h1>${requestScope.poll.title}</h1>
    <p>${requestScope.poll.message}</p>
    <ol>
        <c:forEach var="option" items="${requestScope.options}">
            <li><a href="glasanje-glasaj?pollID=${requestScope.poll.ID}&id=${option.ID}">
                    ${option.optionTitle}
            </a></li>
        </c:forEach>
    </ol>
</body>
</html>
