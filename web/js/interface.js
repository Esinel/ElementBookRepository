var main = function () {
    
    
    $("#hiddenCreationWindow").empty();
    $("#hiddenCreationWindow").load("add-edit-ebook.html", function() {
        loadGenresForSelect();
        loadLanguagesForSelect();
    });
    
    //key bindings
    $(document).keyup(function(e) {
     if (e.keyCode == 27) { 
         // escape key maps to keycode `27`

         $("#add-new-movie-window").hide("fast");
         $("#add-new-movie-window").find("form").trigger("reset");
         $("#add-new-genre-window").hide("fast");
         $("#add-new-genre-window").find("form").trigger("reset");
         $("#add-new-user-window").hide("fast");
         $("#add-new-user-window").find("form").trigger("reset");
         $("#add-new-user-window").animate({height: "350px"});
         $("#madjija").hide("fast");
        }
    });
    
    $(document).on("click", ".add-new-window-close", function(){
        $("#add-new-movie-window").hide("fast");
        $("#add-new-movie-window").find("form").trigger("reset");
        $("#add-new-genre-window").hide("fast");
        $("#add-new-genre-window").find("form").trigger("reset");
        $("#add-new-user-window").hide("fast");
        $("#add-new-user-window").find("form").trigger("reset");
        $("#add-new-user-window").animate({height: "350px"});
        $("#madjija").hide("fast");
    });
    
    
    
    //navigation system
    
    $(document).on('click', '#menuMovies', function(){
        var menuAdmin = $(this).hasClass("aMenuLink");
        var menuModer = $(this).hasClass("mMenuLink");
        var menuGuest = $(this).hasClass("gMenuLink");
        $("#filterContainer").css("display","block");
        $("#tableHeader").text("E-books");
        $("#tableContainer").empty();
        $("#tableContainer").load("table-ebook.jsp", function() {
            if (menuAdmin){
                loadAllMovies();
             }else if (menuModer){
                loadAllMoviesForModerator();
             }else if (menuGuest){
                 loadAllMoviesForGuest();
             }
        });
        $("#hiddenCreationWindow").empty();
        $("#hiddenCreationWindow").load("add-edit-ebook.html", function() {
            loadGenresForSelect();
            loadLanguagesForSelect();
        });
        
        //dynamicaly asign role to 'addNewEntity' button
        $("#addNewButton").removeClass("genreBtn");
        $("#addNewButton").removeClass("userBtn");
        $("#addNewButton").addClass("movieBtn");
    });
    
    $(document).on('click', '#menuGenres', function(){

        $("#filterContainer").css("display","none");
        $("#tableHeader").text("Genres");
        $("#tableContainer").empty();
        $("#tableContainer").load("table-genres.html", function() {
            loadAllGenres();
        });
        $("#hiddenCreationWindow").empty();

        $("#hiddenCreationWindow").load("add-edit-genre.html", function() {
            //loadModeratorsForSelect();
        });

        $("#addNewButton").removeClass("movieBtn");
        $("#addNewButton").removeClass("userBtn");
        $("#addNewButton").addClass("genreBtn");
     });


    //dropdown genres menu
    $(document).on('click', '.ebookGenre', function(){
        var genreID = splitMyId($(this).attr('id'));
        $("#filterContainer").css("display","block");
        $("#tableHeader").text("E-books");
        $("#tableContainer").empty();
        $("#tableContainer").load("table-ebook.jsp", function() {
            loadFilteredMoviesByGenreGuest(genreID);
        });
    });

    
    $(document).on('click', '#menuUsers', function(){
        $("#filterContainer").css("display","none");
        $("#tableHeader").text("Users");
        $("#tableContainer").empty();
        $("#tableContainer").load("table-users.html", function() {
            loadAllUsers();
        });
        $("#hiddenCreationWindow").empty();
        $("#hiddenCreationWindow").load("add-edit-user.html", function() {
            loadGenresForSelect();
        });
        
        $("#addNewButton").removeClass("movieBtn");
        $("#addNewButton").removeClass("genreBtn");
        $("#addNewButton").addClass("userBtn");
    });
    
    
    // LOGOUT
    
    
    
    
    // CREATE of entities
     $(document).on('click', '#addNewButton', function(){
        if($(this).hasClass("movieBtn")){
            $("#add-new-movie-window").find("h2").text("Add new ebook");
            $("#add-new-movie-window").show("fast");
            $("#add-new-movie-window").find("#formMPurpose").text("create");
        }
        else if($(this).hasClass("genreBtn")){
            $("#add-new-genre-window").find("h2").text("Add new genre");
            $("#add-new-genre-window").show("fast");
            $("#add-new-genre-window").find("#formGPurpose").text("create");
        }
        else if($(this).hasClass("userBtn")){
            $("#add-new-user-window").find("h2").text("Add new user");
            $("#add-new-user-window").show("fast");
            $("#add-new-user-window").find("#formUPurpose").text("create");
        }
    });
    
    // EDIT of entities
    
     //EBOOK
    $(document).on('click', '.editMovie', function(){
        var editId = $(this).attr('id');
        var pureId = splitMyId(editId);
        var targetedMovie = loadMovieById(pureId);
        $("#add-new-movie-window").find("h2").text("Edit ebook");
        $("#add-new-movie-window").show("fast");
        $("#add-new-movie-window").find("#formMPurpose").val("update");
        $("#add-new-movie-window").find("#ebookID").val(pureId);
        fillFormMovie(targetedMovie);
     });
    
    
        //GENRE 
    $(document).on('click', '.editGenre', function(){
        var editId = $(this).attr('id');
        var pureId = splitMyId(editId);
        var targetedGenre = loadGenreById(pureId);
        $("#add-new-genre-window").find("h2").text("Edit genre");
        $("#add-new-genre-window").show("fast");
        $("#add-new-genre-window").find("#formGPurpose").text("update");
        $("#add-new-genre-window").find("#genreID").val(pureId);
        fillFormGenre(targetedGenre);
     });
    
        //USERS 
            //**TODO
    $(document).on('click', '.editUser', function(){
        var editId = $(this).attr('id');
        var pureId = splitMyId(editId);
        var targetedUser = loadUserById(pureId);
        $("#add-new-user-window").find("h2").text("Edit user");
        $("#add-new-user-window").show("fast");
        $("#add-new-user-window").find("#formUPurpose").text("update");
        $("#add-new-user-window").find("#userID").val(pureId);
        fillFormUser(targetedUser);
     });
    
    
    
    // show genre in case of creating moderator
    $(document).on("change", "#select-role", function(){
        if($(this).val() == 2){
            $("#madjija").show("fast");
            $("#add-new-user-window").animate({height: "380px"});
        }else{
            $("#madjija").hide("fast");
            $("#add-new-user-window").animate({height: "350px"});
        }
    });
    
    
    // filtering mechanism
    
    $("#select-genre-filter").change(function(){
        var isGuest = $(this).hasClass("gFilter");
        var isModer = $(this).hasClass("mFilter");
        var isAdmin = $(this).hasClass("aFilter");
        var genreID = $(this).val();
        if (genreID == 0){
            if(isGuest){loadAllMoviesForGuest();return;}
            if(isAdmin){loadAllMovies();return;}
        }else{
            if(isGuest){loadFilteredMoviesByGenreGuest(genreID);return;}
            if(isAdmin){loadFilteredMoviesByGenre(genreID);return;}
        }
        
    });
    
    
    $("#filterSortBtn").click(function(){
        var isGuest = $(this).hasClass("gFilter");
        var isModer = $(this).hasClass("mFilter");
        var isAdmin = $(this).hasClass("aFilter");
        if ($(this).find("i").hasClass("fa-toggle-down")){
            // sort ascending
            if(isGuest){loadFilteredMoviesAscGuest();$(this).find("i").toggleClass("fa-toggle-down");
        $(this).find("i").toggleClass("fa-toggle-up");return;}
            if(isModer){loadFilteredMoviesAscModer();$(this).find("i").toggleClass("fa-toggle-down");
        $(this).find("i").toggleClass("fa-toggle-up");return;}
            if(isAdmin){loadFilteredMoviesAsc();$(this).find("i").toggleClass("fa-toggle-down");
        $(this).find("i").toggleClass("fa-toggle-up");return;}
            
        }else{
            // sort descending
            if(isGuest){loadFilteredMoviesDescGuest();$(this).find("i").toggleClass("fa-toggle-down");
        $(this).find("i").toggleClass("fa-toggle-up");return;}
            if(isModer){loadFilteredMoviesDescModer();$(this).find("i").toggleClass("fa-toggle-down");
        $(this).find("i").toggleClass("fa-toggle-up");return;}
            if(isAdmin){loadFilteredMoviesDesc();$(this).find("i").toggleClass("fa-toggle-down");
        $(this).find("i").toggleClass("fa-toggle-up");return;}
        }
        
    });

    $("#select-days-filter").change(function(){
        var isGuest = $(this).hasClass("gFilter");
        var isModer = $(this).hasClass("mFilter");
        var isAdmin = $(this).hasClass("aFilter");
        var filterID = $(this).val();
        
        if (filterID == "7d"){
            if(isAdmin){loadFilteredMoviesDays7();return;}
            if(isModer){loadFilteredMoviesDaysModer(7);return;}
            if(isGuest){loadFilteredMoviesDaysGuest(7);return;}
            
        }else if (filterID == "30d"){
            if(isAdmin){loadFilteredMoviesDays30();return;}
            if(isModer){loadFilteredMoviesDaysModer(30);return;}
            if(isGuest){loadFilteredMoviesDaysGuest(30);return;} 
        }
    });


    $(document).on('change', '#ebookFile', function() {
        fillEbookForm();
    });
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



$(document).ready(main);


    