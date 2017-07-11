<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Search results</title>
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <link href='https://fonts.googleapis.com/css?family=Quicksand|Maven+Pro' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="css/result.css">

</head>
<body>
    <h1>Your search results:</h1>

    <table>
        <c:forEach items="${data}" var="ebook">
            <tr>
                <td><a href="Download?file=${ebook.filename}" target="_blank">${ebook.title}</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
