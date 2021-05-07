// TODO: fix index-page.js
// TODO Vedere tutor repo: js/search-maintenance-page.js

// self executing function here
document.addEventListener("DOMContentLoaded", function(event) {
    // your page initialization code here
    // the DOM will be available here

    // Fetches the list of typeAdvertisements and Cities
    fetchTypeAdvertisementList();
    fetchCityList();

    // calls searchAdvertisements() when clicking the search button
    document.getElementById("search-button").addEventListener("click", fetchAdvertisementList);
});

// TODO: Request the list of available typeAdvertisement
function fetchTypeAdvertisementList() {
    var url = new URL(contextPath+'/.....');
    // put code here
    genericGETRequest(url, showTypeAdvertisementList);
}

// TODO: Shows the list of available typeAdvertisement just requested
function loadTypeAdvertisementList(req){
}

// TODO: Request the list of available cities
function fetchCityList(){
    var url = new URL(contextPath+'/.....');
    // put code here
    genericGETRequest(url, showCityList);
}

// TODO: Shows the list of available cities just requested
function loadCityList(req){
    var url = new URL(contextPath+'/adv');
    // put code here
    genericGETRequest(url, showCityList);
}

// TODO: Converts the form in JSON format and requests the list of advertisements
function fetchAdvertisementList(){
    var url = new URL(contextPath+'/adv');
    //url.searchParams.set("idType", document.getElementById("idType").value);
    //url.searchParams.set("idCity", document.getElementById("idCity").value);
    //url.searchParams.set("dateStart", document.getElementById("dateStart").value);
    genericGETRequest(url, loadAdvertisementList);
}

// TODO: Shows the list of advertisements
function loadAdvertisementList(req){
}