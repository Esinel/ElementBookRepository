var main = function () {
    
    if (document.getElementById('newPass') !== null){
    document.getElementById("newPass").addEventListener('change', checkPasswordValidity, false);
    document.getElementById("repPass").addEventListener('change', checkPasswordValidity, false);
        }
    
    $("#loginUsername").focus();
    
    
    
        // CHANGE USER PASS
    $("#userPassForm").submit(function(){
        var oldPass = $(this).find("input:eq(0)").val();
        var newPass = $(this).find("input:eq(1)").val();
        var repPass = $(this).find("input:eq(2)").val();

        $.ajax({
            method: "POST",
            url: "AccountController",
            data: {'purpose' : "changePass",
                   'oldPass' : oldPass,
                   'newPass' : newPass,
                   'repPass' : repPass},
            dataType: "json",
            success: function (response){
                if (response.message == "wrongPassword"){
                    showMeMessage("Wrong password! Try again.", "red");
                }else{
                    showMeMessage("Successfully changed!", "green");
                    $("#userPassForm").trigger("reset");
                    $("#userPassForm").find("input:eq(2)").blur();
                }
            },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        	event.preventDefault();
    });
    
        // CHANGE PERS DETAILS
    $("#userPersonalForm").submit(function(){
        var newFirstname = $(this).find("input:eq(0)").val();
        var newLastname = $(this).find("input:eq(1)").val();
        $.ajax({
            method: "POST",
            url: "AccountController",
            data: {'purpose' : "changeName",
                   'newFirstname' : newFirstname,
                   'newLastname' : newLastname},
            dataType: "json",
            success: function (response){
                var user = response;
                showMeMessage("Successfully changed!", "green");
                $("#accUsername").text(user.firstName + ' ' + user.lastName);
                $("#userPersonalForm").trigger("reset");
                $("#userPersonalForm").find("input:eq(1)").blur()
            },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        	event.preventDefault();
    });
    
    
    // CHECK REP PASS
    
}


function showMeMessage(value, color){
    $("#messagerBox").text(value);
    if(color == "green"){
        $("#messagerBox").css("background-color", "#00D180");
    }else if(color == "red"){
        $("#messagerBox").css("background-color", "#FF5555");
    }else if(color == "yellow"){
        $("#messagerBox").css("background-color", "#A19500");
    }
    $("#messagerBox").animate({top:'0px'},356)
                     .delay(1024)
                     .animate({top:'-20px'},356);
}


var checkPasswordValidity = function(){
    var password1 = document.getElementById('newPass');
    var password2 = document.getElementById('repPass');
    
    if (password1.value != password2.value) {
        password2.setCustomValidity('Passwords must match.');
    } else {
        password2.setCustomValidity('');
    } 
}
$(document).ready(main);