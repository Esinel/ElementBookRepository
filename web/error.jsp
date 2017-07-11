<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="messages.messages"/>
        
<html>
<!-- 
    <head>
            <title><fmt:message key="greskaNaslov"/></title>
            <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
            <meta HTTP-EQUIV="Expires" CONTENT="-1">
            <link href="css/theme.css" rel="stylesheet" type="text/css" />
        </head>
        <body>
            <h1 align="center">Opps...</h1>
            <table width="100%" border="1" style="background-color:#F2C8C7;">
            <tr valign="top">
            <td width="40%"><b>Error:</b></td>
            <td>${pageContext.exception}</td>
            </tr>
            <tr valign="top">
            <td><b>URI:</b></td>
            <td>${pageContext.errorData.requestURI}</td>
            </tr>
            <tr valign="top">
            <td><b>Status code:</b></td>
            <td>${pageContext.errorData.statusCode}</td>
            </tr>
            <tr valign="top">
            <td><b>Stack trace:</b></td>
            <td>
            <c:forEach var="trace" 
                     items="${pageContext.exception.stackTrace}">
            <p>${trace}</p>
            </c:forEach>
            </td>
            </tr>
            </table>
        </body>
         -->
    </html>	