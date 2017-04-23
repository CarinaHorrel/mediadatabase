// initialize variables to be used
var dvdLijst;
var actorLijst;
var actorSelected;
var dvdSelected
var genreSelected;
var genreLijst;

// gain access from right from the start
window.onload=function(){
	refreshData();
}

// gain acces to various apis
function refreshData() {
    getDataDVD('api/dvd');
    getDataActor('api/actor', "dvd_actors"); 
    getDataActor('api/actor', "actors");
    getDataGenre('api/genre',"dvd_genres");
    getDataGenre('api/genre', "genres");
}

// add a new DVD to database
function addDVD(){
    var title = document.getElementById("title").value;
    var year = document.getElementById("year").value;
    var origin=document.getElementById("origin").value;
    var bonus=document.getElementById("bonus").value;
    var remarks=document.getElementById("remarks").value;
    var dvd = '{"title":"'+title+'","year":'+year+',"origin":"'+origin+'","bonus":"'+bonus+'","remarks":"'+remarks+'"}'; 
    postData('api/dvd', dvd, "POST");
}

// attach artist to a existing DVD in database
// artist must exist in database, otherwise add actor to database first
function addActortoDVD(){
    var id = document.getElementById("id").value;
    var id_actor = document.getElementById("dvd_actors").value;
    postData('api/dvd/'+id+'/actor/'+id_actor, "", "PUT");
}

// attach genre to a existing DVD in database
// genre must exist in database, otherwise add actor to database first
function addGenretoDVD(){
    var id = document.getElementById("id").value;
    var id_genre = document.getElementById("dvd_genres").value;
    postData('api/dvd/'+id+'/genre/'+id_genre,"", "PUT");
}

// update information of existing DVD in database
function putDataDVD(){
    var id = document.getElementById("id").value;
    var title = document.getElementById("title").value;
    var year = document.getElementById("year").value;
    var origin=document.getElementById("origin").value;
    var bonus=document.getElementById("bonus").value;
    var remarks=document.getElementById("remarks").value;
    var dvd = '{"id":'+id+',"title":"'+title+'","year":'+year+',"origin":"'+origin+'","bonus":"'+bonus+'","remarks":"'+remarks+'"}'; 
    postData('api/dvd', dvd, "PUT");
}

// add a new actor to database
function addActor(){
    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var actor = '{"firstName":"'+firstname+'","lastName":"'+lastname+'"}'; 
    postData('api/actor', actor, "POST");
} 

// update information of a existing a artist in database
function putDataActor(){
    var id_actor = document.getElementById("id_actor").value;
    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var actor = '{"id":'+id_actor+',"firstName":"'+firstname+'","lastName":"'+lastname+'"}'; 
    postData('api/actor', actor, "PUT");
}

// add a new genre to database
function addGenre(){
    var genrename = document.getElementById("genrename").value;
    var genre = '{"genreName":"'+genrename+'"}'; 
    postData('api/genre', genre, "POST");
}

// update information of a existing a genre in database
function putDataGenre(){
    var id_genre = document.getElementById("id_genre").value;
    var genrename = document.getElementById("genrename").value;
    var genre = '{"id":'+id_genre+',"genreName":"'+genrename+'"}'; 
    postData('api/genre', genre, "PUT");
}

// gain access to local api(using local parameter=api) and add/update 
// data(using local parameter=crud) to database, using endpoints defined 
// in back-end.// api: 'api/cd', 'api/actor, 'api/genre'
// crud: 'PUT' (adjusting/attaching), 'POST' (adding new data)
function postData(api, data, crud){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 202) {
        	refreshData();
            if (api=='api/dvd') {
                document.getElementById("id").value=this.responseText;
            }
        }
    };
    xhttp.open(crud, "http://localhost:8082/"+api, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(data);
}

// delete DVD from database
// attached data to actor and/or genre will also be deleted
// genre and actor will not be deleted 
function deleteDVD(){
    var id = document.getElementById("id").value;
    var dvd = '{"id":'+id+'}'; 
    deleteData('api/dvd/'+id, dvd, "DELETE");
}

