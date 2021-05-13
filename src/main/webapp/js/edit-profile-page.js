document.addEventListener("DOMContentLoaded", function(event) {

    // Form validation while typing
    document.getElementById("password").addEventListener("keyup", function(event) {validatePassword(0)});
    document.getElementById("phonenumber").addEventListener("keyup", function(event) {validatePhoneNumber(0)});
    //document.getElementById("address").addEventListener("keyup", function(event) {})
    document.getElementById("idCity").addEventListener("keyup", function(event) {validateIdCity()});
    document.getElementById("edit-profile-button").addEventListener(
        "click",
        function(event) {
            event.preventDefault();
            fetchEditProfile();
    });

});

function fetchEditProfile(){

    let url = contextPath + "/user/edit";
    let data =
        "phonenumber=" + document.getElementById("phonenumber").value +"&"+
        "password=" + document.getElementById("password").value +"&"+
        "address=" + document.getElementById("address").value +"&"+
        "idCity=" + document.getElementById("idCity").value;

    sendRequest(url, "POST", sanitize(data), function(req){});

}

function validateIdCity(){
    let idCity = document.getElementById("idCity").value;
    if(idCity<0){
        let error = document.getElementById("error");
        idCity.className = "invalid";
        error.innerHTML = "Invalid ID";
        error.className = "error";
    }
}


