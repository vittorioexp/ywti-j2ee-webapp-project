// TODO: fix utils.js

const contextPath = "http://localhost:8080/ywti_wa2021_war";

function sanitize(str) {
    return String(str).replace(/&/g, "&amp;").replace(/</g, "&lt;")
        .replace(/>/g, "&gt;").replace(/"/g, "&quot;");
}

// TODO: salvare le richieste in delle variabili
document.addEventListener("DOMContentLoaded", function(event) {
    fetchTemplate();
})

// Fetches navbar and footer
function fetchTemplate(){
    let navbarUrl = new URL(contextPath + "/html/reusable-snippets/navbar.html");
    let footerUrl = new URL(contextPath + "/html/reusable-snippets/footer.html");

    sendGenericGetRequest(navbarUrl, loadNavbar);
    sendGenericGetRequest(footerUrl, loadFooter);
}

// Loads the navbar
function loadNavbar(data){

    document.getElementById("navbar-area").innerHTML = data;
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

    let loggedIn = sessionStorage.getItem("loggedIn");
    let userAuthorization = sessionStorage.getItem("userRole");

    // Checks if the user is logged in or not
    if (loggedIn) {

        let email = sessionStorage.getItem("userEmail");
        document.getElementById("user-email").innerHTML = "Logged in as " + email;


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

// Loads the footer
function loadFooter(data){
    document.getElementById("footer-area").innerHTML=data;
}


// Sends a generic HTTP GET request
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


// Sends a generic HTTP GET request
function sendRequest(url, method, data, callback) {
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
    // supported methods
    switch (method) {
        case "GET":
        case "PUT":
        case "POST":
        case "DELETE":
            httpRequest.open(method,url);
            httpRequest.setRequestHeader("Accept","application/json");
            httpRequest.setRequestHeader("Content-Type","application/json");
            httpRequest.send(data);
            break;
        default:
            alert("HTTP method not allowed!");
            return false;
    }
}

// TODO: with HTTP GET, cannot send "data"
function sendJsonRequest(url, method, data, callback) {
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
    // supported methods
    switch (method) {
        case "GET":
        case "PUT":
        case "POST":
        case "DELETE":
            httpRequest.open(method,url, true);
            httpRequest.setRequestHeader("Accept","application/json");
            httpRequest.setRequestHeader("Content-Type","application/json");
            httpRequest.send(data);
            break;
        default:
            alert("HTTP method not allowed!");
            return false;
    }
}
/*
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/ywti_wa2021_war";
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}*/