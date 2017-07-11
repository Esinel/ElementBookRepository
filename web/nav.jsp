<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages.messages"/> 

<nav>
 	<ul id="menu">
 		
        <li><a href="index.jsp"><img src="images/logo.png" id="logo"></a></li>
        
        <% 	String userRole = (String) session.getAttribute("userRole"); %>
        
        <% if ("ADMIN".equals(userRole)) { %>
 		<li><a href="#" id="menuMovies" class="aMenuLink"><fmt:message key="digitalneKnjige"/></a></li>
 		<%} else if ("SUBSCRIBER".equals(userRole)) { %>
 		<%--<li><a href="#" id="menuMovies" class="mMenuLink"><fmt:message key="digitalneKnjige"/></a></li>--%>
 		<%} else if ("GUEST".equals(userRole) || (userRole == null)) { %>
 		<li><a href="#" id="menuMovies" class="gMenuLink"><fmt:message key="digitalneKnjige"/></a></li>
 		<% } %>
 		 
		
		<% if ("ADMIN".equals(userRole)) { %>
        <li><a href="#" id="menuGenres"><fmt:message key="zanrovi"/></a></li>
        <li><a href="#" id="menuUsers"><fmt:message key="korisnici"/></a></li>
        <% } else if ("GUEST".equals(userRole) || (session.getAttribute("user") == null)){ %>
        <li id="genreLinkModal"><a href="#" id="menuGenresModal"><fmt:message key="zanrovi"/>&#8964;</a>
            <ul id="genreContent">
                <c:forEach items="${applicationScope.genres}" var="genre">
                    <li>
                        <a href="#" class="ebookGenre" id="genre-${genre.id}">${genre.name}</a>
                    </li>
                </c:forEach >
            </ul>
        </li>
        <% } %>
            
        <% if ("GUEST".equals(userRole) || "SUBSCRIBER".equals(userRole) || (session.getAttribute("user") == null)) { %>
        <li><a href="#rockBottom" id="menuAbout"><fmt:message key="oNama"/></a>
        <% } %>
        
        
        <li id="langLink"><a href="#"><fmt:message key="jezik"/></a>
            <div id="langContent">
                <a href="LangChangeController?lang=1"><img src="images/flags/serbia.png">Srpski</a>
                <a href="LangChangeController?lang=2"><img src="images/flags/usa.png">English</a>
            </div>
        </li>
        
        <li><a href="AccountController"><fmt:message key="mojNalog"/></a></li>
        
        <% if (session.getAttribute("user") == null) { %>
        
        <li><a href="LoginController"><fmt:message key="prijaviSe"/></a></li>
        <%} else { %>
        <li><a href="LogoutController"><fmt:message key="odjaviSe"/></a></li>
        <% } %>
 	</ul>
 </nav>