// delete actor from database
// attached data to DVD will also be deleted
function deleteActor(){
    var id_actor = document.getElementById("id_actor").value;
    var actor = '{"id":'+id_actor+'}'; 
    deleteData('api/actor/'+id_actor, actor, "DELETE");
}

// delete genre from database
// attached data to DVD will also be deleted
function deleteGenre(){
    var id_genre = document.getElementById("id_genre").value;
    var genre = '{"id":'+id_genre+'}'; 
    deleteData('api/genre/'+id_genre, genre, "DELETE");
}

// gain access to local api(using local parameter=api) and delete 
// data(using local parameter=crud) to database, using endpoints defined 
// in back-end.
// api: 'api/cd', 'api/artist, 'api/genre'
// crud: 'DELETE' (deleting)
function deleteData(api, data, crud){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 202) {
            console.log("DELETE success");
            refreshData();
        }
    };
    xhttp.open(crud, "http://localhost:8082/"+api, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(data);
}

// get DVD data from available database
// retrieved data is needed, to get title 
function getDataDVD(api){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            dvdLijst = JSON.parse(this.responseText);
            var selDVDs = document.getElementById("DVDs");
            selDVDs.innerHTML = "";
            var opt = document.createElement("option");
            opt.value = 0;
            opt.textContent = "dvds" ;
            selDVDs.appendChild(opt);
            for (var i=0 ; i< dvdLijst.length ; i++) {
                opt = document.createElement("option");
                opt.value = dvdLijst[i].id;
                opt.textContent = dvdLijst[i].title ;
                selDVDs.appendChild(opt);
            }
        }
    };
    xhttp.open("GET", "http://localhost:8082/"+api);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

// get actor data from available database
// retrieved data is needed, to get firstName and lasttName
function getDataActor(api, varid) { 
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var actorLijst = JSON.parse(this.responseText);
            var selActors = document.getElementById(varid);
            selActors.innerHTML = "";
            var opt = document.createElement("option");
            opt.value = 0;
            opt.textContent = "actors" ;
            selActors.appendChild(opt);
            for (var i=0 ; i< actorLijst.length ; i++) {
                var opt = document.createElement("option");
                opt.value = actorLijst[i].id;
                opt.textContent = actorLijst[i].firstName + " " + actorLijst[i].lastName;
                selActors.appendChild(opt);
            }
            var subdvda=document.getElementById("subDVDsA");
            subdvda.innerHTML="";
        }
    };
    xhttp.open("GET", "http://localhost:8082/"+api);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

// get genre data from available database
// retrieved data is needed, to get genreName
function getDataGenre(api, varid) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var genreLijst = JSON.parse(this.responseText);
            var selGenres = document.getElementById(varid);
            selGenres.innerHTML = "";
            var opt = document.createElement("option");
            opt.value = 0;
            opt.textContent = "genres" ;
            selGenres.appendChild(opt);
            for (var i=0 ; i< genreLijst.length ; i++) {
                var opt = document.createElement("option");
                opt.value = genreLijst[i].id;
                opt.textContent = genreLijst[i].genreName ;
                selGenres.appendChild(opt);
            }
            var subcdg=document.getElementById("subCDsG");
            subcdg.innerHTML="";
        }
    };
    xhttp.open("GET", "http://localhost:8082/"+api);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

// make selection on dropdown list of available DVDs
function selectDVD(event) {
    var id=event.target.value;
    getDVDByID(id);

}       

// make selection on dropdown list of available actors
function selectActor(event) {
    var id=event.target.value;
    actor = getActorByID(id);
    var subdvda=document.getElementById("subDVDsA");
    subdvda.innerHTML="";
    for (var i=0 ; i< dvdLijst.length ; i++) {
         for (var j=0 ; j< dvdLijst[i].actors.length ; j++) {
               console.log(dvdLijst[i].actors[j].firstName);
               if (id==dvdLijst[i].actors[j].id){
                    var opt = document.createElement("option");
                    opt.value = dvdLijst[i].id;
                    opt.textContent = dvdLijst[i].title ;
                    subdvda.appendChild(opt);
               }
         }       
    }
}

