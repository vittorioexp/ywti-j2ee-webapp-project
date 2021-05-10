let idAdvertisement;

document.addEventListener("DOMContentLoaded", function(event) {

    idAdvertisement = document.getElementById('idAdvertisement').value;
    document.getElementById("edit-button").addEventListener("click", fetchEditAdvertisement);

});

function fetchEditAdvertisement(){

    let url = new URL(contextPath+"/adv/" + idAdvertisement);

    //TODO: Check the input field

    let title = document.getElementById("title").value;
    let description = document.getElementById("description").value;
    let dateStart = document.getElementById("dateStart").value.toString();
    let dateEnd = document.getElementById("dateEnd").value.toString();
    let timeStart = document.getElementById("timeStart").value.toString();
    let timeEnd = document.getElementById("timeEnd").value.toString();
    let price = document.getElementById("price").value.toString();
    let numTotItem = document.getElementById("numTotItem").value.toString();
    let data = "{\"searchParameters\":{\"idType\":\"" + idType +
        "\",\"idCity\":\"" + idCity + "\",\"dateStart\":\"" + dateStart + "\"}}";

    let idAdvertisementString = idAdvertisement.toString();
    let score = 0;

    //TODO: Fix the curl command

    "{\"advertisement\": {\"idAdvertisement\":\"" + idAdvertisement.toString() + ",\"title\":\"" + title
    + "\",\"description\":\"" + description + "\",\"score\":\"" + score.toString() + "\",\"price\":\"" + price +
    "\",\"numTotItem\":\"" + numTotItem + "\",\"dateStart\":\"" + dateStart + "\",\"dateEnd\":\"" + dateEnd
    + "\",\"timeStart\":\"" + timeStart + "\",\"timeEnd\":\"" + timeEnd + "\",\"emailCompany\":\"hotelcentr ale@gmail.com\",\"idType\":\"6\"}}"

    sendJsonRequest(url, "PUT", data, function(req){window.location.replace(contextPath + "/adv-show/" + idAdvertisement);});

}


