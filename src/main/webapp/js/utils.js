
const contextPath = "http://localhost:8080/ywti_wa2021_war";

document.addEventListener("DOMContentLoaded", function(event) {
    fetchTemplate();
})

function getTypeAdvList(elementId) {

    let container = document.getElementById(elementId);
    let str = "";

    // Checks if the list is already in the storage
    let storageList = localStorage.getItem("typeAdvList");

    if (storageList===null || !storageList.includes("option")) {
        // The list is not in the storage
        let url = contextPath + "/typeAdv";
        $.getJSON(url, function (res) {
            let typeAdvList = res.resourceList;
            if (typeAdvList.length>0) {
                str = "<option value=\"\" disabled selected>What</option>\n";
                typeAdvList.forEach(function(resource) {
                    let typeAdv = resource.typeAdvertisement;
                    let idType = typeAdv.idType;
                    let type = typeAdv.type;

                    str += "<option value=" + idType + ">" + type + "</option>\n";
                });

                // Save the list in the storage
                localStorage.setItem("typeAdvList", str);

            } else {
                str = "<option value=" + 0 + ">Error</option>\n";
            }
            // Display the list
            container.innerHTML = str;
        });

    } else {
        // The list is in the storage
        str = localStorage.getItem("typeAdvList");
        container.innerHTML = str;
    }
}

function getCityList(elementId) {

    let container = document.getElementById(elementId);
    let str = "";

    // Checks if the list is already in the storage
    let storageList = localStorage.getItem("cityList");

    if (storageList===null || !storageList.includes("option")) {
        // The list is not in the storage
        let url = contextPath + "/cities";
        $.getJSON(url, function (res) {
            let cityList = res.resourceList;
            if (cityList.length>0) {
                //TODO: change "Where" keywords with "Your city" in register form
                str = "<option value=\"\" disabled selected>Where</option>\n";
                cityList.forEach(function(resource) {
                    let city = resource.city;
                    let idCity = city.idCity;
                    let idCityName = city.idCityName;

                    str += "<option value=" + idCity + ">" + idCityName + "</option>\n";
                });

                // Save the list in the storage
                localStorage.setItem("cityList", str);

            } else {
                str = "<option value=" + 0 + ">Error</option>\n";
            }
            // Display the list
            container.innerHTML = str;
        });

    } else {
        // The list is in the storage
        str = localStorage.getItem("cityList");
        container.innerHTML = str;
    }
}

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

    let navbar = document.getElementById("navbar-area");

    if (navbar===null) {
        return;
    }

    navbar.innerHTML = data;
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
    let footer = document.getElementById("footer-area");
    if (footer===null) {
        return;
    }
    footer.innerHTML=data;
}

// Sends a generic HTTP request
function sendRequest(url, method, data, callback) {
    let httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log("Cannot create an XMLHTTP instance");
        return false;
    }
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                callback(httpRequest.responseText);
            }
            else {
                console.log(httpRequest.responseText);
                console.log("problem processing the request");
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
            console.log("HTTP method not allowed!");
            return false;
    }
}

function sendJsonRequest(url, method, data, callback) {
    let httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log("Cannot create an XMLHTTP instance");
        return false;
    }
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                callback(httpRequest.responseText);
            }
            else {
                console.log(httpRequest.responseText);
                console.log("problem processing the request");
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
            console.log("HTTP method not allowed!");
            return false;
    }
}

function validateEmail(index) {

    // Error section
    let error = document.getElementById("error");

    let email = document.getElementsByName("email")[index];
    let emailRegExp = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    if (email.value.length < 5 || emailRegExp.test(email.value)) {
        //email.className = "valid";
        error.innerHTML = "";
        error.className = "error";
    } else {
        //email.className = "invalid";
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

function validatePhoneNumber(index) {

    // Error section
    let error = document.getElementById("error");

    let phonenumber = document.getElementsByName("phonenumber")[index];

    // Validate length
    if(phonenumber.value.length >= 7 && phonenumber.value.length <= 14 ) {
        error.innerHTML = "";
        error.className = "error";
    } else {
        error.innerHTML = "Invalid phone number";
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

function validateIdCity(){
    let idCity = document.getElementById("idCity").value;
    if(idCity<=0){
        let error = document.getElementById("error");
        idCity.className = "invalid";
        error.innerHTML = "Invalid ID";
        error.className = "error";
    }
}


function validateTitle(){
    let title = document.getElementById("title");
    let regex = /[\{\"\}]/g;
    let error = document.getElementById("error");
    if(regex.test(title.value) || title.value.length===0){
        error.textContent +="<article>Invalid title</article>";
        error.className = "error active";
        return;
    }
    error.textContent.replace('<article>Invalid title</article>','');
}

function validateDescription(){
    let description = document.getElementById("description");
    let regex = /[\{\"\}]/g;
    let error = document.getElementById("error");
    if(regex.test(description.value) || description.value.length===0){
        error.textContent +="<article>Invalid description</article>";
        error.className = "error active";
        return;
    }
    error.textContent.replace('<article>Invalid description</article>','');
}

function validatePrice(){
    let price = document.getElementById("price");
    let error = document.getElementById("error");
    if(price.value<=0 || price.value.length===0){
        error.textContent += "<article>Invalid price</article>";
        error.className = "error active";
        return;
    }
    error.textContent.replace('<article>Invalid price</article>','');
}

function validateNumTotItem(){
    let numTotItem = document.getElementById("numTotItem");
    let error = document.getElementById("error");
    if(numTotItem.value<=0 || numTotItem.value.length===0){
        error.textContent +="<article>Invalid numtotitem</article>";
        error.className = "error active";
        return;
    }
    error.textContent.replace('<article>Invalid numtotitem</article>','');
}