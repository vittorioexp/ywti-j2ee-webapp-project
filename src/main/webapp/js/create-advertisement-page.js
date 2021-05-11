document.addEventListener("DOMContentLoaded", function(event) {

    //fetchTypeAdvList();

    document.getElementById("create-button").addEventListener("click", createAdvertisement);
});

// TODO: Fetches the list of available typeAdv
function fetchTypeAdvList() {
    let url = new URL(contextPath+"/.....");
    // put code here
    sendGenericGetRequest(url, loadTypeAdvList);
}

// TODO: Loads the list of available type adv
function loadTypeAdvList(req){
    // put code here
}

function createAdvertisement() {

    let url = new URL(contextPath+"/adv-create");

    let idAdvertisement = 0;
    let title = document.getElementById("title").value;
    let description = document.getElementById("description").value;
    let price = document.getElementById("price").value.toString();
    let score = (price/3.14);
    let numTotItem = document.getElementById("numTotItem").value.toString();
    let dateStart = document.getElementById("dateStart").value.toString();
    let dateEnd = document.getElementById("dateEnd").value.toString();
    let timeStart = document.getElementById("timeStart").value.toString();
    let timeEnd = document.getElementById("timeEnd").value.toString();
    let idType = document.getElementById("idType").value.toString();
    let emailCompany = sessionStorage.getItem("userEmail");
    let data = "{\"advertisement\":{\"idAvdertisement\":\"" + idAdvertisement.toString() + "\"," +
        "\"title\":\"" + title +
        "\",\"description\":\"" + description + "\"," +
        "\"price\":\"" + price + "\"," +
        "\"score\":\"" + score.toString() + "\"," +
        "\"numTotItem\":\"" + numTotItem + "\"," +
        "\"dateStart\":\"" + dateStart + "\"," +
        "\"dateEnd\":\"" + dateEnd + "\"," +
        "\"timeStart\":\"" + timeStart + "\"," +
        "\"timeEnd\":\"" + timeEnd + "\"," +
        "\"emailCompany\":\"" + emailCompany + "\"," +
        "\"idType\":\"" + idType + "\"}}";

    data = "{\"advertisement\":{\"idAdvertisement\":\"1\",\"title\":\"Capodanno sulle dolomiti\",\"description\":" +
        "\"Sette giorni di relax in mezzo alla neve delle dolomiti in Trentino alto adige a" +
        " capodanno\",\"score\":\"100\",\"price\":\"800\",\"numTotItem\":\"24\",\"dateStart\":\"2021-12-28" +
        "\",\"dateEnd\":\"2021-12-28" +
        "\",\"timeStart\":\"14:14:14\",\"timeEnd\":\"14:14:15\",\"emailCompany\":\"hotelcentrale@gmail.com\",\"idType\":\"6\"}}";

    //alert(data);
    //sendJsonRequest(url, "POST", data, function(req){window.location.replace(contextPath + "/html/protected/show-profile.html");});
    sendJsonRequest(url, "POST", data, function(req){
        alert("bravo");
        });
}