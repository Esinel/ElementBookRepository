<%@ page import="rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.Ebook" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages.messages"/>

<% List<Ebook> ebooks = (List<Ebook>) request.getAttribute("ebooks"); %>
<table id="movieTable" class="contentTable">
    <thead>
        <tr>
        	<% if ("ADMIN".equals(session.getAttribute("userRole")) || "MODERATOR".equals(session.getAttribute("userRole"))) { %>
            <th>ID</th>
            <% } %>
            <th><fmt:message key="naslov"/></th>
            <th><fmt:message key="autor"/></th>
            <th><fmt:message key="godinaIzdanja"/></th>
            <th><fmt:message key="kljucneRijeci"/></th>
            <th><fmt:message key="downloadLink"/></th>
            <% if ("ADMIN".equals(session.getAttribute("userRole")) || "MODERATOR".equals(session.getAttribute("userRole"))) { %>
            <th><fmt:message key="akcija"/></th>
            <% } %>
        </tr>
    </thead>
    <tbody>
        <!-- generated table rows when use of ajax -->
        <% if (ebooks != null) { %>
            <c:forEach items="${requestScope.ebooks}" var="ebook">

                <tr class="tbody">
                    <td class="filmTitle">${ebook.title}</td>
                    <td class="ebookAuthor">${ebook.author}</td>
                    <td class="ebookYear">${ebook.publicationYear}</td>
                    <td class="ebookKeywords">${ebook.keywords}</td>
                    <td class="ebookDownload">Download</td>
                </tr>
            </c:forEach >
        <% } %>

    </tbody>
</table>