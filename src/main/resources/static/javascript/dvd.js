var dvdLijst;
var actorLijst;
var actorSelected;
var dvdSelected
var genreSelected;
var genreLijst;
window.onload=function(){
	refreshData();
}

function refreshData() {
    getDataDVD('api/dvd');
    getDataActor('api/actor', "dvd_actors"); 
    getDataActor('api/actor', "actors");
    getDataGenre('api/genre',"dvd_genres");
    getDataGenre('api/genre', "genres");
}

function addDVD(){
    var title = document.getElementById("title").value;
    var year = document.getElementById("year").value;
    var origin=document.getElementById("origin").value;
    var bonus=document.getElementById("bonus").value;
    var remarks=document.getElementById("remarks").value;
    var dvd = '{"title":"'+title+'","year":'+year+',"origin":"'+origin+'","bonus":"'+bonus+'","remarks":"'+remarks+'"}'; 
    postData('api/dvd', dvd, "POST");
}

function addActortoDVD(){
    var id = document.getElementById("id").value;
    var id_actor = document.getElementById("dvd_actors").value;
    postData('api/dvd/'+id+'/actor/'+id_actor, "", "PUT");
}

function addGenretoDVD(){
    var id = document.getElementById("id").value;
    var id_genre = document.getElementById("dvd_genres").value;
    postData('api/dvd/'+id+'/genre/'+id_genre,"", "PUT");
}

function putDataDVD(){
    console.log("PUT");
    var id = document.getElementById("id").value;
    var title = document.getElementById("title").value;
    var year = document.getElementById("year").value;
    var origin=document.getElementById("origin").value;
    var bonus=document.getElementById("bonus").value;
    var remarks=document.getElementById("remarks").value;
    var dvd = '{"id":'+id+',"title":"'+title+'","year":'+year+',"origin":"'+origin+'","bonus":"'+bonus+'","remarks":"'+remarks+'"}'; 
    postData('api/dvd', dvd, "PUT");
}

function addActor(){
    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var actor = '{"firstName":"'+firstname+'","lastName":"'+lastname+'"}'; 
    postData('api/actor', actor, "POST");
} 

function putDataActor(){
    console.log("PUT");
    var id_actor = document.getElementById("id_actor").value;
    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var actor = '{"id":'+id_actor+',"firstName":"'+firstname+'","lastName":"'+lastname+'"}'; 
    postData('api/actor', actor, "PUT");
}

function addGenre(){
    var genrename = document.getElementById("genrename").value;
    var genre = '{"genreName":"'+genrename+'"}'; 
    postData('api/genre', genre, "POST");
}

function putDataGenre(){
    console.log("PUT");
    var id_genre = document.getElementById("id_genre").value;
    var genrename = document.getElementById("genrename").value;
    var genre = '{"id":'+id_genre+',"genreName":"'+genrename+'"}'; 
    postData('api/genre', genre, "PUT");
}
function postData(api, data, crud){
    console.log(data);
    console.log(crud);
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 202) {
            console.log(this.responseText);
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

function deleteDVD(){
    var id = document.getElementById("id").value;
    var dvd = '{"id":'+id+'}'; 
    deleteData('api/dvd/'+id, dvd, "DELETE");
}

function deleteActor(){
    var id_actor = document.getElementById("id_actor").value;
    var actor = '{"id":'+id_actor+'}'; 
    deleteData('api/actor/'+id_actor, actor, "DELETE");
}
function deleteGenre(){
    var id_genre = document.getElementById("id_genre").value;
    var genre = '{"id":'+id_genre+'}'; 
    deleteData('api/genre/'+id_genre, genre, "DELETE");
}

function deleteData(api, data, crud){
    console.log(data);
    console.log(crud);
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

function getDataDVD(api){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            dvdLijst = JSON.parse(this.responseText);
            console.log(dvdLijst);
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
                // document.getElementById("demo").innerHTML += dvdLijst[i].title + " " + dvdLijst[i].year + "<br>";
            }
        }
    };
    xhttp.open("GET", "http://localhost:8082/"+api);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}


function getDataActor(api, varid) { 
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var actorLijst = JSON.parse(this.responseText);
            console.log(actorLijst);
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
                //document.getElementById("demo").innerHTML += actorLijst[i].firstName + " " + actorLijst[i].lastName + "<br>";
            }
            var subdvda=document.getElementById("subDVDsA");
            subdvda.innerHTML="";
        }
    };
    xhttp.open("GET", "http://localhost:8082/"+api);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

function getDataGenre(api, varid) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var genreLijst = JSON.parse(this.responseText);
            console.log(genreLijst);
            var selGenres = document.getElementById(varid);
            for (var i=0 ; i< genreLijst.length ; i++) {
                var opt = document.createElement("option");
                opt.value = genreLijst[i].id;
                opt.textContent = genreLijst[i].genreName ;
                selGenres.appendChild(opt);
                //document.getElementById("demo").innerHTML += genreLijst[i].genreName"<br>";
            }
        }
    };
    xhttp.open("GET", "http://localhost:8082/"+api);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}


function selectDVD(event) {
    var id=event.target.value;
    getDVDByID(id);

}       

function selectActor(event) {
    var id=event.target.value;
    actor = getActorByID(id);
    console.log(event.target.value);
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

function getActorByID(id){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           var actor = JSON.parse(this.responseText);
            console.log(actor);
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

function selectGenre(event) {
var id=event.target.value;
    genre = getGenreByID(id);
    console.log(event.target.value);
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

function getDVDByID(id){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           var dvd = JSON.parse(this.responseText);
            console.log(dvd);
            this.dvdSelected=dvd;
            document.getElementById("id").value=dvd.id;
            document.getElementById("title").value=dvd.title;
            document.getElementById("year").value=dvd.year;
            document.getElementById("origin").value=dvd.origin;
            document.getElementById("bonus").value=dvd.bonus;
            document.getElementById("remarks").value=dvd.remarks;
            getExternalData(dvd);
        }
    };
    xhttp.open("GET", "http://localhost:8082/api/dvd/"+id);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

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