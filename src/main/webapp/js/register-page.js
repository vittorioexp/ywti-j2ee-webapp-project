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

    //getting the list of cities
    $.getScript(contextPath + "/js/utils.js",function(){
        getCityList("idCity");
        getCityList("idCity_c");
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
    if (checkBox.checked == true){
        company.style.display = "block";
        tourist.style.display = "none";
    } else {
        tourist.style.display = "block";
        company.style.display = "none";
    }
}
