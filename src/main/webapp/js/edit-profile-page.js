document.addEventListener("DOMContentLoaded", function(event) {

    // Form validation while typing
    //document.getElementById("password").addEventListener("focusout", function(event) {validatePassword(0)});
    //document.getElementById("phonenumber").addEventListener("focusout", function(event) {validatePhoneNumber(0)});
    //document.getElementById("address").addEventListener("keyup", function(event) {})
    //document.getElementById("idCity").addEventListener("keyup", function(event) {validateIdCity()});

    // Fetches the list of typeAdvertisements and Cities
    $.getScript(contextPath + "/js/utils.js",function(){
        getCityList("idCity", "City");
    });

    document.getElementById("edit-profile-button").addEventListener("click", function(event){
        event.preventDefault();
        fetchEditProfile();
    });

});

function fetchEditProfile() {

    let messageError = "";
    let errorOccurred = false;

    let password = document.getElementById("password").value;
    let phonenumber = document.getElementById("phonenumber").value;
    let address = document.getElementById("address").value;


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

    if (errorOccurred) {
        alert("Error found:" + "\n" + messageError);
        return;
    }else{
        let data = {
            "password": password,
            "phonenumber": phonenumber,
            "address": address
        }

        let url = contextPath + "/user/edit";
        $.ajax({
            url: url,
            data: data,
            method: 'POST',
            success: function(res) {
                alert(resMessage.message + " " + resMessage.errorDetails);
                window.location.href = contextPath + "/user/profile";
            },
            error: function(res) {
                let resMessage = res.responseJSON.message;
                alert(resMessage.message + " " + resMessage.errorDetails);
                document.getElementById("loginForm").reset();
                location.reload();
            }
        });
    }

}

function validatePasswordOnSubmit(password){
    return password.length < 8;
}

function validatePhoneNumberOnSubmit(phonenumber){
    let numbers = /^[0-9]*$/;
    return (phonenumber.match(numbers) || phonenumber.length < 7 || phonenumber.length > 14);
}

function validateAddressOnSubmit(address){
    return address.length < 4 || address.length > 150 ;
}



