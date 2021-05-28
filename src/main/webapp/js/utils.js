/*
Author: Vittorio Esposito
Author: Matteo Piva
Author: Marco Basso
Author: Francesco Giurisato

JS to provide utilities
 */


// Context path
const contextPath = "http://localhost:8080/ywti_wa2021_war";

document.addEventListener("DOMContentLoaded", function(event) {
    // Load navbar and footer
    fetchTemplate();
})

// For mobile devices, load hamburger menu
function loadHamburger() {
    let x = document.getElementById("header-bar");
    if (x.className === "") {
        x.className += "responsive";
    } else {
        x.className = "";
    }
}

// Load the list of type adv (locally or remotely)
function getTypeAdvList(elementId, placeholder) {

    let container = document.getElementById(elementId);
    let head = "";
    let str = "";

    // Checks if the list is already in the storage
    let storageList = localStorage.getItem("typeAdvList");

    if (storageList===null || !storageList.includes("option")) {
    // The list is not in the storage
    let url = contextPath + "/typeAdv";
    $.getJSON(url, function (res) {
    let typeAdvList = res.resourceList;
    if (typeAdvList.length>0) {
        head = "<option value=\"\" disabled selected>" + placeholder + "</option>\n";
        typeAdvList.forEach(function(resource) {
            let typeAdv = resource.typeAdvertisement;
            let idType = typeAdv.idType;
            let type = typeAdv.type;

            str += "<option value=" + idType + ">" + type + "</option>\n";
        });

        // Save the list in the storage
        localStorage.setItem("typeAdvList", str);

    } else {
        head = "<option value=" + 0 + ">Error</option>\n";
    }
    // Display the list
    container.innerHTML = head + str;
    });

    } else {
    // The list is in the storage
    head = "<option value=\"\" disabled selected>" + placeholder + "</option>\n";
    str = localStorage.getItem("typeAdvList");
    container.innerHTML = head + str;
    }
}

// Load the list of cities (locally or remotely)
function getCityList(elementId, placeholder) {

    let container = document.getElementById(elementId);
    let head = "";
    let str = "";

    // Checks if the list is already in the storage
    let storageList = localStorage.getItem("cityList");

    if (storageList===null || !storageList.includes("option")) {
        // The list is not in the storage
        let url = contextPath + "/cities";
        $.getJSON(url, function (res) {
            let cityList = res.resourceList;
            if (cityList.length>0) {
                head = "<option value=\"\" disabled selected>" + placeholder + "</option>\n";
                cityList.forEach(function(resource) {
                    let city = resource.city;
                    let idCity = city.idCity;
                    let idCityName = city.idCityName;

                    str += "<option value=" + idCity + ">" + idCityName + "</option>\n";
                });

                // Save the list in the storage
                localStorage.setItem("cityList", str);

            } else {
                head = "<option value=" + 0 + ">Error</option>\n";
            }
            // Display the list
            container.innerHTML = head + str;
        });

    } else {
        // The list is in the storage
        head = "<option value=\"\" disabled selected>" + placeholder + "</option>\n";
        str = localStorage.getItem("cityList");
        container.innerHTML = head + str;
    }
}

// Returns true if the user is logged in (checking the storage)
function isLoggedIn() {
    return localStorage.getItem("loggedIn")===true || localStorage.getItem("loggedIn")==="true";
}

// Returns the user email
function getUserEmail() {
    if (!isLoggedIn()) {
        return "";
    }
    return localStorage.getItem("userEmail");
}

