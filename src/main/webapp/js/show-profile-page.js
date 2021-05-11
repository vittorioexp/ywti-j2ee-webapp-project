
document.addEventListener("DOMContentLoaded", function(event) {

    fetchProfile();
    document.getElementById("edit-profile").addEventListener("click", window.location.replace((contextPath + "/user/do-edit")));
    document.getElementByName("delete-booking").addEventListener("click", fetchDeleteBooking(document.getElementByName("delete-booking").id));
    document.getElementsByName("delete-advertisement").addEventListener("click", fetchDeleteAdvertisement(document.getElementsByName("delete-advertisement").id));
    document.getElementsByName("info-advertisement").addEventListener("click", fetchInfoAdvertisement(document.getElementsByName("info-advertisement").id));
    document.getElementsByName("edit-advertisement").addEventListener("click", fetchEditAdvertisement(document.getElementsByName("edit-advertisement").id));

});

function fetchProfile(){
    let url = contextPath + "/user/profile";

    //TODO: Come ottenere i valori passati come setAttribute dalla ShowProfileServlet

    let req = new XMLHttpRequest();

    let isTourist = req.getAttribute("userType");

    let section = document.getElementById("userInfoSection");

    if(isTourist){

        //If he is a tourist certain parameters are shown

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

        // Button for edit profile
        let div = document.createElement("div");
        let editUserButton = document.createElement("button");
        editUserButton.innerHTML = "Edit Profile";
        editUserButton.name = "edit-profile";
        div.appendChild(editUserButton);
        section.appendChild(div);


        for(let i=0;i<bookingList.length;i++){
            createListBooking("Booking n. "+i,advertisementList[i],section)
        }

    }
    else{
        //If he is a company certain parameters are shown
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

function fetchDeleteBooking(id){

    let url = contextPath + "/booking-delete";
    let httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert("Cannot create an XMLHTTP instance");
        return false;
    }
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                alert ( "Delete booking successful!");
            }
            else {
                console.log(httpRequest.responseText);
                alert("Problem processing the delete booking request!");
            }
        }
    };
    httpRequest.setAttribute( "idAdvertisement" ,id);
    httpRequest.open("DELETE", url);
    httpRequest.send();
}

function fetchDeleteAdvertisement(){
    //TODO: Retrieve the ID of the clicked button, call sendJsonRequest
    let idAdvertisement = id.substring(id.lastIndexOf("del-")+4);
    let url = contextPath + "/adv/" + idAdvertisement;
    sendJsonRequest(url, "DELETE", "", manageDelete);
}

function manageDelete(req){
    window.location.replace(contextPath + "/user/profile");
}

function fetchInfoAdvertisement(id){
    //TODO: Retrieve the ID of the clicked button, call sendJsonRequest
    let idAdvertisement = id.substring(id.lastIndexOf("inf-")+4);
    let url = contextPath + "/adv-show/" + idAdvertisement;
    window.location.replace(url);
}

function fetchEditAdvertisement(){
    //TODO: Retrieve the ID of the clicked button, call sendJsonRequest
    let idAdvertisement = id.substring(id.lastIndexOf("edi-")+4);
    let url = contextPath + "/adv-edit/" + idAdvertisement;
    window.location.replace(url);
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

    let par = document.createElement("p");
    let parName = document.createTextNode(name + value.title);
    let deleteBookingButton = document.createElement("button");
    deleteBookingButton.innerHTML = "Delete";
    deleteBookingButton.name = "delete-booking";
    deleteBookingButton.id = value.id;

    par.appendChild(deleteBookingButton);
    par.appendChild(parName);
    section.appendChild(par);

}

function createListAdvertisement(advertisement, section){
    let par = document.createElement("p");
    let parName = document.createTextNode(advertisement.title);
    let deleteAdvButton = document.createElement("button");
    deleteAdvButton.innerHTML = "Delete";
    deleteAdvButton.name = "delete-advertisement";
    let str = "del-";
    deleteAdvButton.id = str + advertisement.id;

    let editAdvButton = document.createElement("button");
    editAdvButton.innerHTML = "Edit";
    editAdvButton.name = "edit-advertisement";
    str = "edi-";
    editAdvButton.id = str + advertisement.id;

    let infoAdvButton = document.createElement("button");
    infoAdvButton.innerHTML = "Info";
    infoAdvButton.name = "info-advertisement";
    str = "inf-";
    infoAdvButton.id = str + advertisement.id;

    par.appendChild(parName);
    par.appendChild(deleteAdvButton);
    par.appendChild(editAdvButton);
    par.appendChild(infoAdvButton);
    section.appendChild(par);
}


