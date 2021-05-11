document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementById("email").addEventListener("input", formValidate);

});

document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementsByTagName("form")[0].addEventListener("submit", formValidateOnSubmit);

});

function formValidate()
{
    let email = document.getElementById("email");
    let error = email.nextElementSibling;
    let emailRegExp = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    let test = email.value.length === 0 || emailRegExp.test(email.value);
    if (test) {
        email.className = "valid";
        error.innerHTML = "";
    } else {
        email.className = "invalid";
        error.innerHTML = "Please insert an e-mail address";
        error.className = "error";
    }
}

function formValidateOnSubmit()
{
    let email = document.getElementById("email");
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