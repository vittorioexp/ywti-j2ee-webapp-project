
document.addEventListener("DOMContentLoaded", function(event) {

    fetchProfile();
    document.getElementById("edit-profile").addEventListener("click", function(){window.location.replace(contextPath + "/user/do-edit");});

});

function redirectEditProfilePage(){

}

function fetchProfile() {
    let url = new URL(contextPath+"/user/profile");
    sendGenericGetRequest(url ,loadProfile());
}

function loadProfile(req){

    let isTourist = req.getAttribute("userType");
    if(isTourist){

        let userObj = req.getAttribute("user");
        let name = userObj.name;
        let surname = userObj.surname;
        let email = userObj.email;
        let address = userObj.address;
        let phonenumber = userObj.phonenumber;
        let idCity = userObj.idCity;
        let birthDate = userObj.birthDate;

        let attributesName = ["Name: ", "Surname: ", "Birthdate: ","Email: ", "Address: ", "City: ", "Phone Number: "];
        let attributesValue = [name, surname, birthDate, email, address, idCity, phonenumber];

        let section = document.getElementById("userSection");
        for(let i=0;i<7;i++){
            createElement(attributesName[i], attributesValue[i], section);
        }

        let button = document.createElement("button");
        button.innerHTML = "Edit Profile";
        button.id = "edit-profile";
        section.appendChild(button);

    }else{

        let userObj = req.getAttribute("user");
        let name = userObj.name;
        let email = userObj.email;
        let address = userObj.address;
        let phonenumber = userObj.phonenumber;
        let idCity = userObj.idCity;

        document.getElementById("nameUser").innerHTML = name;
        document.getElementById("surnameUser").value = surname;
        document.getElementById("birthdateUser").value = birthDate;
        document.getElementById("emailUser").value = email;
        document.getElementById("addressUser").innerHTML = address;
        document.getElementById("cityUser").value = getCityFromId(idCity);
        document.getElementById("phonenumberUser").value = phonenumber;
    }

}

function getCityFromId(idCity) {
    //TODO: Retrieve city from id
    return idCity;
}

function createElement(name, value, section){
    let par = document.createElement("p");
    let parName = document.createTextNode(name + value.toString());
    let parValue = document.createElement("span");
    parValue.className = "userInfo";
    parName.appendChild(parValue);
    par.appendChild(par.name);
    section.appendChild(par);
}
