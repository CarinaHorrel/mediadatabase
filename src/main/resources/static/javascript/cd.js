var cdLijst;
var artistLijst;
var artistSelected;
var cdSelected
var genreSelected;
var genreLijst;
window.onload=function(){
	refreshData();
}

function refreshData() {
    getDatCD('api/cd');
    getDataArtist('api/artist', "cd_artists"); 
    getDataArtist('api/artist', "artists");
    getDataGenre('api/genre',"cd_genres");
    getDataGenre('api/genre', "genres");
}

function addCD(){
    var title = document.getElementById("title").value;
    var year = document.getElementById("year").value;
    var origin=document.getElementById("origin").value;
    var remarks=document.getElementById("remarks").value;
    var cd = '{"title":"'+title+'","year":'+year+',"origin":"'+origin+'","remarks":"'+remarks+'"}'; 
    postData('api/cd', cd, "POST");
}

function adArtisttoCD(){
    var id = document.getElementById("id").value;
    var id_artist = document.getElementById("cd_artists").value;
    postData('api/cd/'+id+'/artist/'+id_artist, "", "PUT");
}

function addGenretoCD(){
    var id = document.getElementById("id").value;
    var id_genre = document.getElementById("cd_genres").value;
    postData('api/cd/'+id+'/genre/'+id_genre,"", "PUT");
}

function putDataCD(){
    console.log("PUT");
    var id = document.getElementById("id").value;
    var title = document.getElementById("title").value;
    var year = document.getElementById("year").value;
    var origin=document.getElementById("origin").value;
    var remarks=document.getElementById("remarks").value;
    var cd = '{"id":'+id+',"title":"'+title+'","year":'+year+',"origin":"'+origin+'","remarks":"'+remarks+'"}'; 
    postData('api/cd', cd, "PUT");
}

function addArtist(){
    var artistname = document.getElementById("artistname").value;
    var artist = '{"artistName":"'+artistname+'"}'; 
    postData('api/artist', artist, "POST");
} 

function putDataArtist(){
    console.log("PUT");
    var id_artist = document.getElementById("id_artist").value;
    var artistname = document.getElementById("artistname").value;
    var artist = '{"id":'+id_artist+',"artistName":"'+artistname+'"}'; 
    postData('api/artist', artist, "PUT");
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
            if (api=='api/cd') {
                document.getElementById("id").value=this.responseText;
            }
        }
    };
    xhttp.open(crud, "http://localhost:8082/"+api, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(data);
}

function deleteCD(){
    var id = document.getElementById("id").value;
    var cd = '{"id":'+id+'}'; 
    deleteData('api/cd/'+id, cd, "DELETE");
}

function deleteArtist(){
    var id_artist = document.getElementById("id_artist").value;
    var artist = '{"id":'+id_artist+'}'; 
    deleteData('api/artist/'+id_artist, artist, "DELETE");
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

function getDataCD(api){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            cdLijst = JSON.parse(this.responseText);
            console.log(cdLijst);
            var selCDs = document.getElementById("CDs");
            selCDs.innerHTML = "";
            
            var opt = document.createElement("option");
            opt.value = 0;
            opt.textContent = "cds" ;
            selCDs.appendChild(opt);
            for (var i=0 ; i< cdLijst.length ; i++) {
                opt = document.createElement("option");
                opt.value = cdLijst[i].id;
                opt.textContent = cdLijst[i].title ;
                selCDs.appendChild(opt);
                // document.getElementById("demo").innerHTML += cdLijst[i].title + " " + cdLijst[i].year + "<br>";
            }
        }
    };
    xhttp.open("GET", "http://localhost:8082/"+api);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}


function getDataArtist(api, varid) { 
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var artistLijst = JSON.parse(this.responseText);
            console.log(artistLijst);
            var selArtists = document.getElementById(varid);
            selArtists.innerHTML = "";
            var opt = document.createElement("option");
            opt.value = 0;
            opt.textContent = "artists" ;
            selArtists.appendChild(opt);
            for (var i=0 ; i< artistLijst.length ; i++) {
                var opt = document.createElement("option");
                opt.value = artistLijst[i].id;
                opt.textContent = artistLijst[i].artistName ;
                selArtists.appendChild(opt);
                //document.getElementById("demo").innerHTML += artistLijst[i].artistName + " " + artistLijst[i].lastName + "<br>";
            }
            var subcda=document.getElementById("subCDsA");
            subcda.innerHTML="";
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


function selectCD(event) {
    var id=event.target.value;
    getCDByID(id);

}       

function selectArtist(event) {
    var id=event.target.value;
    artist = getArtistByID(id);
    console.log(event.target.value);
    var subcda=document.getElementById("subCDsA");
    subcda.innerHTML="";
    for (var i=0 ; i< cdLijst.length ; i++) {
         for (var j=0 ; j< cdLijst[i].artists.length ; j++) {
               console.log(cdLijst[i].artists[j].artistName);
               if (id==cdLijst[i].artists[j].id){
                    var opt = document.createElement("option");
                    opt.value = cdLijst[i].id;
                    opt.textContent = cdLijst[i].title ;
                    subcda.appendChild(opt);
               }
         }       
    }
}

function getArtistByID(id){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           var artist = JSON.parse(this.responseText);
            console.log(artist);
            this.artistSelected=artist;
            document.getElementById("id_artist").value=artist.id;
            document.getElementById("artistname").value=artist.artistName;
        }
    };
    xhttp.open("GET", "http://localhost:8082/api/artist/"+id);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

function selectGenre(event) {
var id=event.target.value;
    genre = getGenreByID(id);
    console.log(event.target.value);
    var subcdg=document.getElementById("subCDsG");
    subcdg.innerHTML="";
    for (var i=0 ; i< cdLijst.length ; i++) {
         for (var j=0 ; j< cdLijst[i].genres.length ; j++) {
               console.log(cdLijst[i].genres[j].genreName);
               if (id==cdLijst[i].genres[j].id){
                    var opt = document.createElement("option");
                    opt.value = cdLijst[i].id;
                    opt.textContent = cdLijst[i].title ;
                    subcdg.appendChild(opt);
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

function getCDByID(id){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           var cd = JSON.parse(this.responseText);
            console.log(cd);
            this.cdSelected=cd;
            document.getElementById("id").value=cd.id;
            document.getElementById("title").value=cd.title;
            document.getElementById("year").value=cd.year;
            document.getElementById("origin").value=cd.origin;
            document.getElementById("remarks").value=cd.remarks;
            getExternalData(cd);
        }
    };
    xhttp.open("GET", "http://localhost:8082/api/cd/"+id);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}
https://freemusicarchive.org/api/get/{dataset}.{format}?api_key={yourkey}
function getExternalData(cd) {
    var url = "http://www.omdbapi.com/?t=";
    url += cd.title;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var extCD = JSON.parse(this.responseText);
            console.log(extCD);
            document.getElementById("cdPlot").textContent = extCD.Plot;
            document.getElementById("cdPoster").src = extCD.Poster;
            document.getElementById("cdGenre").textContent = extCD.Genre;
        }
    };
    xhttp.open("GET", url);
    xhttp.send();


}