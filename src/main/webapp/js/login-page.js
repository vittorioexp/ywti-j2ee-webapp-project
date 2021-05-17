
document.addEventListener("DOMContentLoaded", function(event) {

    // Form validation while typing
    document.getElementById("email").addEventListener("focusout", function(event) {
        validateEmail(0)
    })
    document.getElementById("password").addEventListener("focusout", function(event) {
        validatePassword(0)
    })

    //Submit button
    document.getElementById("login-button").addEventListener("click", function(event) {
        event.preventDefault();
        fetchLogin();
    });
});

function fetchLogin(){

    let url = contextPath+"/user/login";

    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    let data = {
        "email": email,
        "password": password
    }

    $.ajax({
        url: contextPath+"/user/login",
        data: data,
        method: 'GET',
        success: function(data) {
            location.reload();
        },
        error: function(data) {
            let resMessage = data.responseJSON.message;
            alert(resMessage.message + " " + resMessage.errorDetails);
            document.getElementById("loginForm").reset();
            location.reload();
        }
    });
}


