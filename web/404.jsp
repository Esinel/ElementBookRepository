<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:setBundle basename="messages.messages"/>

<html>
	<head>
		<title><fmt:message key="stranicaNijePronadjenaNaslov"/></title>
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta HTTP-EQUIV="Expires" CONTENT="-1">
		<link href="./theme.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<h1><fmt:message key="stranicaNijePronadjenaNaslov"/></h1>
		<p><fmt:message key="stranicaNijePronadjenaPoruka"/></p>
	</body>
</html>	