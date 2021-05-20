
document.addEventListener("DOMContentLoaded", function(event) {

    getTypeAdvList("idType", "Type");

    document.getElementById("title").addEventListener("focusout", validateTitle);
    document.getElementById("description").addEventListener("focusout", validateDescription);
    document.getElementById("price").addEventListener("focusout", validatePrice);
    document.getElementById("numTotItem").addEventListener("focusout", validateNumTotItem);

    document.getElementById("create-button").addEventListener("click", function(event) {
        event.preventDefault();
        createAdvertisement()
    });
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

    let data = "{\"advertisement\":" +
        "{\"idAvdertisement\":\"" + idAdvertisement.toString() + "\"," +
        "\"title\":\"" + sanitizeString(title) +
        "\",\"description\":\"" + sanitizeString(description) + "\"," +
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
        let idAdvertisement = jsonData.idAdvertisement;

        // Sends to the upload images page
        window.location.href = contextPath + "/image-do-upload/" + idAdvertisement;
    });
}
