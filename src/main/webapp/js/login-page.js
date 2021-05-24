
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

    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    let data = {
        "email": email,
        "password": CryptoJS.MD5(password).toString()
    }

    $.ajax({
        url: contextPath+"/user/login",
        data: data,
        method: 'GET',
        success: function(res) {
            location.reload();
        },
        error: function(res) {
            let resMessage = res.responseJSON.message;
            alert(resMessage.message + " " + resMessage.errorDetails);
            document.getElementById("loginForm").reset();
            location.reload();
        }
    });
}


