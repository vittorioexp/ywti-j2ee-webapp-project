document.addEventListener("DOMContentLoaded", function(event) {

    // Form validation while typing
    document.getElementsById("password").addEventListener("keyup", function(event) {validatePassword(0)})
    document.getElementsById("phonenumber").addEventListener("keyup", function(event) {validatePhoneNumber(0)})
    document.getElementsById("address").addEventListener("keyup", function(event) {})
    document.getElementsById("idCity").addEventListener("keyup", function(event) {validateIdCity})
    document.getElementsById("edit-profile-button").addEventListener("click", fetchEditProfile)

});

function fetchEditProfile(){

    var elements = document.getElementById("edit-profile-form").elements;

    //Field checking is performed only by javascript and servlet

    for (var i = 0, element; element = elements[i++];) {
        if (element.value === ""){
            let error = document.getElementById("error");
            element.className = "invalid";
            error.innerHTML = "Complete all fields!";
            error.className = "error";
            break;
        }
    }

}


function validateIdCity(){
    let idCity = document.getElementsById("idCity").value;
    if(idCity<0){
        let error = document.getElementById("error");
        idCity.className = "invalid";
        error.innerHTML = "Invali ID";
        error.className = "error";
    }
    let url = contextPath + "/user/edit";
    let data ="phonenumber="+document.getElementsById("idCity").value+"&"+
        "password"+document.getElementsById("password").value+"&"+
        "address"+document.getElementsById("address").value+"&"+
        "idCity"+document.getElementsById("idCity").value;

    sendRequest(url, "POST", data, function(req){});
}


