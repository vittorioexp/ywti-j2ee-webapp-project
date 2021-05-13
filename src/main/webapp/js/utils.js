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

function isLoggedIn() {
    return localStorage.getItem("loggedIn");
}

function getUserEmail() {
    return localStorage.getItem("userEmail");
}

function getUserRole() {
    return localStorage.getItem("userRole");
}

// Fetches navbar and footer
function fetchTemplate(){
    let navbarUrl = new URL(contextPath + "/html/reusable-snippets/navbar.html");
    let footerUrl = new URL(contextPath + "/html/reusable-snippets/footer.html");

    sendRequest(navbarUrl, "GET", "", loadNavbar);
    sendRequest(footerUrl, "GET", "", loadFooter);
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

    let userAuthorization = getUserRole();

    // Checks if the user is logged in or not
    if (isLoggedIn()) {

        let email = getUserEmail();
        document.getElementById("user-email").innerHTML = "Logged in as " + email;


        list = document.getElementsByClassName("unlogged");
        for (i = 0; i < list.length; i++) {
            list[i].setAttribute("class", "unlogged d-none");
            list[i].style.display = "none";
        }

        list = document.getElementsByClassName("logged");
        for (i = 0; i < list.length; i++) {
            list[i].setAttribute("class", "logged d-block");
            list[i].style.display = "inline-block";
        }
    } else {

        list = document.getElementsByClassName("logged");
        for (i = 0; i < list.length; i++) {
            list[i].setAttribute("class", "logged d-none");
            list[i].style.display = "none";
        }

        list = document.getElementsByClassName("unlogged");
        for (i = 0; i < list.length; i++) {
            list[i].setAttribute("class", "unlogged d-block");
            list[i].style.display = "inline-block";
        }
    }
}

// Loads the footer
function loadFooter(data){
    document.getElementById("footer-area").innerHTML=data;
}

// Sends a generic HTTP request
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
            httpRequest.open(method,url, true);
            httpRequest.setRequestHeader("Accept","utf-8");
            httpRequest.setRequestHeader("Accept","text/html");
            httpRequest.setRequestHeader("Accept","application/xhtml+xml");
            httpRequest.setRequestHeader("Content-Type","utf-8");
            httpRequest.setRequestHeader("Content-Type","text/html");
            httpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
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
            httpRequest.setRequestHeader("Accept","utf-8");
            httpRequest.setRequestHeader("Content-Type","application/json");
            httpRequest.send(data);
            break;
        default:
            alert("HTTP method not allowed!");
            return false;
    }
}

function validatePhoneNumber(index) {

    // Error section
    let error = document.getElementById("error");

    let phonenumber = document.getElementsByName("phonenumber")[index];

    // Validate length
    if(phonenumber.value.length >= 7 && phonenumber.value.length <= 14 ) {
        error.innerHTML = "";
        error.className = "error";
    } else {
        error.innerHTML = "Invalid phonenumber";
        error.className = "error active";
        return;
    }

    // Validate numbers
    let numbers = /^[0-9]*$/;
    if(phonenumber.value.match(numbers)) {
        error.innerHTML = "";
        error.className = "error";
    } else {
        error.innerHTML = "Invalid phone number";
        error.className = "error active";
        return;
    }

}

function validateEmail(index) {

    // Error section
    let error = document.getElementById("error");

    let email = document.getElementsByName("email")[index];
    let emailRegExp = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    if (email.value.length === 0 || emailRegExp.test(email.value)) {
        email.className = "valid";
        error.innerHTML = "";
        error.className = "error";
    } else {
        email.className = "invalid";
        error.innerHTML = "Invalid email";
        error.className = "error active";
    }
}

function validatePassword(index) {

    // Error section
    let error = document.getElementById("error");

    let password = document.getElementsByName("password")[index];

    // Validate length
    if(password.value.length >= 8) {
        error.innerHTML = "";
        error.className = "error";
    } else {
        error.innerHTML = "Invalid password: insert at least 8 characters";
        error.className = "error active";
        return;
    }

    // Validate lowercase letters
    let lowerCaseLetters = /[a-z]/g;
    if(password.value.match(lowerCaseLetters)) {
        error.innerHTML = "";
        error.className = "error";
    } else {
        error.innerHTML = "Invalid password: insert at least one lower case character";
        error.className = "error active";
        return;
    }

    // Validate capital letters
    let upperCaseLetters = /[A-Z]/g;
    if(password.value.match(upperCaseLetters)) {
        error.innerHTML = "";
        error.className = "error";
    } else {
        error.innerHTML = "Invalid password: insert at least one upper case character";
        error.className = "error active";
        return;
    }

    // Validate numbers
    let numbers = /[0-9]/g;
    if(password.value.match(numbers)) {
        error.innerHTML = "";
        error.className = "error";
    } else {
        error.innerHTML = "Invalid password: insert at least one number";
        error.className = "error active";
        return;
    }
}