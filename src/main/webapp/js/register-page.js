document.addEventListener("DOMContentLoaded", function(event) {

    // By default, shows only the tourist registration form
    checkUserType();

    // On each click of the checkbox, show the proper registration form
    //document.getElementById("userTypeCheckbox").addEventListener("CheckboxStateChange", checkUserType, false);

    // Form validation while typing
    document.getElementsByName("email")[0].addEventListener("keyup", function(event) {validateEmail(0)})
    document.getElementsByName("email")[1].addEventListener("keyup", function(event) {validateEmail(1)})
    document.getElementsByName("password")[0].addEventListener("keyup", function(event) {validatePassword(0)})
    document.getElementsByName("password")[1].addEventListener("keyup", function(event) {validatePassword(1)})

});

// Checks the user type, and display a different form depending on the user type
function checkUserType(){

    // Get the checkbox
    let checkBox = document.getElementById("userTypeCheckbox");

    // Get the output text
    let tourist = document.getElementById("touristRegistration");
    let company = document.getElementById("companyRegistration");

    // If the checkbox is checked, display the proper form
    if (checkBox.checked == true){
        company.style.display = "block";
        tourist.style.display = "none";
    } else {
        tourist.style.display = "block";
        company.style.display = "none";
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

    // Validate length
    if(password.value.length >= 8) {
        error.innerHTML = "";
        error.className = "error";
    } else {
        error.innerHTML = "Invalid password: insert at least 8 characters";
        error.className = "error active";
        return;
    }
}