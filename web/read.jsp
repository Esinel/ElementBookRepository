<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="vozila" type="java.util.List" scope="request"/>



<fmt:setBundle basename="messages.messages"/>

<html>
	<head>
		<title><fmt:message key="listaVozila"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta HTTP-EQUIV="Expires" CONTENT="-1">
		<link href="css/theme.css" rel="stylesheet" type="text/css" />
	</head>
	<c:if test="${sessionScope.admin==null}">
		<c:redirect url="./login.jsp" />
	</c:if>
	<body>
		<table class="listaVozilaTabela">
			<thead>
				<tr>
					<th><fmt:message key="naziv"/></th>
					<th><fmt:message key="godinaProizvodnje"/></th>
					<th><fmt:message key="snaga"/></th>
					<th><fmt:message key="predjeniPut"/></th>
					<th><fmt:message key="cena"/></th>
					<th><fmt:message key="radnaZapremina"/></th>
					<th><fmt:message key="brojVrata"/></th>
					<th><fmt:message key="brojSedista"/></th>
					<th><fmt:message key="brojBrzina"/></th>
					<th><fmt:message key="masa"/></th>
					<th><fmt:message key="klimaUredjaj"/></th>
					<th><fmt:message key="gorivo"/></th>
					<th><fmt:message key="menjac"/></th>
					<th><fmt:message key="datumPostavljanja"/></th>
					<th><fmt:message key="promenaVozila"/></th>
					<th><fmt:message key="obrisi"/></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${vozila}" var="vozilo">
				<tr>
					<td>${vozilo.nazivVozila}</td>
					<td>${vozilo.godinaProizvodnjeVozila}</td>
					<td>${vozilo.snagaVozila}</td>
					<td>${vozilo.predjeniPutVozila}</td>
					<td>${vozilo.cenaVozila}</td>
					<td>${vozilo.radnaZapreminaVozila}</td>
					<td>${vozilo.brojVrataVozila}</td>
					<td>${vozilo.brojSedistaVozila}</td>
					<td>${vozilo.brojBrzinaVozila}</td>
					<td>${vozilo.masaVozila}</td>
					<td><c:if test="${vozilo.voziloImaKlimaUredjaj==false}"><fmt:message key="ne"/></c:if><c:if test="${vozilo.voziloImaKlimaUredjaj==true}"><fmt:message key="da"/></c:if></td>
					<td>${vozilo.gorivo.tipGoriva}</td>
					<td>${vozilo.menjac.tipMenjaca}</td>
					<td>${vozilo.datumPostavljanjaVozilaString}</td>
					<td><a href="./PrepareUpdateController?voziloId=${vozilo.id}"><fmt:message key="promenaVozila"/></a></td>
					<td><a href="./DeleteController?voziloId=${vozilo.id}"><fmt:message key="obrisi"/></a></td>			
				</tr>
				</c:forEach>
			</tbody>
		</table>
		[<a href="./MessageChange?jezik=1"><fmt:message key="srpski"/></a>]<br/>
		[<a href="./MessageChange?jezik=2"><fmt:message key="engleski"/></a>]<br/>
		[<a href="./PrepareCreateController"><fmt:message key="dodavanjeNovogVozila"/></a>]<br/>
		[<a href="./LogoutController"><fmt:message key="odjava"/></a>]<br/>
		
	</body>	
</html>