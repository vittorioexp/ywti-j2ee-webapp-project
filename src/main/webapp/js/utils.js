
var contextPath = 'http://localhost:63342/ywti-wa2021';

function sanitize(str) {
    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;')
        .replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

document.addEventListener('DOMContentLoaded', function(event) {
    loadTemplate();
})

function loadTemplate(){

    var navbarUrl = new URL(contextPath+'/html/reusable-snippets/navbar.html');
    var footerUrl = new URL(contextPath+'/html/reusable-snippets/footer.html');

    sendGenericGetRequest(navbarUrl, loadNavbar);
    sendGenericGetRequest(footerUrl, loadFooter);
}

function sendGenericGetRequest(url, callback){
    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status == 200) {
                callback(httpRequest.responseText)
            }
            else {
                console.log(req.responseText);
                alert("problem processing the request");
            }
        }

    };

    httpRequest.open('GET', url);
    httpRequest.send();
}

function loadNavbar(data){
    var loggedIn = sessionStorage.getItem("loggedIn");
    var userEmail = sessionStorage.getItem("userEmail");

    document.getElementById("navbar-area").innerHTML = data;
    document.getElementById("logo-button").setAttribute("href", contextPath+"/index");
    document.getElementById("home-button").setAttribute("href", contextPath+"/index");
    document.getElementById("profile-button").setAttribute("href", contextPath+"/user/profile");
    document.getElementById("logout-button").setAttribute("href", contextPath+"/user/do-logout");
    document.getElementById("login-button").setAttribute("href", contextPath+"/user/do-login");
    document.getElementById("register-button").setAttribute("href", contextPath+"/user/do-register");
    document.getElementById("contacts-button").setAttribute("href", contextPath+"/contacts");

    if(loggedIn){

        document.getElementById("user-email").innerHTML = userEmail;

        list = document.getElementsByClassName("unlogged")
        for (var i = 0; i < list.length; i++) {
            list[i].setAttribute("class", 'unlogged d-none');
        }

        list = document.getElementsByClassName("logged");
        for (var i = 0; i < list.length; i++) {
            list[i].setAttribute("class", 'logged d-block');
        }
    }
    if(!loggedIn){

        list = document.getElementsByClassName("logged");
        for (var i = 0; i < list.length; i++) {
            list[i].setAttribute("class", 'logged d-none');
        }

        list = document.getElementsByClassName("unlogged")
        for (var i = 0; i < list.length; i++) {
            list[i].setAttribute("class", 'unlogged d-block');
        }
    }
}

function loadFooter(data){
    document.getElementById("footer-area").innerHTML= data;
}