document.addEventListener("DOMContentLoaded", function(event) {

    // Form validation while typing
    document.getElementById("password").addEventListener("focusout", function(event) {validatePassword(0)});
    document.getElementById("phonenumber").addEventListener("focusout", function(event) {validatePhoneNumber(0)});
    //document.getElementById("address").addEventListener("keyup", function(event) {})
    //document.getElementById("idCity").addEventListener("keyup", function(event) {validateIdCity()});

    // Fetches the list of typeAdvertisements and Cities
    $.getScript(contextPath + "/js/utils.js",function(){
        getCityList("idCity");
    });

});



