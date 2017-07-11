var ajaxFun = function () {
    
    
    // RATING MACHANISM
    
    $(document).on("submit", ".ratingForm", function(){
        var formId = $(this).attr('id');
        var pureId = splitMyId(formId);
        var ratingVal = $(this).find("select").val();
        $.ajax({
            method:"POST",
            url: "RatingController",
            data: {rating : ratingVal,
                   movieId: pureId},
            dataType:"text",
            success: function(response){
                if (response == "success"){
                    showMeMessage("Successfully rated!", "green");
                    loadAllMoviesForGuest();
                }else if (response == "failed"){
                    showMeMessage("Movie allready rated!", "red");
                }else{
                    window.location.replace(response);
                }
            },
            error: function(errorThrown) {
                alert(errorThrown);
            }
        });
        event.preventDefault();
    });
    
    
//-------------------------     
    // CREATION OF ENTITIES
        
        //EBOOK
	/*
    $(document).on("submit", "#add-new-movie-form", function(){
        var movie = JSON.stringify($(this).serializeObject());
        var formPurpose = $(this).find("#formMPurpose").text();
        $.ajax({
            method: "POST",
            url: "EbookController",
            data: {purpose: formPurpose,
                   movieJson: movie},
            success: function(){
                if (formPurpose == "create"){
                    showMeMessage("Successfully created!", "green");
                }else if (formPurpose == "update"){
                    showMeMessage("Successfully edited!", "yellow");
                }
                if (checkSessionUserRole() == "ADMIN"){
                    loadAllMovies();
                }else{
                    loadAllMoviesForModerator();
                }
                
                $("#add-new-movie-window").css("display", "none");
                $("#add-new-movie-window").find("form").trigger("reset");
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        event.preventDefault();
    });*/

    
    
        //GENRE
    $(document).on("submit", "#add-new-genre-form", function(){
        var genre = JSON.stringify($(this).serializeObject());
        var formPurpose = $(this).find("#formGPurpose").text();
        $.ajax({
            method: "POST",
            url: "GenreController",
            data: {purpose: formPurpose,
                   genreJson: genre},
            success: function(){
                if (formPurpose == "create"){
                    showMeMessage("Successfully created!", "green");
                }else if (formPurpose == "update"){
                    showMeMessage("Successfully edited!", "yellow");
                }
                loadAllGenres();
                $("#add-new-genre-window").css("display", "none");
                $("#add-new-genre-window").find("form").trigger("reset");
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        event.preventDefault();
    });

    
    
        //USER
    $(document).on("submit", "#add-new-user-form", function(){
       var user = JSON.stringify($(this).serializeObject());
        var formPurpose = $(this).find("#formUPurpose").text();
        $.ajax({
            method: "POST",
            url: "UserController",
            data: {purpose: formPurpose,
                   userJson: user},
            success: function(){
                if (formPurpose == "create"){
                    showMeMessage("Successfully created!", "green");
                }else if (formPurpose == "update"){
                    showMeMessage("Successfully edited!", "yellow");
                }
                loadAllUsers();
                $("#add-new-user-window").css("display", "none");
                $("#add-new-user-window").find("form").trigger("reset");
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
        });
        event.preventDefault(); 
    });

    
    

    
//-------------------------    
    // DELETING ENTITIES
    
        //MOVIE
    $(document).on('click', '.deleteMovie', function(){
        var deleteId = $(this).attr('id');
        var pureId = splitMyId(deleteId);
        $.ajax({
            url: 'EbookController',
            method: "POST",
            data:{purpose:"delete",
                  movieId:pureId},
            success: function (response){
                showMeMessage("Successfully deleted!", "red");
                loadAllMovies();
            }
        });
    });
    
    
        //GENRE 
    $(document).on('click', '.deleteGenre', function(){
        var deleteId = $(this).attr('id');
        var pureId = splitMyId(deleteId);
        $.ajax({
            url: 'GenreController',
            method: "POST",
            data:{purpose:"delete",
                  genreId:pureId},
            success: function (response){
                showMeMessage("Successfully deleted!", "red");
                loadAllGenres();
            }
        });
    });
    
        //USERS 
    $(document).on('click', '.deleteUser', function(){
        var deleteId = $(this).attr('id');
        var pureId = splitMyId(deleteId);
        $.ajax({
            url: 'UserController',
            method: "POST",
            data:{purpose:"delete",
                  userId:pureId},
            success: function (response){
                showMeMessage("Successfully deleted!", "red");
                loadAllUsers();
            }
        });
    });
}


//------------------------- 
// INITIAL AJAX CALLS FOR TABLES


function loadAllMovies(){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "EbookController",
		dataType: "json",
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
				film = response[i];
                editID = "edit-" + film.id.toString();
                deleteID = "delete-" + film.id.toString();
                var actionTab = $('<div style="margin-left:5px;margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMovie" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;">|</span><a href="#" class="fa fa-times fa-2x deleteMovie" id='+deleteID+' aria-hidden="true"/></div>');
				var filmTr = $('<tr class="tbody"></tr>');

				var idTd = $("<td></td>");
				idTd.addClass("orderNumber");
				idTd.text(film.id);
				filmTr.append(idTd);
				var titleTd = $("<td></td>");
				titleTd.addClass("filmTitle");
				titleTd.text(film.title);
				filmTr.append(titleTd);
				var authorTd = $("<td></td>");
                authorTd.addClass("ebookAuthor");
                authorTd.text(film.author);
				filmTr.append(authorTd);
                var yearTd = $("<td></td>");
                yearTd.addClass("ebookYear");
                yearTd.text(film.publicationYear);
                filmTr.append(yearTd);
				var keywordsTd = $("<td></td>");
                keywordsTd.addClass("ebookKeywords");
                keywordsTd.text(film.keywords);
				filmTr.append(keywordsTd);
				var downloadTd = $("<td></td>");
				var downloadLink = $("<a href=Download?file="+film.filename+" target='_blank'>Download</a>");
				downloadTd.addClass("ebookDownload");
                downloadTd.append(downloadLink);
				filmTr.append(downloadTd);
                var actionTd = $("<td></td>");
                actionTd.addClass("actionTab");
                actionTd.append(actionTab);
                filmTr.append(actionTd);
				$("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}

function loadAllMoviesForModerator(){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "EbookController",
        data:{purpose: "getAllForModerator"},
		dataType: "json",
        cache: false,
		success: function(response) {
            for(var i=0; i<response.length; i++) {
                film = response[i];
                var filmTr = $('<tr class="tbody"></tr>');
                var titleTd = $("<td></td>");
                titleTd.addClass("filmTitle");
                titleTd.text(film.title);
                filmTr.append(titleTd);
                var authorTd = $("<td></td>");
                authorTd.addClass("ebookAuthor");
                authorTd.text(film.author);
                filmTr.append(authorTd);
                var yearTd = $("<td></td>");
                yearTd.addClass("ebookYear");
                yearTd.text(film.publicationYear);
                filmTr.append(yearTd);
                var keywordsTd = $("<td></td>");
                keywordsTd.addClass("ebookKeywords");
                keywordsTd.text(film.keywords);
                filmTr.append(keywordsTd);
                var downloadTd = $("<td></td>");
                var downloadLink = $("<a href=Download?file="+film.filename+" target='_blank'>Download</a>");
                downloadTd.addClass("ebookDownload");
                downloadTd.append(downloadLink);
                filmTr.append(downloadTd);
                $("#movieTable > tbody").append(filmTr);
            }
        },
        error: function(request, options, error) {
            alert(error);
        }
    });
}


function loadAllMoviesForGuest(){
    $("#movieTable > tbody").empty();
    $.ajax({
        method: "GET",
        url: "EbookController",
        dataType: "json",
        cache: false,
        success: function(response) {
            for(var i=0; i<response.length; i++) {
                film = response[i];
                var filmTr = $('<tr class="tbody"></tr>');
                var titleTd = $("<td></td>");
                titleTd.addClass("filmTitle");
                titleTd.text(film.title);
                filmTr.append(titleTd);
                var authorTd = $("<td></td>");
                authorTd.addClass("ebookAuthor");
                authorTd.text(film.author);
                filmTr.append(authorTd);
                var yearTd = $("<td></td>");
                yearTd.addClass("ebookYear");
                yearTd.text(film.publicationYear);
                filmTr.append(yearTd);
                var keywordsTd = $("<td></td>");
                keywordsTd.addClass("ebookKeywords");
                keywordsTd.text(film.keywords);
                filmTr.append(keywordsTd);
                var downloadTd = $("<td></td>");
                var downloadLink = $("<a href=Download?file="+film.filename+">Download</a>")
                downloadTd.addClass("ebookDownload");
                downloadTd.append(downloadLink);
                filmTr.append(downloadTd);
                $("#movieTable > tbody").append(filmTr);
            }
        },
        error: function(request, options, error) {
            alert(error);
        }
    });
}


function loadAllGenres(){
    $("#genreTable > tbody").empty();
	$.ajax({
        method: "GET",
		url: "GenreController",
		dataType: "json",
        cache: false,
		success: function(response) {
			var genres = response;
            var tr;
            for (var i = 0; i < genres.length; i++) {
                var moderators = "";
                var editID = "edit-" + genres[i].id.toString();
                var deleteID = "delete-" + genres[i].id.toString();
                var tabAction = $('<td><div style="margin-right:5px; margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editGenre" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;">|</span><a href="#" class="fa fa-times fa-2x deleteGenre" id='+deleteID+' aria-hidden="true"/></div></td>');
                tr = $('<tr/>');
                tr.append("<td>" + genres[i].id + "</td>");
                tr.append("<td>" + genres[i].name + "</td>");
                tr.append(tabAction);
                $("#genreTable > tbody").append(tr);
            }
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}


function loadAllUsers() {
    $("#userTable > tbody").empty();
	$.ajax({
        method: "GET",
		url: "UserController",
		dataType: "json",
        cache: false,
		success: function(response) {
			var users = response;
            var tr;
            for (var i = 0; i < users.length; i++) {
                var editID = "edit-" + users[i].id.toString();
                var deleteID = "delete-" + users[i].id.toString();
                var tabAction = $('<td><div style="margin-right:5px; margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editUser" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;">|</span><a href="#" class="fa fa-times fa-2x deleteUser" id='+deleteID+' aria-hidden="true"/></div></td>');
                tr = $('<tr/>');
                tr.append("<td>" + users[i].id + "</td>");
                tr.append("<td>" + users[i].username + "</td>");
                tr.append("<td>" + users[i].password + "</td>");
                tr.append("<td>" + users[i].firstName + "</td>");
                tr.append("<td>" + users[i].lastName + "</td>");
                tr.append("<td>" + users[i].role + "</td>");
                tr.append(tabAction);
                $("#userTable > tbody").append(tr);
            }
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}


// FILTERED RESULTS

function loadFilteredMoviesDays7(){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "MovieController",
		dataType: "json",
        data: {purpose: "filterByDays",
               days: 7},
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
				film = response[i];
                editID = "edit-" + film.id.toString();
                deleteID = "delete-" + film.id.toString();
                var actionTab = $('<div style="margin-left:5px;margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMovie" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;">|</span><a href="#" class="fa fa-times fa-2x deleteMovie" id='+deleteID+' aria-hidden="true"/></div>');
				// Za svaki film kreiramo po jedan <tr> element u tebeli
				var filmTr = $('<tr class="tbody"></tr>');
				// <td>  sa klasom 'orderNumber' za redni broj filma
				var idTd = $("<td></td>");
				idTd.addClass("orderNumber");
				idTd.text(film.id);
				filmTr.append(idTd);
				// <td> sa klasom 'categoryName' za ime kategorije
				var titleTd = $("<td></td>");
				titleTd.addClass("filmTitle");
				titleTd.text(film.title);
				filmTr.append(titleTd);
				var descriptionTd = $("<td></td>");
				descriptionTd.addClass("filmDescription");
				descriptionTd.text(film.description);
				filmTr.append(descriptionTd);
				var directorTd = $("<td></td>");
				directorTd.addClass("filmDirector");
				directorTd.text(film.director);
				filmTr.append(directorTd);
				var producerTd = $("<td></td>");
				producerTd.addClass("filmProducer");
				producerTd.text(film.producer);
				filmTr.append(producerTd);
				var actorsTd = $("<td></td>");
				actorsTd.addClass("filmActors");
				actorsTd.text(film.actors);
				filmTr.append(actorsTd);
				var yearTd = $("<td></td>");
				yearTd.addClass("filmYear");
				yearTd.text(film.year);
				filmTr.append(yearTd);
                var trailerTd = $("<td></td>");
                var trailerLink = $("<a href=" + film.trailer + " target='_blank'>Watch me</a>")
				trailerTd.addClass("filmTrailer");
				trailerTd.append(trailerLink);
				filmTr.append(trailerTd);
				var avgScoreTd = $("<td></td>");
				avgScoreTd.addClass("filmAvgScore");
				avgScoreTd.text(JSON.stringify(film.avgScore));
				filmTr.append(avgScoreTd);
                var actionTd = $("<td></td>")
                actionTd.addClass("actionTab");
                actionTd.append(actionTab);
                filmTr.append(actionTd);
				$("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}


function loadFilteredMoviesDays30(){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "MovieController",
		dataType: "json",
        data: {purpose: "filterByDays",
               days: 30},
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
				film = response[i];
                editID = "edit-" + film.id.toString();
                deleteID = "delete-" + film.id.toString();
                var actionTab = $('<div style="margin-left:5px;margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMovie" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;">|</span><a href="#" class="fa fa-times fa-2x deleteMovie" id='+deleteID+' aria-hidden="true"/></div>');
				// Za svaki film kreiramo po jedan <tr> element u tebeli
				var filmTr = $('<tr class="tbody"></tr>');
				// <td>  sa klasom 'orderNumber' za redni broj filma
				var idTd = $("<td></td>");
				idTd.addClass("orderNumber");
				idTd.text(film.id);
				filmTr.append(idTd);
				// <td> sa klasom 'categoryName' za ime kategorije
				var titleTd = $("<td></td>");
				titleTd.addClass("filmTitle");
				titleTd.text(film.title);
				filmTr.append(titleTd);
				var descriptionTd = $("<td></td>");
				descriptionTd.addClass("filmDescription");
				descriptionTd.text(film.description);
				filmTr.append(descriptionTd);
				var directorTd = $("<td></td>");
				directorTd.addClass("filmDirector");
				directorTd.text(film.director);
				filmTr.append(directorTd);
				var producerTd = $("<td></td>");
				producerTd.addClass("filmProducer");
				producerTd.text(film.producer);
				filmTr.append(producerTd);
				var actorsTd = $("<td></td>");
				actorsTd.addClass("filmActors");
				actorsTd.text(film.actors);
				filmTr.append(actorsTd);
				var yearTd = $("<td></td>");
				yearTd.addClass("filmYear");
				yearTd.text(film.year);
				filmTr.append(yearTd);
                var trailerTd = $("<td></td>");
                var trailerLink = $("<a href=" + film.trailer + " target='_blank'>Watch me</a>")
				trailerTd.addClass("filmTrailer");
				trailerTd.append(trailerLink);
				filmTr.append(trailerTd);
				var avgScoreTd = $("<td></td>");
				avgScoreTd.addClass("filmAvgScore");
				avgScoreTd.text(JSON.stringify(film.avgScore));
				filmTr.append(avgScoreTd);
                var actionTd = $("<td></td>")
                actionTd.addClass("actionTab");
                actionTd.append(actionTab);
                filmTr.append(actionTd);
				$("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}

function loadFilteredMoviesDaysModer(daysVal){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "MovieController",
		dataType: "json",
        data: {purpose: "filterByDaysModer",
               days: daysVal},
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
				film = response[i];
                editID = "edit-" + film.id.toString();
                deleteID = "delete-" + film.id.toString();
                var actionTab = $('<div style="margin-left:5px;margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMovie" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;">|</span><a href="#" class="fa fa-times fa-2x deleteMovie" id='+deleteID+' aria-hidden="true"/></div>');
				// Za svaki film kreiramo po jedan <tr> element u tebeli
				var filmTr = $('<tr class="tbody"></tr>');
				// <td>  sa klasom 'orderNumber' za redni broj filma
				var idTd = $("<td></td>");
				idTd.addClass("orderNumber");
				idTd.text(film.id);
				filmTr.append(idTd);
				// <td> sa klasom 'categoryName' za ime kategorije
				var titleTd = $("<td></td>");
				titleTd.addClass("filmTitle");
				titleTd.text(film.title);
				filmTr.append(titleTd);
				var descriptionTd = $("<td></td>");
				descriptionTd.addClass("filmDescription");
				descriptionTd.text(film.description);
				filmTr.append(descriptionTd);
				var directorTd = $("<td></td>");
				directorTd.addClass("filmDirector");
				directorTd.text(film.director);
				filmTr.append(directorTd);
				var producerTd = $("<td></td>");
				producerTd.addClass("filmProducer");
				producerTd.text(film.producer);
				filmTr.append(producerTd);
				var actorsTd = $("<td></td>");
				actorsTd.addClass("filmActors");
				actorsTd.text(film.actors);
				filmTr.append(actorsTd);
				var yearTd = $("<td></td>");
				yearTd.addClass("filmYear");
				yearTd.text(film.year);
				filmTr.append(yearTd);
                var trailerTd = $("<td></td>");
                var trailerLink = $("<a href=" + film.trailer + " target='_blank'>Watch me</a>")
				trailerTd.addClass("filmTrailer");
				trailerTd.append(trailerLink);
				filmTr.append(trailerTd);
				var avgScoreTd = $("<td></td>");
				avgScoreTd.addClass("filmAvgScore");
				avgScoreTd.text(film.avgScore);
				filmTr.append(avgScoreTd);
                var actionTd = $("<td></td>")
                actionTd.addClass("actionTab");
                actionTd.append(actionTab);
                filmTr.append(actionTd);
				$("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}

function loadFilteredMoviesDaysGuest(daysVal){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "MovieController",
		dataType: "json",
        data: {purpose: "filterByDays",
               days: daysVal},
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
				film = response[i];
                var rateFormId = "rateForm-" + film.id.toString();
				var filmTr = $('<tr class="tbody"></tr>');
				var titleTd = $("<td></td>");
				titleTd.addClass("filmTitle");
				titleTd.text(film.title);
				filmTr.append(titleTd);
				var descriptionTd = $("<td></td>");
				descriptionTd.addClass("filmDescription");
				descriptionTd.text(film.description);
				filmTr.append(descriptionTd);
				var directorTd = $("<td></td>");
				directorTd.addClass("filmDirector");
				directorTd.text(film.director);
				filmTr.append(directorTd);
				var producerTd = $("<td></td>");
				producerTd.addClass("filmProducer");
				producerTd.text(film.producer);
				filmTr.append(producerTd);
				var actorsTd = $("<td></td>");
				actorsTd.addClass("filmActors");
				actorsTd.text(film.actors);
				filmTr.append(actorsTd);
				var yearTd = $("<td></td>");
				yearTd.addClass("filmYear");
				yearTd.text(film.year);
				filmTr.append(yearTd);
                var trailerTd = $("<td></td>");
                var trailerLink = $("<a href=" + film.trailer + " target='_blank'>Watch me</a>")
				trailerTd.addClass("filmTrailer");
				trailerTd.append(trailerLink);
				filmTr.append(trailerTd);
				var avgScoreTd = $("<td></td>");
				avgScoreTd.addClass("filmAvgScore");
				avgScoreTd.text(film.avgScore);
				filmTr.append(avgScoreTd);
				var rateTd = $('<td><form id='+rateFormId+' class="ratingForm"><select name="rate"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option>><option value="5">5</option>><option value="6">6</option>><option value="7">7</option>><option value="8">8</option>><option value="9">9</option>><option value="10">10</option></select> <input type="submit" name="submitButton" value="RATE"/></form></td>');
				filmTr.append(rateTd);
				$("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}


function loadFilteredMoviesByGenre(gID){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "EbookController",
		dataType: "json",
        data: {purpose: "filterByGenre",
               genreID: gID},
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
                film = response[i];
                editID = "edit-" + film.id.toString();
                deleteID = "delete-" + film.id.toString();
                var actionTab = $('<div style="margin-left:5px;margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMovie" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;">|</span><a href="#" class="fa fa-times fa-2x deleteMovie" id='+deleteID+' aria-hidden="true"/></div>');
                var filmTr = $('<tr class="tbody"></tr>');

                var idTd = $("<td></td>");
                idTd.addClass("orderNumber");
                idTd.text(film.id);
                filmTr.append(idTd);
                var titleTd = $("<td></td>");
                titleTd.addClass("filmTitle");
                titleTd.text(film.title);
                filmTr.append(titleTd);
                var authorTd = $("<td></td>");
                authorTd.addClass("ebookAuthor");
                authorTd.text(film.author);
                filmTr.append(authorTd);
                var yearTd = $("<td></td>");
                yearTd.addClass("ebookYear");
                yearTd.text(film.publicationYear);
                filmTr.append(yearTd);
                var keywordsTd = $("<td></td>");
                keywordsTd.addClass("ebookKeywords");
                keywordsTd.text(film.keywords);
                filmTr.append(keywordsTd);
                var downloadTd = $("<td></td>");
                var downloadLink = $("<a href=Download?file="+film.filename+" target='_blank'>Download</a>");
                downloadTd.addClass("ebookDownload");
                downloadTd.append(downloadLink);
                filmTr.append(downloadTd);
                var actionTd = $("<td></td>");
                actionTd.addClass("actionTab");
                actionTd.append(actionTab);
                filmTr.append(actionTd);
                $("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}

function loadFilteredMoviesByGenreGuest(gID){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "EbookController",
		dataType: "json",
        data: {purpose: "filterByGenre",
               genreID: gID},
        cache: false,
		success: function(response) {
            if (response.length == 0){
                showMeMessage("No results found!", "yellow");
                return;
            }
			for(var i=0; i<response.length; i++) {
                film = response[i];
                var filmTr = $('<tr class="tbody"></tr>');
                var titleTd = $("<td></td>");
                titleTd.addClass("filmTitle");
                titleTd.text(film.title);
                filmTr.append(titleTd);
                var authorTd = $("<td></td>");
                authorTd.addClass("ebookAuthor");
                authorTd.text(film.author);
                filmTr.append(authorTd);
                var yearTd = $("<td></td>");
                yearTd.addClass("ebookYear");
                yearTd.text(film.publicationYear);
                filmTr.append(yearTd);
                var keywordsTd = $("<td></td>");
                keywordsTd.addClass("ebookKeywords");
                keywordsTd.text(film.keywords);
                filmTr.append(keywordsTd);
                var downloadTd = $("<td></td>");
                var downloadLink = $("<a href=Download?file="+film.filename+" target='_blank'>Download</a>");

                downloadTd.addClass("ebookDownload");
                downloadTd.append(downloadLink);
                filmTr.append(downloadTd);
                $("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}

function loadFilteredMoviesAsc(){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "MovieController",
		dataType: "json",
        data: {purpose: "filterAsc"},
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
				film = response[i];
                editID = "edit-" + film.id.toString();
                deleteID = "delete-" + film.id.toString();
                var actionTab = $('<div style="margin-left:5px;margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMovie" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;">|</span><a href="#" class="fa fa-times fa-2x deleteMovie" id='+deleteID+' aria-hidden="true"/></div>');
				// Za svaki film kreiramo po jedan <tr> element u tebeli
				var filmTr = $('<tr class="tbody"></tr>');
				// <td>  sa klasom 'orderNumber' za redni broj filma
				var idTd = $("<td></td>");
				idTd.addClass("orderNumber");
				idTd.text(film.id);
				filmTr.append(idTd);
				// <td> sa klasom 'categoryName' za ime kategorije
				var titleTd = $("<td></td>");
				titleTd.addClass("filmTitle");
				titleTd.text(film.title);
				filmTr.append(titleTd);
				var descriptionTd = $("<td></td>");
				descriptionTd.addClass("filmDescription");
				descriptionTd.text(film.description);
				filmTr.append(descriptionTd);
				var directorTd = $("<td></td>");
				directorTd.addClass("filmDirector");
				directorTd.text(film.director);
				filmTr.append(directorTd);
				var producerTd = $("<td></td>");
				producerTd.addClass("filmProducer");
				producerTd.text(film.producer);
				filmTr.append(producerTd);
				var actorsTd = $("<td></td>");
				actorsTd.addClass("filmActors");
				actorsTd.text(film.actors);
				filmTr.append(actorsTd);
				var yearTd = $("<td></td>");
				yearTd.addClass("filmYear");
				yearTd.text(film.year);
				filmTr.append(yearTd);
                var trailerTd = $("<td></td>");
                var trailerLink = $("<a href=" + film.trailer + " target='_blank'>Watch me</a>")
				trailerTd.addClass("filmTrailer");
				trailerTd.append(trailerLink);
				filmTr.append(trailerTd);
				var avgScoreTd = $("<td></td>");
				avgScoreTd.addClass("filmAvgScore");
				avgScoreTd.text(film.avgScore);
				filmTr.append(avgScoreTd);
                var actionTd = $("<td></td>")
                actionTd.addClass("actionTab");
                actionTd.append(actionTab);
                filmTr.append(actionTd);
				$("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}

function loadFilteredMoviesAscModer(){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "MovieController",
		dataType: "json",
        data: {purpose: "filterAscModer"},
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
				film = response[i];
                editID = "edit-" + film.id.toString();
                deleteID = "delete-" + film.id.toString();
                var actionTab = $('<div style="margin-left:5px;margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMovie" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;">|</span><a href="#" class="fa fa-times fa-2x deleteMovie" id='+deleteID+' aria-hidden="true"/></div>');
				// Za svaki film kreiramo po jedan <tr> element u tebeli
				var filmTr = $('<tr class="tbody"></tr>');
				// <td>  sa klasom 'orderNumber' za redni broj filma
				var idTd = $("<td></td>");
				idTd.addClass("orderNumber");
				idTd.text(film.id);
				filmTr.append(idTd);
				// <td> sa klasom 'categoryName' za ime kategorije
				var titleTd = $("<td></td>");
				titleTd.addClass("filmTitle");
				titleTd.text(film.title);
				filmTr.append(titleTd);
				var descriptionTd = $("<td></td>");
				descriptionTd.addClass("filmDescription");
				descriptionTd.text(film.description);
				filmTr.append(descriptionTd);
				var directorTd = $("<td></td>");
				directorTd.addClass("filmDirector");
				directorTd.text(film.director);
				filmTr.append(directorTd);
				var producerTd = $("<td></td>");
				producerTd.addClass("filmProducer");
				producerTd.text(film.producer);
				filmTr.append(producerTd);
				var actorsTd = $("<td></td>");
				actorsTd.addClass("filmActors");
				actorsTd.text(film.actors);
				filmTr.append(actorsTd);
				var yearTd = $("<td></td>");
				yearTd.addClass("filmYear");
				yearTd.text(film.year);
				filmTr.append(yearTd);
                var trailerTd = $("<td></td>");
                var trailerLink = $("<a href=" + film.trailer + " target='_blank'>Watch me</a>")
				trailerTd.addClass("filmTrailer");
				trailerTd.append(trailerLink);
				filmTr.append(trailerTd);
				var avgScoreTd = $("<td></td>");
				avgScoreTd.addClass("filmAvgScore");
				avgScoreTd.text(film.avgScore);
				filmTr.append(avgScoreTd);
                var actionTd = $("<td></td>")
                actionTd.addClass("actionTab");
                actionTd.append(actionTab);
                filmTr.append(actionTd);
				$("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}

function loadFilteredMoviesAscGuest(){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "MovieController",
		dataType: "json",
        data: {purpose: "filterAsc"},
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
				film = response[i];
                var rateFormId = "rateForm-" + film.id.toString();
				var filmTr = $('<tr class="tbody"></tr>');
				var titleTd = $("<td></td>");
				titleTd.addClass("filmTitle");
				titleTd.text(film.title);
				filmTr.append(titleTd);
				var descriptionTd = $("<td></td>");
				descriptionTd.addClass("filmDescription");
				descriptionTd.text(film.description);
				filmTr.append(descriptionTd);
				var directorTd = $("<td></td>");
				directorTd.addClass("filmDirector");
				directorTd.text(film.director);
				filmTr.append(directorTd);
				var producerTd = $("<td></td>");
				producerTd.addClass("filmProducer");
				producerTd.text(film.producer);
				filmTr.append(producerTd);
				var actorsTd = $("<td></td>");
				actorsTd.addClass("filmActors");
				actorsTd.text(film.actors);
				filmTr.append(actorsTd);
				var yearTd = $("<td></td>");
				yearTd.addClass("filmYear");
				yearTd.text(film.year);
				filmTr.append(yearTd);
                var trailerTd = $("<td></td>");
                var trailerLink = $("<a href=" + film.trailer + " target='_blank'>Watch me</a>")
				trailerTd.addClass("filmTrailer");
				trailerTd.append(trailerLink);
				filmTr.append(trailerTd);
				var avgScoreTd = $("<td></td>");
				avgScoreTd.addClass("filmAvgScore");
				avgScoreTd.text(film.avgScore);
				filmTr.append(avgScoreTd);
				var rateTd = $('<td><form id='+rateFormId+' class="ratingForm"><select name="rate"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option>><option value="5">5</option>><option value="6">6</option>><option value="7">7</option>><option value="8">8</option>><option value="9">9</option>><option value="10">10</option></select> <input type="submit" name="submitButton" value="RATE"/></form></td>');
				filmTr.append(rateTd);
				$("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}


function loadFilteredMoviesDesc(){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "MovieController",
		dataType: "json",
        data: {purpose: "filterDesc"},
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
				film = response[i];
                editID = "edit-" + film.id.toString();
                deleteID = "delete-" + film.id.toString();
                var actionTab = $('<div style="margin-left:5px;margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMovie" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;">|</span><a href="#" class="fa fa-times fa-2x deleteMovie" id='+deleteID+' aria-hidden="true"/></div>');
				// Za svaki film kreiramo po jedan <tr> element u tebeli
				var filmTr = $('<tr class="tbody"></tr>');
				// <td>  sa klasom 'orderNumber' za redni broj filma
				var idTd = $("<td></td>");
				idTd.addClass("orderNumber");
				idTd.text(film.id);
				filmTr.append(idTd);
				// <td> sa klasom 'categoryName' za ime kategorije
				var titleTd = $("<td></td>");
				titleTd.addClass("filmTitle");
				titleTd.text(film.title);
				filmTr.append(titleTd);
				var descriptionTd = $("<td></td>");
				descriptionTd.addClass("filmDescription");
				descriptionTd.text(film.description);
				filmTr.append(descriptionTd);
				var directorTd = $("<td></td>");
				directorTd.addClass("filmDirector");
				directorTd.text(film.director);
				filmTr.append(directorTd);
				var producerTd = $("<td></td>");
				producerTd.addClass("filmProducer");
				producerTd.text(film.producer);
				filmTr.append(producerTd);
				var actorsTd = $("<td></td>");
				actorsTd.addClass("filmActors");
				actorsTd.text(film.actors);
				filmTr.append(actorsTd);
				var yearTd = $("<td></td>");
				yearTd.addClass("filmYear");
				yearTd.text(film.year);
				filmTr.append(yearTd);
                var trailerTd = $("<td></td>");
                var trailerLink = $("<a href=" + film.trailer + " target='_blank'>Watch me</a>")
				trailerTd.addClass("filmTrailer");
				trailerTd.append(trailerLink);
				filmTr.append(trailerTd);
				var avgScoreTd = $("<td></td>");
				avgScoreTd.addClass("filmAvgScore");
				avgScoreTd.text(film.avgScore);
				filmTr.append(avgScoreTd);
                var actionTd = $("<td></td>")
                actionTd.addClass("actionTab");
                actionTd.append(actionTab);
                filmTr.append(actionTd);
				$("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}


function loadFilteredMoviesDescModer(){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "MovieController",
		dataType: "json",
        data: {purpose: "filterDescModer"},
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
				film = response[i];
                editID = "edit-" + film.id.toString();
                deleteID = "delete-" + film.id.toString();
                var actionTab = $('<div style="margin-left:5px;margin-top:8px;"><a href="#" class="fa fa-pencil fa-2x editMovie" id='+editID+' aria-hidden="true"></a><span style="font-size:2.2em;">|</span><a href="#" class="fa fa-times fa-2x deleteMovie" id='+deleteID+' aria-hidden="true"/></div>');
				// Za svaki film kreiramo po jedan <tr> element u tebeli
				var filmTr = $('<tr class="tbody"></tr>');
				// <td>  sa klasom 'orderNumber' za redni broj filma
				var idTd = $("<td></td>");
				idTd.addClass("orderNumber");
				idTd.text(film.id);
				filmTr.append(idTd);
				// <td> sa klasom 'categoryName' za ime kategorije
				var titleTd = $("<td></td>");
				titleTd.addClass("filmTitle");
				titleTd.text(film.title);
				filmTr.append(titleTd);
				var descriptionTd = $("<td></td>");
				descriptionTd.addClass("filmDescription");
				descriptionTd.text(film.description);
				filmTr.append(descriptionTd);
				var directorTd = $("<td></td>");
				directorTd.addClass("filmDirector");
				directorTd.text(film.director);
				filmTr.append(directorTd);
				var producerTd = $("<td></td>");
				producerTd.addClass("filmProducer");
				producerTd.text(film.producer);
				filmTr.append(producerTd);
				var actorsTd = $("<td></td>");
				actorsTd.addClass("filmActors");
				actorsTd.text(film.actors);
				filmTr.append(actorsTd);
				var yearTd = $("<td></td>");
				yearTd.addClass("filmYear");
				yearTd.text(film.year);
				filmTr.append(yearTd);
                var trailerTd = $("<td></td>");
                var trailerLink = $("<a href=" + film.trailer + " target='_blank'>Watch me</a>")
				trailerTd.addClass("filmTrailer");
				trailerTd.append(trailerLink);
				filmTr.append(trailerTd);
				var avgScoreTd = $("<td></td>");
				avgScoreTd.addClass("filmAvgScore");
				avgScoreTd.text(film.avgScore);
				filmTr.append(avgScoreTd);
                var actionTd = $("<td></td>")
                actionTd.addClass("actionTab");
                actionTd.append(actionTab);
                filmTr.append(actionTd);
				$("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}

function loadFilteredMoviesDescGuest(){
    $("#movieTable > tbody").empty();
    // Kada se ucita stranica, pokupimo listu filmova sa servera i popunimo tabelu
	$.ajax({
        method: "GET",
		url: "MovieController",
		dataType: "json",
        data: {purpose: "filterDesc"},
        cache: false,
		success: function(response) {
			for(var i=0; i<response.length; i++) {
				film = response[i];
                var rateFormId = "rateForm-" + film.id.toString();
				var filmTr = $('<tr class="tbody"></tr>');
				var titleTd = $("<td></td>");
				titleTd.addClass("filmTitle");
				titleTd.text(film.title);
				filmTr.append(titleTd);
				var descriptionTd = $("<td></td>");
				descriptionTd.addClass("filmDescription");
				descriptionTd.text(film.description);
				filmTr.append(descriptionTd);
				var directorTd = $("<td></td>");
				directorTd.addClass("filmDirector");
				directorTd.text(film.director);
				filmTr.append(directorTd);
				var producerTd = $("<td></td>");
				producerTd.addClass("filmProducer");
				producerTd.text(film.producer);
				filmTr.append(producerTd);
				var actorsTd = $("<td></td>");
				actorsTd.addClass("filmActors");
				actorsTd.text(film.actors);
				filmTr.append(actorsTd);
				var yearTd = $("<td></td>");
				yearTd.addClass("filmYear");
				yearTd.text(film.year);
				filmTr.append(yearTd);
                var trailerTd = $("<td></td>");
                var trailerLink = $("<a href=" + film.trailer + " target='_blank'>Watch me</a>")
				trailerTd.addClass("filmTrailer");
				trailerTd.append(trailerLink);
				filmTr.append(trailerTd);
				var avgScoreTd = $("<td></td>");
				avgScoreTd.addClass("filmAvgScore");
				avgScoreTd.text(film.avgScore);
				filmTr.append(avgScoreTd);
				var rateTd = $('<td><form id='+rateFormId+' class="ratingForm"><select name="rate"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option>><option value="5">5</option>><option value="6">6</option>><option value="7">7</option>><option value="8">8</option>><option value="9">9</option>><option value="10">10</option></select> <input type="submit" name="submitButton" value="RATE"/></form></td>');
				filmTr.append(rateTd);
				$("#movieTable > tbody").append(filmTr);
			}
		},
		error: function(request, options, error) {
			alert(error);
		}
	});
}




//------------------------- 
// INITIAL AJAX CALLS 4 SELECT controllers 

    //4 MOVIES
function loadGenresForSelect(){
    var selectOptGenre = $("#select-genre");
    var selectOptGenreFilter = $("#select-genre-filter");
    var selectOptGenreNewUser = $("#select-new-user-genre");
    
    selectOptGenre.empty();
    selectOptGenreFilter.empty();
    selectOptGenreNewUser.empty();
    $.ajax({
        method: "GET",
        url: "GenreController",
        dataType: "json",
        cache: false,
        async: false,
        success: function(response){
            var genres = response;
                // select all genres
            selectOptGenreFilter.append('<option value=0>All</option>');
            for (var i=0; i<genres.length; i++){
                selectOptGenre.append('<option value=' + genres[i].id.toString() + '>' + genres[i].name + '</option>');
               
                selectOptGenreFilter.append('<option value=' + genres[i].id.toString() + '>' + genres[i].name + '</option>');
                selectOptGenreNewUser.append('<option value=' + genres[i].id.toString() + '>' + genres[i].name + '</option>');
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
    });
}


function loadLanguagesForSelect(){
    var selectOptLang = $("#select-language");
    
    selectOptLang.empty();
    $.ajax({
        method: "GET",
        url: "LanguageController",
        dataType: "json",
        cache: false,
        success: function(response){
            var languages = response;
            for (var i=0; i<languages.length; i++){
                selectOptLang.append('<option value=' + languages[i].id.toString() + '>' + languages[i].name + '</option>');
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
    });
}

    // 4 GENRES

function loadModeratorsForSelect(){
    var selectOptModer = $("#select-moderator");
    
    selectOptModer.empty();
    $.ajax({
        method: "GET",
        url: "UserController",
        data: {purpose: "getModerators"},
        dataType: "json",
        cache: false,
        success: function(response){
            var moderators = response;
            for (var i=0; i<moderators.length; i++){
                selectOptModer.append('<option value=' + moderators[i].id.toString() + '>' + (moderators[i].username + ' ('+moderators[i].firstName + ' ' + moderators[i].lastName+')')  + '</option>');
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            alert(XMLHttpRequest.status);
	            alert(errorThrown);
	        }
    });
}

//-------------------------   
    // EDITING ENTITIES
    

// !!!!configure this!!!!
function fillFormMovie(movie){
    var editingForm = $("#add-new-movie-form");
    editingForm.find("#ebookTitle").val(movie.title);
    editingForm.find("#ebookAuthor").val(movie.author);
    editingForm.find("#ebookYear").val(movie.publicationYear);
    editingForm.find("#ebookKeywords").val(movie.keywords);

    editingForm.find("#select-genre").val(movie.category.id);
    editingForm.find("#select-language").val(movie.language.id);
}

    //GENRE 
function fillFormGenre(genre){
    var editingForm = $("#add-new-genre-form");
    editingForm.find("#genreName").val(genre.name);
}

    //USERS 
function fillFormUser(user){
    var editingForm = $("#add-new-user-form");
    editingForm.find("#usrUsername").val(user.username);
    editingForm.find("#usrPassword").val(user.password);
    editingForm.find("#usrFirstname").val(user.firstName);
    editingForm.find("#usrLastname").val(user.lastName);
    
    editingForm.find("#select-role option").each(function() {
        this.selected = (this.text == user.role);
    });
    
    if (user.role == "MODERATOR"){
        $("#madjija").show("fast");
        $("#add-new-user-window").animate({height: "380px"});
    }
    
    if (user.category !== null){
        // then he's moderator in work
        editingForm.find("#select-new-user-genre").val(user.category.id);
    }
        
}

//------------------------- 
// LOAD ENTITIES BY IDs

function loadMovieById(id){
    var movie = "";
    $.ajax({
        method: "GET",
        url: "EbookController",
        data: {purpose : "getById",
               movieId : id},
        dataType: "json",
        async:false,
        success: function(response){
            movie = response;
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert(XMLHttpRequest.status);
        alert(errorThrown);
        }
    });
    return movie;
}

function loadGenreById(id){
    var genre = "";
    $.ajax({
        method: "GET",
        url: "GenreController",
        data: {purpose : "getById",
               genreId : id},
        dataType: "json",
        async:false,
        success: function(response){
            genre = response;
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert(XMLHttpRequest.status);
        alert(errorThrown);
        }
    });
    return genre;
}

function loadUserById(id){
    var user = "";
    $.ajax({
        method: "GET",
        url: "UserController",
        data: {purpose : "getById",
               userId : id},
        dataType: "json",
        async:false,
        success: function(response){
            user = response;
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert(XMLHttpRequest.status);
        alert(errorThrown);
        }
    });
    return user;
}


function checkSessionUserRole(){
    var role = "";
    $.ajax({
        method: "GET",
        url: "AccountController",
        data: {purpose : "checkSessionUserRole"},
        dataType: "text",
        async:false,
        success: function(response){
            role = response;
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert(XMLHttpRequest.status);
        alert(errorThrown);
        }
    });
    return role;
}



// NEW ERAAAAAAAAAAAAAAAAAAAAA
//===================================================
function fillEbookForm(){
    var editingForm = $("#add-new-movie-form");
    var fullPath = document.getElementById('ebookFile').value;

    // check if pdf
    if (!fullPath.endsWith("pdf")){
    	showMeMessage("Wrong file extension!", "red");
        editingForm.find(':input[type=submit]').prop('disabled', true);
    	return;
	}else{
        editingForm.find(':input[type=submit]').prop('disabled', false);
	}

    //OVDE POSALJI PDF NA SERVER - FETCHUJ METADATA - VRATI U JSON FORMATU & popuni formu
    var formData = new FormData($("#add-new-movie-form")[0]);
    $.ajax({
        url: 'UploadController',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',
        success: function(ebook) {
            editingForm.find("#ebookTitle").val(ebook.title);
            editingForm.find("#ebookAuthor").val(ebook.author);
            editingForm.find("#ebookYear").val(ebook.publicationYear);
            editingForm.find("#ebookKeywords").val(ebook.keywords);
        },
        error: function(msg) {
            alert("Couldn't upload file");
        }
    });
}

// Custom functions

function splitMyId(id){
        return id.split('-')[1];
    }

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


$(document).ready(ajaxFun);
$(function(){
    //$.ajaxSetup({ cache: false });
    $("#nav").load("nav.jsp");
    
    var userRole = checkSessionUserRole();
    
    if (userRole == "ADMIN"){
        $("#tableContainer").load("table-ebook.jsp", function() {
            loadAllMovies();
            $("#hiddenCreationWindow").load("add-edit-movie.html", function() {
                loadGenresForSelect();
                loadLanguagesForSelect();
            });
        });
    }else if (userRole == "SUBSCRIBER"){
        $("#tableContainer").load("table-ebook.jsp", function() {
                // 4 moderators
            loadAllMoviesForModerator();
            $("#hiddenCreationWindow").load("add-edit-movie.html", function() {
                loadGenresForSelect();
                loadLanguagesForSelect();
            });
        });
    }else if (userRole == "GUEST" || userRole == "none"){
        $("#tableContainer").load("table-ebook.jsp", function() {
            loadAllMoviesForGuest();
            loadGenresForSelect();
        });
    }
    
    
    
    $("#footer").load("footer.html");    
    
    
});