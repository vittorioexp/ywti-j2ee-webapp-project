/*
Author: Vittorio Esposito
Author: Matteo Piva
Author: Marco Basso
Author: Francesco Giurisato

JS to manage the register page
 */

document.addEventListener("DOMContentLoaded", function(event) {

    // By default, shows only the tourist registration form
    checkUserType();

    // On each click of the checkbox, show the proper registration form
    //document.getElementById("userTypeCheckbox").addEventListener("CheckboxStateChange", checkUserType, false);

    // Form validation while typing
    document.getElementsByName("email")[0].addEventListener("focusout", function(event) {validateEmail(0)})
    document.getElementsByName("email")[1].addEventListener("focusout", function(event) {validateEmail(1)})
    document.getElementsByName("password")[0].addEventListener("focusout", function(event) {validatePassword(0)})
    document.getElementsByName("password")[1].addEventListener("focusout", function(event) {validatePassword(1)})

    getCityList("idCity_t", "City");
    getCityList("idCity_c", "City");

    //Submit buttons
    let registerButton = document.getElementsByName("register-button");

    registerButton.forEach(function(button, index) {

        // Adds an event listener (on click) on each delete adv button
        button.addEventListener(
            "click",
            function(event) {
                event.preventDefault();
                fetchRegister();
            });
    });

});

// Checks the user type, and display a different form depending on the user type
function checkUserType(){

    // Get the checkbox
    let checkBox = document.getElementById("userTypeCheckbox");

    // Get the output text
    let tourist = document.getElementById("touristRegistration");
    let company = document.getElementById("companyRegistration");

    // If the checkbox is checked, display the proper form
    if (checkBox.checked === true){
        company.style.display = "block";
        tourist.style.display = "none";
    } else {
        tourist.style.display = "block";
        company.style.display = "none";
    }
}

// sends register data to the server
function fetchRegister(){

    let data;

    let checkBox = document.getElementById("userTypeCheckbox");

    if (checkBox.checked === true){
        // Company form
        let userType = "company";
        // Data from input tags
        let email = document.getElementById("email_c").value.toString();
        let password = document.getElementById("password_c").value.toString();
        let rpassword = document.getElementById("rpassword_c").value.toString();
        let name = document.getElementById("name_c").value.toString();
        let address = document.getElementById("address_c").value.toString();
        let phone = document.getElementById("phonenumber_c").value.toString();
        let city = document.getElementById("idCity_c").value.toString();

        // data to be sent
        data = {
            "userType": userType,
            "email": email,
            "password": CryptoJS.MD5(password).toString(),
            "rpassword": CryptoJS.MD5(rpassword).toString(),
            "name": name,
            "address": address,
            "phone": phone,
            "city": city
        }

    } else {
        // Tourist form
        let userType = "tourist";
        // Data from input tags
        let email = document.getElementById("email_t").value.toString();
        let password = document.getElementById("password_t").value.toString();
        let rpassword = document.getElementById("rpassword_t").value.toString();
        let name = document.getElementById("name_t").value.toString();
        let surname = document.getElementById("surname_t").value.toString();
        let birthDate = document.getElementById("birthdate_t").value.toString();
        let address = document.getElementById("address_t").value.toString();
        let phone = document.getElementById("phonenumber_t").value.toString();
        let city = document.getElementById("idCity_t").value.toString();

        // data to be sent
        data = {
            "userType": userType,
            "email": email,
            "password": CryptoJS.MD5(password).toString(),
            "rpassword": CryptoJS.MD5(rpassword).toString(),
            "name": name,
            "surname": surname,
            "birthDate": birthDate,
            "address": address,
            "phone": phone,
            "city": city,
        }
    }

    // request to the server
    $.ajax({
        url: contextPath+"/user/register",
        data: data,
        method: 'POST',
        success: function(res) {
            window.location.href = contextPath + "/user/do-login";
        },
        error: function(res) {
            let resMessage = res.responseJSON.message;
            alert(resMessage.message + " " + resMessage.errorDetails);
        }
    });
}