/*
Author: Marco Basso

JS that manages the modification of the user's profile. Checks are made on the submit button.

 */

document.addEventListener("DOMContentLoaded", function(event) {

    // Fetches the list of typeAdvertisements and Cities
    getCityList("idCity", "City");

    document.getElementById("edit-profile-button").addEventListener("click", function(event){
        event.preventDefault();
        fetchEditProfile();
    });

});

function fetchEditProfile() {

    let messageError = "";
    let errorOccurred = false;

    // Retrieve the values of the input fields
    let password = document.getElementById("password").value;
    let phonenumber = document.getElementById("phonenumber").value;
    let address = document.getElementById("address").value;
    let city = document.getElementById("idCity").value;

    // Validate the various input fields
    if (validatePasswordOnSubmit(password)) {
        messageError += "Password invalid" + "\n";
        document.getElementById("password").value = document.getElementById("password").defaultValue;
        errorOccurred = true;
    }
    if (validatePhoneNumberOnSubmit(phonenumber)) {
        messageError += "Phone number invalid" + "\n";
        document.getElementById("phonenumber").value = document.getElementById("phonenumber").defaultValue;
        errorOccurred = true;
    }
    if (validateAddressOnSubmit(address)) {
        messageError += "Address invalid" + "\n";
        document.getElementById("address").value = document.getElementById("address").defaultValue;
        errorOccurred = true;
    }
    if(validateCityOnSubmit(city)){
        messageError += "City invalid" + "\n";
        errorOccurred = true;
    }


    if (errorOccurred) {
        alert("Error found:" + "\n" + messageError);
        return;
    }else{
        // If the fields are entered correctly
        let data = {
            "password": CryptoJS.MD5(password).toString(),
            "phonenumber": phonenumber,
            "address": address,
            "idCity": city
        }

        let url = contextPath + "/user/edit";
        $.ajax({
            url: url,
            data: data,
            method: 'POST',
            success: function(res) {
                // Successful edit request
                alert(res.message.message);
                window.location.href = contextPath + "/user/profile";
            },
            error: function(res) {
                // Error in sending the request
                let resMessage = res.responseJSON.message;
                alert(resMessage.message + " " + resMessage.errorDetails);
                document.getElementById("edit-profile-form").reset();
            }
        });
    }

}

// Returns true in case of error
function validatePasswordOnSubmit(password){

    // Validate length
    if(password.length < 8) {
        return true;
    }

    // Validate lowercase letters
    let lowerCaseLetters = /[a-z]/g;
    if(!password.match(lowerCaseLetters)) {
        return true;
    }

    // Validate capital letters
    let upperCaseLetters = /[A-Z]/g;
    if(!password.match(upperCaseLetters)) {
        return true;
    }

    // Validate numbers
    let numbers = /[0-9]/g;
    if(!password.match(numbers)) {
        return true;
    }
    return false;
}

// Returns true in case of error
function validatePhoneNumberOnSubmit(phonenumber){
    // Regex for the phone number
    let numbers = /[0-9]/;

    return (isNaN(phonenumber) || phonenumber.length < 7 || phonenumber.length > 14);
}

// Returns true in case of error
function validateAddressOnSubmit(address){
    return address.length < 4 || address.length > 150 ;
}

// Returns true in case of error
function validateCityOnSubmit(city){
    return city==="" || city < 1;
}



