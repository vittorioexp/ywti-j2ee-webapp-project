
document.addEventListener("DOMContentLoaded", function(event) {

    fetchProfile();
    document.getElementById("edit-profile").addEventListener("click", function(){window.location.replace(contextPath + "/user/do-edit");});
    document.getElementById("delete-booking").addEventListener("click", sendGenericDeleteRequest(contextPath+"/booking-delete",function(){}));
    document.getElementsByName("delete-advertisement").addEventListener("click", fetchDeleteAdvertisement());
    document.getElementsByName("info-advertisement").addEventListener("click", fetchInfoAdvertisement());
    document.getElementsByName("edit-advertisement").addEventListener("click", fetchEditAdvertisement());

});

function fetchDeleteAdvertisement(){
    //TODO: Retrieve the ID of the clicked button, call sendJsonRequest
}

function fetchInfoAdvertisement(){
    //TODO: Retrieve the ID of the clicked button, call sendJsonRequest
}

function fetchEditAdvertisement(){
    //TODO: Retrieve the ID of the clicked button, call sendJsonRequest
}

function fetchProfile() {
    let url = new URL(contextPath+"/user/profile");
    sendGenericGetRequest(url ,loadProfile());
}

function loadProfile(req){

    let isTourist = req.getAttribute("userType");
    let section = document.getElementById("userInfoSection");

    if(isTourist){

        let bookingList = req.getAttribute("bookingList");
        let advertisementList = req.getAttribute("advertisementList");
        let userObj = req.getAttribute("user");
        let name = userObj.name;
        let surname = userObj.surname;
        let email = userObj.email;
        let address = userObj.address;
        let phonenumber = userObj.phonenumber;
        let idCity = userObj.idCity;
        let birthDate = userObj.birthDate;
        let score = req.getAttribute("score");

        let attributesName = ["Name: ", "Surname: ", "Birthdate: ","Email: ", "Address: ", "City: ", "Phone Number: ", "Score: "];
        let attributesValue = [name, surname, birthDate, email, address, idCity, phonenumber, score];

        for(let i=0;i<7;i++){
            createElement(attributesName[i], attributesValue[i], section);
        }

        let button = document.createElement("button");
        button.innerHTML = "Edit Profile";
        button.id = "edit-profile";
        section.appendChild(button);

        for(let i=0;i<bookingList.length;i++){
            createListBooking("Booking n. "+i,advertisementList[i],section)
        }

    }
    else{

        let advertisementList = req.getAttribute("advertisementList");
        let userObj = req.getAttribute("user");
        let name = userObj.name;
        let email = userObj.email;
        let address = userObj.address;
        let phonenumber = userObj.phonenumber;
        let idCity = userObj.idCity;

        let attributesName = ["Name: ", "Email: ", "Address: ", "Phone Number: ", "City: "];
        let attributesValue = [name, email, address, phonenumber, idCity];

        let section = document.getElementById("userInfoSection");
        for(let i=0;i<7;i++){
            createElement(attributesName[i], attributesValue[i], section);
        }

        let button = document.createElement("button");
        button.innerHTML = "Edit Profile";
        button.id = "edit-profile";
        section.appendChild(button);

        for(let i=0;i<advertisementList.length;i++){
            createListAdvertisement(advertisementList[i], section)
        }

    }

}

function getCityFromId(idCity) {
    //TODO: Retrieve city from id
    return idCity;
}

function createElement(name, value, section){
    let par = document.createElement("p");
    let parName = document.createTextNode(name);
    let parValue = document.createElement("span");
    parValue.className = "userInfo";
    parName.appendChild(parValue);
    par.appendChild(parName);
    section.appendChild(par);
}

function createListBooking(name, value, section){
    let form = document.createElement("form");
    let par = document.createElement("p");
    let parName = document.createTextNode(name + value.title);
    let hiddenDelete = document.createElement("input");
    hiddenDelete.setAttribute("type", "hidden");
    hiddenDelete.setAttribute("idAdvertisement", value.id);
    par.appendChild(hiddenDelete);
    let deleteBookingButton = document.createElement("button");
    deleteBookingButton.innerHTML = "Delete";
    deleteBookingButton.name = "delete-advertisement";
    deleteBookingButton.setAttribute("type", "submit");

    par.appendChild(deleteBookingButton);
    par.appendChild(parName);
    form.appendChild(par);
    section.appendChild(form);

}

function createListAdvertisement(advertisement, section){
    let par = document.createElement("p");
    let parName = document.createTextNode(advertisement.title);
    let deleteAdvButton = document.createElement("button");
    deleteAdvButton.innerHTML = "Delete";
    deleteAdvButton.name = "delete-advertisement"
    deleteAdvButton.id = advertisement.id;
    par.appendChild(deleteAdvButton);
    par.appendChild(parName);
    section.appendChild(par);
}


