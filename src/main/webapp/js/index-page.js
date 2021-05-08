// TODO: fix index-page.js
// TODO Vedere tutor repo: js/search-maintenance-page.js

document.addEventListener("DOMContentLoaded", function(event) {

    // Fetches the list of typeAdvertisements and Cities
    //fetchTypeAdvList();
    //fetchCityList();

    // fetch the list of advertisements when clicking the search button
    document.getElementById("search-button").addEventListener("click", fetchAdvertisementList);
    //document.addEventListener("submit", fetchAdvertisementList);
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

// Converts the form in JSON format and fetches the list of advertisements
function fetchAdvertisementList(){

    let url = new URL(contextPath+"/adv");

    // Converts the form in JSON format
    // Es: "{\"searchParameters\":{\"idType\":\"6\",\"idCity\":\"28\",\"dateStart\":\"2021-04-20\"}}"
    let idType = document.getElementById("idType").value;
    let idCity = document.getElementById("idCity").value;
    let dateStart = document.getElementById("dateStart").value.toString();
    let data = "{\"searchParameters\":{\"idType\":\"" + idType +
        "\",\"idCity\":\"" + idCity + "\",\"dateStart\":\"" + dateStart + "\"}}";

    // fetches the list of advertisements
    sendJsonRequest(url, "GET", data, loadAdvertisementList);
}

// TODO: Loads the list of advertisements
function loadAdvertisementList(req){
    // make the section visible in index.html
    let advertisementList = document.getElementById("advertisementList")
    advertisementList.setAttribute("class", "d-block");

    // TODO: display the list
    document.getElementById("advertisementList").innerHTML = req;
    /*
    var jsonData = JSON.parse(req.responseText);
            console.log(jsonData["description"]);
            document.getElementById("description").value = jsonData["description"];
            setPreselectedPark(jsonData['parkid']);
            setPreselectedModel(jsonData['modelid']);
     */
}