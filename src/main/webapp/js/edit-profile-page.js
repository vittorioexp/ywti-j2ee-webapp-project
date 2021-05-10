
import MD5 from "crypto-js/md5";

document.addEventListener("DOMContentLoaded", function(event) {

    fetchProfile();

});

const input = document.querySelector('input');
const update = document.querySelector('#editProfileForm')

input.addEventListener('change', checkValue);
update.addEventListener("submit", updateProfile );


function checkValue(e) {
    if (e.target.value ===""){
        alert ( e.target.name + " field is empty!");
    }
    else{
        if (e.target.id === "password")
            if (e.target.value === MD5(document.getElementById( e.target.id + "Old").value) )
                alert ( e.target.name + " field has not been modified!");
            else if (e.target.value === document.getElementById( e.target.id + "Old").value)
                alert ( e.target.name + " field has not been modified!");
    }
}

function fetchProfile() {
    let url = new URL(contextPath+"/user/profile");
    sendGenericGetRequest(url ,loadPreviousProfile);
}

function loadPreviousProfile(req){

    let userObj = req.getAttribute("user");
    document.getElementById("passwordOld").value = userObj.password;
    document.getElementById("idCityOld").value = userObj.idCity;
    document.getElementById("addressOld").value = userObj.address;
    document.getElementById("phonenumberOld").value = userObj.phonenumber;

}

function updateProfile() {

    let url = new URL(contextPath + "/user/edit");
    let httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert("Cannot create an XMLHTTP instance");
        return false;
    }
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                alert ( "edit successful ");
            }
            else {
                console.log(httpRequest.responseText);
                alert("problem processing the request");
            }
        }

    };
    httpRequest.setAttribute( "phonenumber" , document.getElementById("phonenumber").value );
    httpRequest.setAttribute( "password" , document.getElementById("password").value );
    httpRequest.setAttribute( "idCity" , document.getElementById("idCity").value );
    httpRequest.setAttribute( "address" , document.getElementById("address").value );
    httpRequest.open("POST", url);
    httpRequest.send();

}

