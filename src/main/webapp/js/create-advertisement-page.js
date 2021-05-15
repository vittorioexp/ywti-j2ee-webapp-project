
document.addEventListener("DOMContentLoaded", function(event) {

    //fetchTypeAdvList();

    document.getElementById("create-button").addEventListener("click", function(event) {
        event.preventDefault();
        validateCreation(event);
        createAdvertisement();
    });

    //document.getElementById("title").addEventListener("keyup", function(event) {validateTitle()});
});

function createAdvertisement() {

    let url = new URL(contextPath+"/adv-create");

    let idAdvertisement = 0;
    let title = document.getElementById("title").value;
    let description = document.getElementById("description").value;
    let price = document.getElementById("price").value.toString();
    let numTotItem = document.getElementById("numTotItem").value.toString();
    let dateStart = document.getElementById("dateStart").value.toString();
    let dateEnd = document.getElementById("dateEnd").value.toString();
    let timeStart = document.getElementById("timeStart").value.toString();
    let timeEnd = document.getElementById("timeEnd").value.toString();
    let idType = document.getElementById("idType").value.toString();
    let data = "{\"advertisement\":{\"idAvdertisement\":\"" + idAdvertisement.toString() + "\"," +
        "\"title\":\"" + title +
        "\",\"description\":\"" + description + "\"," +
        "\"price\":\"" + price + "\"," +
        "\"score\":\"" + 0 + "\"," +
        "\"numTotItem\":\"" + numTotItem + "\"," +
        "\"dateStart\":\"" + dateStart + "\"," +
        "\"dateEnd\":\"" + dateEnd + "\"," +
        "\"timeStart\":\"" + timeStart + "\"," +
        "\"timeEnd\":\"" + timeEnd + "\"," +
        "\"emailCompany\":\"" + getUserEmail() + "\"," +
        "\"idType\":\"" + idType + "\"}}";

    sendJsonRequest(url, "POST", data, function(req){

        // Parses the JSON obj
        let jsonData = JSON.parse(req).advertisement;
        let idAdvertisement = jsonData['idAdvertisement'];

        // Sends to the upload images page
        window.location.href = contextPath + "/image-do-upload/" + idAdvertisement;
    });
}


function validateCreation(event)
{
    let error = document.getElementById("error");
    let title = document.getElementById("title");
    let description = document.getElementById("description");
    let price = document.getElementById("price");
    let numTotItem = document.getElementById("numTotItem");

    if(!validateTitle(title.value)){
        errorCreateAdvertisement(error, event);
    }else if(!validateDescription(description.value)){
        errorCreateAdvertisement(error, event);
    }else if(!validatePrice(price.value)){
        errorCreateAdvertisement(error, event);
    }else if(!validateNumTotItem(numTotItem.value)){
        errorCreateAdvertisement(error, event);
    }else {
        error.innerHTML = "";
        error.className = "error";
    }
}

function errorCreateAdvertisement(error, event){
    error.innerHTML = "Value not correct";
    error.className = "error active";
    event.preventDefault();
}
