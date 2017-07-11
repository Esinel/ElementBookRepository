<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="messages.messages"/>

<!DOCTYPE html>
<html lang="">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="mojNalog"/></title>
    <link rel="icon" href="images/logo2.png">
    <script src="js/jquery-2.1.4.js" type="text/javascript"></script>
    <script src="js/login-acc.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="css/my-acc.css">
    <link href='https://fonts.googleapis.com/css?family=Quicksand|Maven+Pro' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
</head>
    
    

<body>
    <div id="messagerBox"></div>
    
    <div id="mainContainer">
        <h2 style="text-align:center;padding-top:20px;padding-bottom:20px;">USER PANEL</h2>
        <img src="images/myAcc.png" id="userAccIcon">
        <div style="text-align:center;padding-bottom:20px;"><fmt:message key="dobrodosli"/> <span id="accUsername"><%= session.getAttribute("user") %></span></div>
        <hr>
        <h2 style="text-align:center;padding-top:20px;padding-bottom:20px;"><fmt:message key="promeniSvojuLozinku"/></h2>
            
        <form id="userPassForm">
            <input type="text" required placeholder="<fmt:message key='staraLozinka'/>" class="txtfield" name="oldPass" ><br>
            <input type="text" required placeholder="<fmt:message key='novaLozinka'/>" class="txtfield" name="newPass" id="newPass"><br>
            <input type="text" required placeholder="<fmt:message key='ponovoLozinka'/>" class="txtfield" name="repeatPass" id="repPass"><br>
            <input type="submit" value="<fmt:message key='potvrdi'/>" class="flatbtn-blu" id="confirmPass">
        </form>
            
            
        <hr>
            
            
        <h2 style="text-align:center;padding-top:20px;padding-bottom:20px;"><fmt:message key="promeniSvojeLicnePodatke"/></h2>
        <form id="userPersonalForm">
            <input type="text" required placeholder="<fmt:message key='novoIme'/>" class="txtfield" name="newFirstname"><br>
            <input type="text" required placeholder="<fmt:message key='novoPrezime'/>" class="txtfield" name="newLastname"><br>
            <input type="submit" value="<fmt:message key='potvrdi'/>" class="flatbtn-blu" id="confirmPersonal">
        </form>
    </div>
</body>
</html>
