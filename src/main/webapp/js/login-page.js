

document.addEventListener("DOMContentLoaded", function(event) {
    document.getElementById("login-button").addEventListener("click", loginUserEmail);
})

function loginUserEmail() {
    let url = new URL(contextPath+"/user/login");
    // put code here
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    url.searchParams.set("email", email);
    url.searchParams.set("password", password);
    // TODO: fix this
    setCookie("email", email,1);
    sendGenericGetRequest(url, function(req) {});
}