// Returns the user role: company/tourist
function getUserRole() {
    if (!isLoggedIn()) {
        return "";
    }
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
    // Show appropriate content to logged/unlogged user
    if (isLoggedIn()) {

        list = document.getElementsByClassName("unlogged");
        for (i = 0; i < list.length; i++) {
            list[i].setAttribute("class", "unlogged");
            list[i].style.display = "none";
        }

        list = document.getElementsByClassName("logged");
        for (i = 0; i < list.length; i++) {
            list[i].setAttribute("class", "logged");
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
            list[i].setAttribute("class", "unlogged");
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
    if (isLoggedIn()) {
        document.getElementById("footer-register").style.display = "none";
        document.getElementById("user-email").style.display = "block";
        let email = getUserEmail();
        document.getElementById("user-email").innerHTML = "Logged in as " + email;
    } else {
        document.getElementById("footer-register").style.display = "block";
        document.getElementById("user-email").style.display = "none";
        document.getElementById("user-email").innerHTML = "";
    }
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

// Checks if an email is valid. Takes in input the index of the input tag
// Prints the error in a tag with id="error"
function validateEmail(index) {
    // Error section
    let error = document.getElementById("error");

    let email = document.getElementsByName("email")[index].value;
    console.log(email);
    if (email.length === 0) {
        error.innerHTML = "";
        return;
    }

    if (typeof email === "string" || email instanceof String) {
        // it's a string

        let emailRegExp = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

        if (emailRegExp.test(email)) {
            error.innerHTML = "";
        } else {
            error.innerHTML = "Invalid email";
            return;
        }
    } else {
        // it's not a string
        error.innerHTML = "Invalid email";
        return;
    }
}

// Checks if a password is valid. Takes in input the index of the input tag
// Prints the error in a tag with id="error"
function validatePassword(index) {

    // Error section
    let error = document.getElementById("error");

    let password = document.getElementsByName("password")[index].value;

    if (password.length === 0) {
        error.innerHTML = "";
        return;
    }

    // Validate length
    if(password.length >= 8) {
        error.innerHTML = "";
    } else {
        error.innerHTML = "Invalid password: insert at least 8 characters";
        return;
    }

    // Validate lowercase letters
    let lowerCaseLetters = /[a-z]/g;
    if(password.match(lowerCaseLetters)) {
        error.innerHTML = "";
    } else {
        error.innerHTML = "Invalid password: insert at least one lower case character";
        return;
    }

    // Validate capital letters
    let upperCaseLetters = /[A-Z]/g;
    if(password.match(upperCaseLetters)) {
        error.innerHTML = "";
    } else {
        error.innerHTML = "Invalid password: insert at least one upper case character";
        return;
    }

    // Validate numbers
    let numbers = /[0-9]/g;
    if(password.match(numbers)) {
        error.innerHTML = "";
    } else {
        error.innerHTML = "Invalid password: insert at least one number";
        return;
    }
}

// Checks if a phone number is valid. Takes in input the index of the input tag
function validatePhoneNumber(index) {

    // Error section
    let error = document.getElementById("error");

    let phonenumber = document.getElementsByName("phonenumber")[index].value;

    if (phonenumber.length === 0) {
        error.innerHTML = "";
        return;
    }

    // Validate length
    if(phonenumber.length >= 7 && phonenumber.length <= 14 ) {
        error.innerHTML = "";
    } else {
        error.innerHTML = "Invalid phone number";
        return;
    }

    // Validate numbers
    let numbers = /^[0-9]*$/;
    if(phonenumber.match(numbers)) {
        error.innerHTML = "";
    } else {
        error.innerHTML = "Invalid phone number";
        return;
    }

}

// Checks if a id city is valid. Takes in input the index of the input tag
function validateIdCity(){
    let idCity = document.getElementById("idCity").value;
    if(idCity<=0){
        let error = document.getElementById("error");
        error.innerHTML = "Invalid ID";
        return
    } else {
        error.innerHTML = "";
    }
}

// Checks if a title is valid
function validateTitle(){
    let title = document.getElementById("title").value;
    let error = document.getElementById("error");
    let regex = /[\{\}]/g;

    if (title.length === 0) {
        error.innerHTML = error.innerHTML.replace('<p>Invalid title.</p>','');
        return;
    }

    if (regex.test(title) || title.length < 5 || title.length > 200) {
        if(!error.innerHTML.includes("<p>Invalid title.</p>")){
            error.innerHTML +="<p>Invalid title.</p>";
        }
        return;
    }

    // At this point there are no errors
    error.innerHTML = error.innerHTML.replace('<p>Invalid title.</p>','');
}

// Checks if a description is valid
function validateDescription(){
    let description = document.getElementById("description").value;
    let error = document.getElementById("error");
    let regex = /[\{\}]/g;

    if (description.length === 0) {
        error.innerHTML = error.innerHTML.replace('<p>Invalid description.</p>','');
        return;
    }

    if (regex.test(description) || description.length < 5  || description.length > 10000) {
        if(error.innerHTML.includes("<p>Invalid description.</p>")){
            return;
        } else if(!error.innerHTML.includes("<p>Invalid description.</p>")){
            error.innerHTML +="<p>Invalid description.</p>";
            return;
        }
    }
    error.innerHTML = error.innerHTML.replace('<p>Invalid description.</p>','');
}

// Checks if a price is valid
function validatePrice(){
    let price = document.getElementById("price").value;
    let error = document.getElementById("error");

    if (price.length === 0) {
        error.innerHTML = error.innerHTML.replace('<p>Invalid price.</p>','');
        return;
    }

    if (price.length === 0 || price<=0) {
        if(error.innerHTML.includes("<p>Invalid price.</p>")){
            return;
        } else if(!error.innerHTML.includes("<p>Invalid price.</p>")){
            error.innerHTML += "<p>Invalid price.</p>";
            return;
        }
    }
    error.innerHTML = error.innerHTML.replace('<p>Invalid price.</p>','');
}

// Checks if a number of items is valid
function validateNumTotItem(){
    let numTotItem = document.getElementById("numTotItem").value;
    let error = document.getElementById("error");

    if (numTotItem.length === 0) {
        error.innerHTML = error.innerHTML.replace('<p>Invalid number of item.</p>','');
        return;
    }

    if(numTotItem<=0){
        if(error.innerHTML.includes("<p>Invalid number of item.</p>")){
            return;
        } else if(!error.innerHTML.includes("<p>Invalid number of item.</p>")){
            error.innerHTML +="<p>Invalid number of item.</p>";
            return;
        }
    }
    error.innerHTML = error.innerHTML.replace('<p>Invalid number of item.</p>','');
}

/*
Returns a string ready to be send in JSON
 */
function sanitizeString(str) {
    return str.replace(/(?:\r|\n|\r\n)/g," ").replace(/["]/g, "'");
}

/*
Returns true if the string is ready to be send in JSON, false otherwise
 */
function validateStringOnSubmit(str) {
    let regex = /[\{\}]/g;
    return !regex.test(str);
}

/*
Returns true if the number is ready to be send in JSON, false otherwise
 */
function validateIntegerOnSubmit(num) {
    if(num < 0 || num > 2000){
        return false;
    }
    return true;
}
