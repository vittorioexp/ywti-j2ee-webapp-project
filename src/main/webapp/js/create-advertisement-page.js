
/*
Author: Matteo Piva

Js to manage the creation of an advertisement
 */

document.addEventListener("DOMContentLoaded", function(event) {

    // Load dropdrown menu for type adv
    getTypeAdvList("idType", "Type");

    // Real time validation of some input fields
    document.getElementById("title").addEventListener("focusout", validateTitle);
    document.getElementById("description").addEventListener("focusout", validateDescription);
    document.getElementById("price").addEventListener("focusout", validatePrice);
    document.getElementById("numTotItem").addEventListener("focusout", validateNumTotItem);

    document.getElementById("create-button").addEventListener("click", function(event) {
        event.preventDefault();
        createAdvertisement()
    });
});

// Reads the input field and send a message to the server
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

    // Sanitize inputs
    title = sanitizeString(title);
    description = sanitizeString(description);

    // Data to be send to the server
    let data = "{\"advertisement\":" +
        "{\"idAvdertisement\":\"" + idAdvertisement.toString() + "\"," +
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

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){

            // Parses the JSON obj
            let jsonData = data.advertisement;
            let idAdvertisement = jsonData.idAdvertisement;

            // Sends to the upload images page
            window.location.href = contextPath + "/image-do-upload/" + idAdvertisement;
            },
        error: function(res) {
            let resMessage = res.responseJSON.message;
            alert(resMessage.message + " " + resMessage.errorDetails);
        }
    });


}
