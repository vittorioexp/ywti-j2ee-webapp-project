document.addEventListener("DOMContentLoaded", function(event) {

    //fetchTypeAdvList();
    //fetchCityList();

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

// TODO: Fetches the list of available cities
function fetchCityList(){
    let url = new URL(contextPath+"/.....");
    // put code here
    sendGenericGetRequest(url, loadCityList);
}

// TODO: Loads the list of available cities
function loadCityList(req){
    // TODO put code here
}

function createAdvertisement() {

    let url = new URL(contextPath+"/adv-create");

    let idAdvertisement = 0;
    let title = document.getElementById("title").value;
    let description = document.getElementById("description").value;
    let price = document.getElementById("price").value;
    let score = price/3.14;
    let numTotItem = document.getElementById("numTotItem").value;
    let dateStart = document.getElementById("dateStart").value.toString();
    let dateEnd = document.getElementById("dateEnd").value;
    let timeStart = document.getElementById("timeStart").value.toString();
    let timeEnd = document.getElementById("timeEnd").value;
    let idType = document.getElementById("idType").value;
    let emailCompany =
    let data = "{\"advertisement\":{\"idAvdertisement\":\"" + idAdvertisement + "\"," +
        "\"title\":\"" + title +
        "\",\"description\":\"" + description + "\"," +
        "\"price\":\"" + price + "\"," +
        "\"score\":\"" + score + "\"," +
        "\"numTotItem\":\"" + numTotItem + "\"," +
        "\"dateStart\":\"" + dateStart + "\"," +
        "\"dateEnd\":\"" + dateEnd + "\"," +
        "\"timeStart\":\"" + timeStart + "\"," +
        "\"timeEnd\":\"" + timeEnd + "\"," +
        "\"emailCompany\":\"" + emailCompany + "\"," +
        "\"idType\":\"" + idType + "\"}}";


    sendJsonRequest(url, "POST", data);


}