

document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementById("edit-button").addEventListener("click", fetchEditAdvertisement);

});

function fetchEditAdvertisement(){

    let url = new URL(contextPath+"/adv/" + idAdvertisement);

    //TODO: Check the input field
    let currentUrl = window.location.href;
    let idAdvertisement =  currentUrl.substring(currentUrl.indexOf("adv-edit/")+9)

    let title = document.getElementById("title").value;
    let description = document.getElementById("description").value;
    let dateStart = document.getElementById("dateStart").value.toString();
    let dateEnd = document.getElementById("dateEnd").value.toString();
    let timeStart = document.getElementById("timeStart").value.toString();
    let timeEnd = document.getElementById("timeEnd").value.toString();
    let price = document.getElementById("price").value.toString();
    let numTotItem = document.getElementById("numTotItem").value.toString();

    let emailCompany = sessionStorage.getItem("userEmail");

    "{\"advertisement\": {\"idAdvertisement\":\"" + idAdvertisement + ",\"title\":\"" + title
    + "\",\"description\":\"" + description + "\",\"score\":\"" + "" + "\",\"price\":\"" + price +
    "\",\"numTotItem\":\"" + numTotItem + "\",\"dateStart\":\"" + dateStart + "\",\"dateEnd\":\"" + dateEnd
    + "\",\"timeStart\":\"" + timeStart + "\",\"timeEnd\":\"" + timeEnd + "\",\"emailCompany\":\"" + emailCompany + "\",\"idType\":\"0\"}}"

    sendJsonRequest(url, "PUT", data, function(req){window.location.replace(contextPath + "/adv-show/" + idAdvertisement);});

}


