// TODO: fix index-page.js
// TODO Vedere tutor repo: js/search-maintenance-page.js

document.addEventListener("DOMContentLoaded", function(event) {

    // Fetches the list of typeAdvertisements and Cities
    fetchTypeAdvList();
    fetchCityList();

    // fetch the list of advertisements when clicking the search button
    document.getElementById("search-button").addEventListener("click", fetchAdvertisementList);
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
    // put code here
}

// TODO: Converts the form in JSON format and fetches the list of advertisements
function fetchAdvertisementList(){
    let url = new URL(contextPath+"/adv");

    // Converts the form in JSON format
    //url.searchParams.set("idType", document.getElementById("idType").value);
    //url.searchParams.set("idCity", document.getElementById("idCity").value);
    //url.searchParams.set("dateStart", document.getElementById("dateStart").value);

    // fetches the list of advertisements
    sendGenericGetRequest(url, loadAdvertisementList);
}

// TODO: Loads the list of advertisements
function loadAdvertisementList(req){

}