// get data actor by id from endpoint 
function getActorByID(id){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           var actor = JSON.parse(this.responseText);
            this.actorSelected=actor;
            document.getElementById("id_actor").value=actor.id;
            document.getElementById("firstname").value=actor.firstName;
            document.getElementById("lastname").value=actor.lastName;
        }
    };
    xhttp.open("GET", "http://localhost:8082/api/actor/"+id);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

// make selection on dropdown list of available genres
function selectGenre(event) {
var id=event.target.value;
    genre = getGenreByID(id);
    var subdvdg=document.getElementById("subDVDsG");
    subdvdg.innerHTML="";
    for (var i=0 ; i< dvdLijst.length ; i++) {
         for (var j=0 ; j< dvdLijst[i].genres.length ; j++) {
               console.log(dvdLijst[i].genres[j].genreName);
               if (id==dvdLijst[i].genres[j].id){
                    var opt = document.createElement("option");
                    opt.value = dvdLijst[i].id;
                    opt.textContent = dvdLijst[i].title ;
                    subdvdg.appendChild(opt);
               }
         }       
    }
}

// get data genre by id from endpoints
function getGenreByID(id){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           var genre = JSON.parse(this.responseText);
            console.log(genre);
            this.genreSelected=genre;
            document.getElementById("id_genre").value=genre.id;
            document.getElementById("genrename").value=genre.genreName;
        }
    };
    xhttp.open("GET", "http://localhost:8082/api/genre/"+id);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

// get data DVD by id from endpoint
function getDVDByID(id){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           var dvd = JSON.parse(this.responseText);
            this.dvdSelected=dvd;
            document.getElementById("id").value=dvd.id;
            document.getElementById("title").value=dvd.title;
            document.getElementById("year").value=dvd.year;
            document.getElementById("origin").value=dvd.origin;
            document.getElementById("bonus").value=dvd.bonus;
            document.getElementById("remarks").value=dvd.remarks;
            getExternalData(dvd);
            return dvd;
        }
    };
    xhttp.open("GET", "http://localhost:8082/api/dvd/"+id);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

// get data from open movie source api
// for accessing data no apikey is needed
// data available  is used to get additional information from the open source api
function getExternalData(dvd) {
    var url = "http://www.omdbapi.com/?t=";
    url += dvd.title;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var extDVD = JSON.parse(this.responseText);
            console.log(extDVD);
            document.getElementById("dvdPlot").textContent = extDVD.Plot;
            document.getElementById("dvdPoster").src = extDVD.Poster;
            document.getElementById("dvdGenre").textContent = extDVD.Genre;
        }
    };
    xhttp.open("GET", url);
    xhttp.send();
}

    function listDVD(api) {

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            dvdLijst = JSON.parse(this.responseText);
            console.log(dvdLijst);

            
            document.getElementById("id").innerHTML=dvdLijst.id;
            document.getElementById("title").innerHTML=dvdLijst.title;
            document.getElementById("year").innerHTML=dvdLijst.year;
            document.getElementById("origin").innerHTML=dvdLijst.origin;
            document.getElementById("bonus").innerHTML=dvdLijst.bonus;
            document.getElementById("remarks").innerHTML=dvdLijst.remarks;
            for  (var j=0; j<dvdLijst.genres.length; j++){ 
                     //alert();
                     document.getElementById("genres").innerHTML=dvdLijst.genres[j];
            }  
            for  (var j=0; j<dvdLijst.actors.length; j++){ 
                     //alert();
                     document.getElementById("actors").innerHTML=dvdLijst.actors[j];
            } 
           
        }
        }; 
    }