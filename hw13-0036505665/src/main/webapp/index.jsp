<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Background color chooser</title>
	<style>body {background-color: ${sessionScope.pickedBgCol};}</style>
</head>
<body>
	<a href="colors.jsp">Background color chooser</a>
</body>
</html>