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

function validatePasswordOnSubmit(password){
    // Min. size of the password = 8
    return password.length < 8;
}

function validatePhoneNumberOnSubmit(phonenumber){
    // Regex for the phone number
    let numbers = /^[0-9]$/;
    return (phonenumber.match(numbers) || phonenumber.length < 7 || phonenumber.length > 14);
}

function validateAddressOnSubmit(address){
    return address.length < 4 || address.length > 150 ;
}

function validateCityOnSubmit(city){
    return city==="" || city < 1;
}



