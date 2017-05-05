// initialize variables to be used
var cdLijst;
var artistLijst;
var artistSelected;
var cdSelected
var genreSelected;
var genreLijst;
var uniqueArtistArray = ['Select artist from API'];

// gain access from right from the start
window.onload=function(){
	refreshData();
}

function showResults(){
    var a = document.getElementById("AvailableMusicInDatabase").options[document.getElementById("AvailableMusicInDatabase").value].text;
    var b = document.getElementById("ArtistsFromAPI").value;
    var url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=5e4225aa6762d769875182b25f45f325&artist="+b+"&album="+a+"&format=json";
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var objectReturned = JSON.parse(this.responseText);
    //        console.log(objectReturned.album.tracks.track.length);
            var allenrs = "";
            for(var x = 0; x < objectReturned.album.tracks.track.length ; x++ ){
                allenrs = allenrs + "<br>" + objectReturned.album.tracks.track[x].name + " "+  objectReturned.album.tracks.track[x].duration;
            }
            document.getElementById("jojo2").innerHTML = allenrs;
        }
    };

    xhttp.open("GET", url);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
   
}



// gain acces to various apis
function refreshData() {
    getDataCD('api/cd');
    getDataArtist('api/artist', "cd_artists"); 
    getDataArtist('api/artist', "artists");
    getDataGenre('api/genre',"cd_genres");
    getDataGenre('api/genre', "genres");
}

// add a new CD to database
function addCD(){
    var title = document.getElementById("title").value;
    var year = document.getElementById("year").value;
    var origin=document.getElementById("origin").value;
    var remarks=document.getElementById("remarks").value;
    var cd = '{"title":"'+title+'","year":'+year+',"origin":"'+origin+'","remarks":"'+remarks+'"}'; 
    postData('api/cd', cd, "POST");
}

// attach artist to a existing CD in database
// artist must exist in database, otherwise add artist to database first
function addArtisttoCD(){
    var id = document.getElementById("id").value;
    var id_artist = document.getElementById("cd_artists").value;
    postData('api/cd/'+id+'/artist/'+id_artist, "", "PUT");
}

// attach genre to a existing CD in database
// genre must exist in database, otherwise add artist to database first
function addGenretoCD(){
    var id = document.getElementById("id").value;
    var id_genre = document.getElementById("cd_genres").value;
    postData('api/cd/'+id+'/genre/'+id_genre,"", "PUT");
}

// update information of existing CD in database
function putDataCD(){
    var id = document.getElementById("id").value;
    var title = document.getElementById("title").value;
    var year = document.getElementById("year").value;
    var origin=document.getElementById("origin").value;
    var remarks=document.getElementById("remarks").value;
    var cd = '{"id":'+id+',"title":"'+title+'","year":'+year+',"origin":"'+origin+'","remarks":"'+remarks+'"}'; 
    postData('api/cd', cd, "PUT");
}

// add a new artist to database
function addArtist(){
    var artistname = document.getElementById("artistname").value;
    var artist = '{"artistName":"'+artistname+'"}'; 
    postData('api/artist', artist, "POST");
} 

// update information of a existing a artist in database
function putDataArtist(){
    var id_artist = document.getElementById("id_artist").value;
    var artistname = document.getElementById("artistname").value;
    var artist = '{"id":'+id_artist+',"artistName":"'+artistname+'"}'; 
    postData('api/artist', artist, "PUT");
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
// in back-end.// api: 'api/cd', 'api/artist, 'api/genre'
// crud: 'PUT' (adjusting/attaching), 'POST' (adding new data)
function postData(api, data, crud){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 202) {
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

// delete CD from database
// attached data to artist and/or genre will also be deleted
// genre and artist will not be deleted 
function deleteCD(){
    var id = document.getElementById("id").value;
    var cd = '{"id":'+id+'}'; 
    deleteData('api/cd/'+id, cd, "DELETE");
}

// delete artist from database
// attached data to CD will also be deleted
function deleteArtist(){
    var id_artist = document.getElementById("id_artist").value;
    var artist = '{"id":'+id_artist+'}'; 
    deleteData('api/artist/'+id_artist, artist, "DELETE");
}

// delete genre from database
// attached data to CD will also be deleted
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

// get CD data from available database
// retrieved data is needed, to get title 
function getDataCD(api) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            cdLijst = JSON.parse(this.responseText);
            var selCDs = document.getElementById("AvailableMusicInDatabase");
            selCDs.innerHTML = "";
            var opt = document.createElement("option");
            opt.value = 0;
            opt.textContent = "Available Music In Database" ;
            selCDs.appendChild(opt);
            for (var i=0 ; i< cdLijst.length ; i++) {
                opt = document.createElement("option");
                opt.value = cdLijst[i].id;
                opt.textContent = cdLijst[i].title ;
                selCDs.appendChild(opt);
            }
        }
    };
    xhttp.open("GET", "http://localhost:8082/"+api);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

