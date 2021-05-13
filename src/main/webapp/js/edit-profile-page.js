document.addEventListener("DOMContentLoaded", function(event) {

    // Form validation while typing
    document.getElementById("password").addEventListener("keyup", function(event) {validatePassword(0)});
    document.getElementById("phonenumber").addEventListener("keyup", function(event) {validatePhoneNumber(0)});
    //document.getElementById("address").addEventListener("keyup", function(event) {})
    document.getElementById("idCity").addEventListener("keyup", function(event) {validateIdCity()});

});



