<%@ page import="rs.ac.uns.ftn.informatika.osa.ElementBookRepository.server.entity.User" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages.messages"/>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ElementBookRepository</title>
		<link rel="icon" href="images/logo2.png">
        <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
		<link href='https://fonts.googleapis.com/css?family=Quicksand|Maven+Pro' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<script src="js/jquery-2.1.4.js"></script>

		<script type="text/javascript">
            function checkStatus(){
                param = window.location.search;
                if(param.indexOf("?") != -1){
                    message = param.split("?")[1];
                    if(message == "success"){
                        showMeMessage("Successfully created!", "green");
                    }else{
                        showMeMessage("Something went wrong!", "red");
                    }
                }
            }
		</script>
	</head>
	
	<body>
        
        
        <div id="messagerBox">
        	Wellcome back <%= session.getAttribute("user") %>
        </div>
 
		<div id="header"></div>
		<div id="nav"></div>

		<% 	String userRole = (String) session.getAttribute("userRole"); %>
		<% User sessionUser = (User) session.getAttribute("user"); %>

		<div id="content">
            <h1 id="tableHeader"><fmt:message key="digitalneKnjige"/>
            <% if (session.getAttribute("moderGenre") != null){ %> <span style="font-size:20px;">[ <%= session.getAttribute("moderGenre") %> ]</span> <% } %>
            </h1> 
            <hr>
            <div id="filterContainer">
            	<% if ("ADMIN".equals(userRole)) { %>
	                <span id="filterGenre"><fmt:message key="zanr"/>: <select name="genre-name" id="select-genre-filter" class="aFilter"><option val=0>All</option></select></span>

					<form action="Search" method="POST" class="searchForm">
						<table>
							<tr>
								<th>Search value</th>
								<th>Search type</th>
								<th>Search condition</th>
							</tr>
							<tr>
								<td><input type="text" name="title" placeholder="Title"/></td>
								<td><select name="titlest">
									<option value="Regular">Regular</option>
									<option value="Fuzzy">Fuzzy</option>
									<option value="Phrase">Phrase</option>
								</select>
								</td>
								<td><select name="titlesc">
									<option value="+">AND</option>
									<option value="">OR</option>
								</select>
								</td>
							</tr>
							<tr>
								<td><input type="text" name="author" placeholder="Author"/></td>
								<td><select name="authorst">
									<option value="Regular">Regular</option>
									<option value="Fuzzy">Fuzzy</option>
									<option value="Phrase">Phrase</option>
								</select>
								</td>
								<td><select name="authorsc">
									<option value="+">AND</option>
									<option value="">OR</option>
								</select>
								</td>
							</tr>
							<tr>
								<td><input type="text" name="kw" placeholder="Keywords"/></td>
								<td>
									<select name="kwst">
										<option value="Regular">Regular</option>
										<option value="Fuzzy">Fuzzy</option>
										<option value="Phrase">Phrase</option>
									</select>
								</td>
								<td>
									<select name="kwsc">
										<option value="+">AND</option>
										<option value="">OR</option>
									</select>
								</td>
							</tr>

							<tr>
								<td><input type="text" name="text" placeholder="Text"/></td>
								<td><select name="textst">
									<option value="Regular">Regular</option>
									<option value="Fuzzy">Fuzzy</option>
									<option value="Phrase">Phrase</option>
								</select>
								</td>
								<td><select name="textsc">
									<option value="+">AND</option>
									<option value="">OR</option>
								</select>
								</td>
							</tr>
							<tr>
								<td><input type="text" name="text" placeholder="Language"/></td>
								<td><select name="languagest">
									<option value="Regular">Regular</option>
									<option value="Fuzzy">Fuzzy</option>
									<option value="Phrase">Phrase</option>
								</select>
								</td>
								<td><select name="languagesc">
									<option value="+">AND</option>
									<option value="">OR</option>
								</select>
								</td>
							</tr>
							<tr><td colspan="4" align="center"><input type="submit" value="Search" id="searchTrigger"/></td></tr>
						</table>
					</form>
                <% } else if ("SUBSCRIBER".equals(userRole)) { %>
                	<%--nathaaa yet--%>
				<form action="Search?genre=${sessionScope.user.category.id}" method="POST" class="searchForm">
					<table>
						<tr>
							<th>Search value</th>
							<th>Search type</th>
							<th>Search condition</th>
						</tr>
						<tr>
							<td><input type="text" name="title" placeholder="Title"/></td>
							<td><select name="titlest">
								<option value="Regular">Regular</option>
								<option value="Fuzzy">Fuzzy</option>
								<option value="Phrase">Phrase</option>
							</select>
							</td>
							<td><select name="titlesc">
								<option value="+">AND</option>
								<option value="">OR</option>
							</select>
							</td>
						</tr>
						<tr>
							<td><input type="text" name="author" placeholder="Author"/></td>
							<td><select name="authorst">
								<option value="Regular">Regular</option>
								<option value="Fuzzy">Fuzzy</option>
								<option value="Phrase">Phrase</option>
							</select>
							</td>
							<td><select name="authorsc">
								<option value="+">AND</option>
								<option value="">OR</option>
							</select>
							</td>
						</tr>
						<tr>
							<td><input type="text" name="kw" placeholder="Keywords"/></td>
							<td>
								<select name="kwst">
									<option value="Regular">Regular</option>
									<option value="Fuzzy">Fuzzy</option>
									<option value="Phrase">Phrase</option>
								</select>
							</td>
							<td>
								<select name="kwsc">
									<option value="+">AND</option>
									<option value="">OR</option>
								</select>
							</td>
						</tr>

						<tr>
							<td><input type="text" name="text" placeholder="Text"/></td>
							<td><select name="textst">
								<option value="Regular">Regular</option>
								<option value="Fuzzy">Fuzzy</option>
								<option value="Phrase">Phrase</option>
							</select>
							</td>
							<td><select name="textsc">
								<option value="+">AND</option>
								<option value="">OR</option>
							</select>
							</td>
						</tr>
						<tr>
							<td><input type="text" name="text" placeholder="Language"/></td>
							<td><select name="languagest">
								<option value="Regular">Regular</option>
								<option value="Fuzzy">Fuzzy</option>
								<option value="Phrase">Phrase</option>
							</select>
							</td>
							<td><select name="languagesc">
								<option value="+">AND</option>
								<option value="">OR</option>
							</select>
							</td>
						</tr>
						<tr><td colspan="4" align="center"><input type="submit" value="Search" id="searchTrigger"/></td></tr>
					</table>
				</form>
                <% } else if ("GUEST".equals(userRole) || userRole == null) { %>
					<%--nathaaa yet--%>
				<form action="Search" method="POST" class="searchForm">
					<table>
						<tr>
							<th>Search value</th>
							<th>Search type</th>
							<th>Search condition</th>
						</tr>
						<tr>
							<td><input type="text" name="title" placeholder="Title"/></td>
							<td><select name="titlest">
								<option value="Regular">Regular</option>
								<option value="Fuzzy">Fuzzy</option>
								<option value="Phrase">Phrase</option>
							</select>
							</td>
							<td><select name="titlesc">
								<option value="+">AND</option>
								<option value="">OR</option>
							</select>
							</td>
						</tr>
						<tr>
							<td><input type="text" name="author" placeholder="Author"/></td>
							<td><select name="authorst">
								<option value="Regular">Regular</option>
								<option value="Fuzzy">Fuzzy</option>
								<option value="Phrase">Phrase</option>
							</select>
							</td>
							<td><select name="authorsc">
								<option value="+">AND</option>
								<option value="">OR</option>
							</select>
							</td>
						</tr>
						<tr>
							<td><input type="text" name="kw" placeholder="Keywords"/></td>
							<td>
								<select name="kwst">
									<option value="Regular">Regular</option>
									<option value="Fuzzy">Fuzzy</option>
									<option value="Phrase">Phrase</option>
								</select>
							</td>
							<td>
								<select name="kwsc">
									<option value="+">AND</option>
									<option value="">OR</option>
								</select>
							</td>
						</tr>

						<tr>
							<td><input type="text" name="text" placeholder="Text"/></td>
							<td><select name="textst">
								<option value="Regular">Regular</option>
								<option value="Fuzzy">Fuzzy</option>
								<option value="Phrase">Phrase</option>
							</select>
							</td>
							<td><select name="textsc">
								<option value="+">AND</option>
								<option value="">OR</option>
							</select>
							</td>
						</tr>
						<tr>
							<td><input type="text" name="text" placeholder="Language"/></td>
							<td><select name="languagest">
								<option value="Regular">Regular</option>
								<option value="Fuzzy">Fuzzy</option>
								<option value="Phrase">Phrase</option>
							</select>
							</td>
							<td><select name="languagesc">
								<option value="+">AND</option>
								<option value="">OR</option>
							</select>
							</td>
						</tr>
						<tr><td colspan="4" align="center"><input type="submit" value="Search" id="searchTrigger"/></td></tr>
					</table>
				</form>
                <% } %>
                
                
            </div>
            <hr>
            
            <div id="tableContainer"></div>
                
            <div id="hiddenCreationWindow"></div>
            
		</div>
        
        <% if ("ADMIN".equals(session.getAttribute("userRole")) || "MODERATOR".equals(session.getAttribute("userRole"))) { %>
        <div id="addNewButton" class="add-new movieBtn">
                <a href="#" title="Add new" class="fa fa-plus-square fa-4x"></a>
        </div>
        <% } %>



		
		
		<div id="footer"></div>
        
        
        <script src="js/interface.js"></script>
        <script src="js/ajax-calls.js"></script>
        
        <% 
        if (session.isNew() && session.getAttribute("loginState") != null){ %>
        <script>
        	$(function(){
                $("#messagerBox").css("background-color", "#00D180");
                $("#messagerBox").animate({top:'0px'},356)
                         .delay(1024)
                         .animate({top:'-20px'},356);
        	});
    	</script>
       	<% } %>

		<script type="text/javascript">
            checkStatus();
		</script>
	</body>
</html>