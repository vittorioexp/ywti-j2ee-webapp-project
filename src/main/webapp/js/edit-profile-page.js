
document.addEventListener("DOMContentLoaded", function(event) {

    fetchProfile();

});

const input = document.querySelector('input');

input.addEventListener('change', checkValue);

function checkValue(e) {
    if (e.target.value ===""){
        alert ( e.target.name + " field is empty!");
    }else if (e.target.value === document.getElementById( e.target.id + "Old").value){
        alert ( e.target.name + " field has not been modified!");
    }
}

function fetchProfile() {
    let url = new URL(contextPath+"/user/profile");
    sendGenericGetRequest(url ,loadPreviousProfile());
}

function loadPreviousProfile(req){

    document.getElementById("passwordOld").value = req.getAttribute("password");
    document.getElementById("idCityOld").value = req.getAttribute("idCity");
    document.getElementById("addressOld").value = req.getAttribute("address");
    document.getElementById("phonenumberOld").value = req.getAttribute("phonenumber");

}

function updateProfile(req){

}

