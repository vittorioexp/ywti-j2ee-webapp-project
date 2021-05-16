
document.addEventListener("DOMContentLoaded", function(event) {

    // Form validation while typing
    document.getElementById("email").addEventListener("focusout", function(event) {
        validateEmail(0)
    })
    document.getElementById("password").addEventListener("focusout", function(event) {
        validatePassword(0)
    })

    //Submit button
    document.getElementById("login-button").addEventListener("click", fetchLogin);
});

function fetchLogin(){

    let url = contextPath+"/user/login";

    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    let data = {
        "email": email,
        "password": password
    }

    $.getJSON(url, data, function (res) {

        if(res.status === 200){
            //Redirect made on the server side
        }else{
            // Parses the JSON obj
            let jsonData = JSON.parse(res).message;
            let messageError = jsonData['message'];
            alert(messageError);
            document.getElementById("loginForm").reset();
        }
    });
}