// get artist data from available database
// retrieved data is needed, to get artistName
function getDataArtist(api, varid) { 
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var artistLijst = JSON.parse(this.responseText);
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
            }
            var subcda=document.getElementById("subCDsA");
            subcda.innerHTML="";
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

// make selection on dropdown list of available CDs
function selectCD(event) {
    var id=event.target.value;
    getCDByID(id);

}      

// make selection on dropdown list of available artists
function selectArtist(event) {
    var id=event.target.value;
    var artist = getArtistByID(id);
    var subcda=document.getElementById("subCDsA");
    subcda.innerHTML="";
    for (var i=0 ; i< cdLijst.length ; i++) {
         for (var j=0 ; j< cdLijst[i].artists.length ; j++) {
               if (id==cdLijst[i].artists[j].id) {
                    var opt = document.createElement("option");
                    opt.value = cdLijst[i].id;
                    opt.textContent = cdLijst[i].title ;
                    subcda.appendChild(opt);
               }
         }       
    }
}

// get data artist by id from endpoint 
function getArtistByID(id) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           var artist = JSON.parse(this.responseText);
            this.artistSelected=artist;
            document.getElementById("id_artist").value=artist.id;
            document.getElementById("artistname").value=artist.artistName;
            getExternalData(artist);
        }
    };
    xhttp.open("GET", "http://localhost:8082/api/artist/"+id);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

// make selection on dropdown list of available genres
function selectGenre(event) {
    var id=event.target.value;
    var genre = getGenreByID(id);
    var subcdg=document.getElementById("subCDsG");
    subcdg.innerHTML="";
    for (var i=0 ; i< cdLijst.length ; i++) {
         for (var j=0 ; j< cdLijst[i].genres.length ; j++) {
               if (id==cdLijst[i].genres[j].id) {
                    var opt = document.createElement("option");
                    opt.value = cdLijst[i].id;
                    opt.textContent = cdLijst[i].title ;
                    subcdg.appendChild(opt);
               }
         }       
    }
}

// get data genre by id from endpoint
function getGenreByID(id) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           var genre = JSON.parse(this.responseText);
           this.genreSelected=genre;
           document.getElementById("id_genre").value=genre.id;
           document.getElementById("genrename").value=genre.genreName;
        }
    };
    xhttp.open("GET", "http://localhost:8082/api/genre/"+id);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

// get data CD by id from endpoint
function getCDByID(id) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           var cd = JSON.parse(this.responseText);
           this.cdSelected=cd;
           document.getElementById("id").value=cd.id;
           document.getElementById("title").value=cd.title;
           document.getElementById("year").value=cd.year;
           document.getElementById("origin").value=cd.origin;
           document.getElementById("remarks").value=cd.remarks;
           getExternalData(cd.title);
           return cd;
        }
    };
    xhttp.open("GET", "http://localhost:8082/api/cd/"+id);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

// show available results in array
function showArray(myArray) {
    for (i=0; i<myArray.length; j++) {
            if (!checkDuplicate(myArray[i])) {
                nodupArray.push(myArray[i]);
            }
        }
}

// function to check for duplicates in array
function checkDuplicate(data){
    for (j=0; j<uniqueArtistArray.length; j++) {
        if (uniqueArtistArray[j]==data) {
            return true;
        }
    }
    return false;
}

// get data from open music source api
// for accessing data apikey is needed
// a search method is used on title
// this returns all artist related to this title
// this means that  this list also contains duplicates on artists
// therefore a function checkDuplicate is created
// the duplicates will be deleted
// data available after removing duplicates is used to get additional information from the open source api
function getExternalData(title) {
    var url="http://ws.audioscrobbler.com/2.0/?method=album.search&album="+title+ "&api_key=5e4225aa6762d769875182b25f45f325&format=json";
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
                var extCD = JSON.parse(this.responseText);
                    for  (var j=0; j<extCD.results.albummatches.album.length; j++) {
                            if (!checkDuplicate(extCD.results.albummatches.album[j].artist)) {
                                uniqueArtistArray.push(extCD.results.albummatches.album[j].artist);
                            }
                    }  
                ArtistAPI(uniqueArtistArray); 
                // document.getElementById("ResultsArtistTitle").innerHTML = uniqueArtistArray;
                console.log();
        }
    };

    xhttp.open("GET", url);
    xhttp.send();
}

// create a dropdown- or pulldownmenu of unique artists from open source API
function ArtistAPI(myArray){
    var artistapi=document.getElementById("ArtistsFromAPI");
    artistapi.innerHTML="";
    for(q=0 ; q < myArray.length ; q++){
                    var opt = document.createElement("option");
                    opt.value = myArray[q];
                    opt.textContent = myArray[q] ;
                    artistapi.appendChild(opt);


    }
}

