<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<jsp:useBean id="menjaci" type="java.util.List" scope="request"/>
<jsp:useBean id="goriva" type="java.util.List" scope="request"/>

<fmt:setBundle basename="messages.messages"/>

<html>
	<head>
		<title><fmt:message key="dodavanjeVozila"/></title>
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta HTTP-EQUIV="Expires" CONTENT="-1">
		<link href="css/theme.css" rel="stylesheet" type="text/css" />
	</head>
	<c:if test="${sessionScope.admin==null}">
		<c:redirect url="./login.jsp" />
	</c:if>
	<body>
		<form action="./CreateController" method="post"class="dodavanjeVozilaForma" accept-charset="ISO-8859-1">
			<table class="dodavanjeVozilaTabela">
				<tr>
					<td><fmt:message key="naziv"/>:</td>
					<td><input type="text" name="naziv" ></td>			
				</tr>
				<tr>
					<td><fmt:message key="godinaProizvodnje"/>:</td>
					<td><input type="text" name="godinaProizvodnje" ></td>				
				</tr>
				<tr>
					<td><fmt:message key="snaga"/>:</td>
					<td><input type="text" name="snaga" ></td>				
				</tr>
				<tr>
					<td><fmt:message key="predjeniPut"/>:</td>
					<td><input type="text" name="predjeniPut" ></td>				
				</tr>
				<tr>
					<td><fmt:message key="cena"/>:</td>
					<td><input type="text" name="cena" ></td>				
				</tr>
				<tr>
					<td><fmt:message key="radnaZapremina"/>:</td>
					<td><input type="text" name="radnaZapremina" ></td>				
				</tr>
				<tr>
					<td><fmt:message key="brojVrata"/>:</td>
					<td><input type="text" name="brojVrata" ></td>				
				</tr>
				<tr>
					<td><fmt:message key="brojSedista"/>:</td>
					<td><input type="text" name="brojSedista" ></td>				
				</tr>
				<tr>
					<td><fmt:message key="brojBrzina"/>:</td>
					<td><input type="text" name="brojBrzina" ></td>				
				</tr>
				<tr>
					<td><fmt:message key="masa"/>:</td>
					<td><input type="text" name="masa" ></td>				
				</tr>	
				<tr>
					<td><fmt:message key="postojiKlimaUredjaj"/>:</td>
					<td><input type="checkbox" name="klimaUredjaj" ></td>				
				</tr>
				<tr>
					<td><fmt:message key="gorivo"/>:</td>
					<td>
						<select name="gorivo">
						<c:forEach items="${goriva}" var="gorivo">
							<option value="${gorivo.id}">${gorivo.tipGoriva}</option>
						</c:forEach>
						</select>
					</td>				
				</tr>	
				<tr>
					<td><fmt:message key="menjac"/>:</td>
					<td>
						<select name="menjac">
						<c:forEach items="${menjaci}" var="menjac">
							<option value="${menjac.id}">${menjac.tipMenjaca}</option>
						</c:forEach>
						</select>
					</td>				
				</tr>
				<tr>
					<td><fmt:message key="datumPostavljanja"/>:</td>
					<td><input type="text" name="datumPostavljanja" ></td>				
				</tr>					
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" name="submit" value="<fmt:message key="dodaj"/>"></td>				
				</tr>
			</table>							
		</form>
		[<a href="./ReadController"><fmt:message key="listaVozila"/></a>]<br/>
		[<a href="./LogoutController"><fmt:message key="odjava"/></a>]<br/>
	<body>
</html>