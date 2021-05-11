document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementsByName("userType").addEventListener("radio", fetchUserType);
    document.getElementsById("register-form").addEventListener("submit", formValidateOnSubmit);

});


function fetchUserType(){
    //TODO: Check the user type, and display a different form
    let userType = document.getElementsByName("userType").value;
}
function formValidateOnSubmit(){
    //TODO: Check input form (validation as in login page)
    let userType = document.getElementsByName("userType").value;
    if(userType == "tourist"){
        let email = document.getElementById("email_t");
        let error = email.nextElementSibling;
        let emailRegExp = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

        let test = email.value.length === 0 || emailRegExp.test(email.value);
        if (test) {
            email.className = "valid";
            error.innerHTML = "";
            error.className = "error";
        } else {
            email.className = "invalid";
            error.innerHTML = "I expect an e-mail!";
            error.className = "error active";
            event.preventDefault();
        }
    }
    else{

    }

}