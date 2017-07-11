<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>



<fmt:setBundle basename="messages.messages"/>

    
    
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title><fmt:message key="prijaviSe"/></title>
    <link rel="icon" href="images/logo.png">
    <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<meta HTTP-EQUIV="Expires" CONTENT="-1">
	<script src="js/jquery-2.1.4.js" type="text/javascript"></script>
    <script src="js/login-acc.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <link href='https://fonts.googleapis.com/css?family=Play' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    
    <style>
    #messageBox{
	    width: 100%;
	    height: 20px;
	    position: absolute;
	    top: -20px;
	    background-color: #00D180;
	    text-align: center;
	    font-weight: bold;
	    color: #E7E7E7;
    }
    </style>
</head>
<body>  
    
    
    
    <div id="messageBox"></div>
    
    
    <div id='login-window'>
        <div id='login-label'><h2><fmt:message key="prijaviSe"/></h2></div>
            <form id="loginForm" name="loginForm" action="LoginController" method="POST">
                <div>
                    <input type="text" id="loginUsername" name="username" class="txtfield" tabindex="1" placeholder=<fmt:message key="korisnickoIme"/>>
                </div>
                <div>
                    <input type="password" id="loginPassword" name="password" class="txtfield" tabindex="2" required style="margin-left:0.8%" placeholder=<fmt:message key="lozinka"/>>
                </div>
                <div>
                    <input type="submit" id="loginBtn" class="flatbtn-blu hidemodal" value="<fmt:message key='prijaviSe'/>" tabindex="3">
                </div>
            </form>
    </div>
    
    
    <% if (session.getAttribute("loginState") == "false"){ %>
        <script>
        	$(function(){
        		$("#messageBox").text('Incorrect username or password!');
                $("#messageBox").css("background-color", "#FF5555");
                $("#messageBox").animate({top:'0px'},356)
                         .delay(1024)
                         .animate({top:'-20px'},356);
        	});
        </script>
    <% } %>
    	
</body>
</html>
