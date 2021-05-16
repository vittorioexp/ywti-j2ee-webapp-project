
document.addEventListener("DOMContentLoaded", function(event) {

    // Form validation while typing
    document.getElementById("email").addEventListener("focusout", function(event) {
        validateEmail(0)
    })
    document.getElementById("password").addEventListener("focusout", function(event) {
        validatePassword(0)
    })

    //Submit button
    document.getElementById("login-button").addEventListener("cick", fetchLogin);
});

function fetchLogin(){
    let url = new URL(contextPath+"/user/login");

    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let data = "email"+"="+email+"&"+"password"+"="+password;

    sendRequest(url, "POST", data, function(req){

        if(req.status === 200){
           //Redirect made on the server side
        }else{
            // Parses the JSON obj
            let jsonData = JSON.parse(req).message;
            let messageError = jsonData['message'];
            alert(messageError);
            document.getElementById("loginForm").reset();
        }

    });
}


