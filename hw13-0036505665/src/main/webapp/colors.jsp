<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Colors</title>
	<style>body {background-color: ${sessionScope.pickedBgCol};}</style>
</head>
<body>
	<a href="setcolor?col=white">WHITE</a>
	<a href="setcolor?col=red">RED</a>
	<a href="setcolor?col=green">GREEN</a>
	<a href="setcolor?col=cyan">CYAN</a>
</body>
</html>