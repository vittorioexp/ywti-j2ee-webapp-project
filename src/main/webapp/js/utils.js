// TODO: fix utils.js

const contextPath = "http://localhost:63342/ywti-wa2021";

function sanitize(str) {
    return String(str).replace(/&/g, "&amp;").replace(/</g, "&lt;")
        .replace(/>/g, "&gt;").replace(/"/g, "&quot;");
}

document.addEventListener("DOMContentLoaded", function(event) {
    fetchTemplate();
});

function fetchTemplate(){

    let navbarUrl = new URL(contextPath+"/html/reusable-snippets/navbar.html");
    let footerUrl = new URL(contextPath+"/html/reusable-snippets/footer.html");

    sendGenericGetRequest(navbarUrl, loadNavbar);
    sendGenericGetRequest(footerUrl, loadFooter);
}

function sendGenericGetRequest(url, callback){
    let httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert("Cannot create an XMLHTTP instance");
        return false;
    }
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                callback(httpRequest.responseText);
            }
            else {
                console.log(httpRequest.responseText);
                alert("problem processing the request");
            }
        }

    };
    httpRequest.open("GET", url);
    httpRequest.send();
}

function loadNavbar(data){

    document.getElementById("navbar-area")
        .innerHTML = data;
    document.getElementById("logo-button")
        .setAttribute("href", contextPath+"/index");
    document.getElementById("home-button")
        .setAttribute("href", contextPath+"/index");
    document.getElementById("profile-button")
        .setAttribute("href", contextPath+"/user/profile");
    document.getElementById("logout-button")
        .setAttribute("href", contextPath+"/user/do-logout");
    document.getElementById("login-button")
        .setAttribute("href", contextPath+"/user/do-login");
    document.getElementById("register-button")
        .setAttribute("href", contextPath+"/user/do-register");
    document.getElementById("contacts-button")
        .setAttribute("href", contextPath+"/contacts");

    let i;
    let list;

    // TODO: use cookies https://www.w3schools.com/js/js_cookies.asp
    let cookie = document.cookie;
    let email = cookie.substring(
        cookie.lastIndexOf("email=")+1,
        cookie.indexOf(";", 0)
    );

    //let authorization = sessionStorage.getItem("Authorization");
    
    if (email!=null) {

        /*
        // Basic XXXXXXXX
        let encodedAuth = authorization.substring(
            authorization.lastIndexOf("Basic ") + 1,
        );

        let decodedAuth = atob(encodedAuth);

        document.getElementById("user-email").innerHTML = decodedAuth.substring(
            0,
            decodedAuth.lastIndexOf(":")
        );*/

        document.getElementById("user-email").innerHTML = email;

        list = document.getElementsByClassName("unlogged")
        for (i = 0; i < list.length; i++) {
            list[i].setAttribute("class", "unlogged d-none");
        }

        list = document.getElementsByClassName("logged");
        for (i = 0; i < list.length; i++) {
            list[i].setAttribute("class", "logged d-block");
        }
    } else {

        list = document.getElementsByClassName("logged");
        for (i = 0; i < list.length; i++) {
            list[i].setAttribute("class", "logged d-none");
        }

        list = document.getElementsByClassName("unlogged")
        for (i = 0; i < list.length; i++) {
            list[i].setAttribute("class", "unlogged d-block");
        }
    }
}

function loadFooter(data){
    document.getElementById("footer-area").innerHTML= data;
}